package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.community.AttachmentCommunityUrlRepository;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.common.PostTypeEnum;
import com.example.livingbymyselfserver.common.RedisUtil;
import com.example.livingbymyselfserver.common.RedisViewCountUtil;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.community.like.CommunityLikeRepository;
import com.example.livingbymyselfserver.community.repository.CommunityRepository;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.badge.BadgeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final S3Service s3Service;
    private final AttachmentCommunityUrlRepository attachmentCommunityUrlRepository;
    private final BadgeService badgeService;
    private final RedisViewCountUtil redisViewCountUtil;
    private final RedisUtil redisUtil;
    private final CommunityLikeRepository communityLikeRepository;
    @Override
    @Transactional
    public ApiResponseDto createCommunity(User user, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException {
        CommunityRequestDto communityRequestDto = conversionRequestDto(requestDto);

        Community community = new Community(communityRequestDto, user);

        communityRepository.save(community);

        if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
            uploadImage(multipartFiles, community);
        }

        badgeService.addBadgeForCommunityCount(user);

        return new ApiResponseDto("커뮤니티 게시글 생성 완료", 201);
    }

    @Override
    @Transactional
    public ApiResponseDto updateCommunity(User user, Long communityId, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException {
        CommunityRequestDto communityRequestDto = conversionRequestDto(requestDto);

        Community community = findCommunity(communityId);

        communityUserVerification(community, user);

        if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
            updateCommunityS3Image(community, multipartFiles);
        }

        community.setTitle(communityRequestDto.getTitle());
        community.setDescription(communityRequestDto.getDescription());
        community.setCategory(communityRequestDto.getCategory());

        return new ApiResponseDto("커뮤니티 게시글 수정 완료", 200);
    }

    private void updateCommunityS3Image(Community community,  MultipartFile[] multipartFiles) {
        AttachmentCommunityUrl attachmentUrl = attachmentCommunityUrlRepository.findByCommunity(community);

        if (attachmentUrl != null) {
            s3Service.updateS3Image(attachmentUrl, multipartFiles);
        } else {
            uploadImage(multipartFiles, community);
        }
    }

    @Override
    @Transactional
    public ApiResponseDto deleteCommunity(User user, Long communityId) {
        Community community = findCommunity(communityId);
        communityUserVerification(community, user);

        AttachmentCommunityUrl attachmentUrl = attachmentCommunityUrlRepository.findByCommunity(community);

        if (attachmentUrl != null) {
            String[] fileNames = attachmentUrl.getFileName().split(",");

            for (String fileName : fileNames) {
                s3Service.deleteFile(fileName);
            }

            attachmentCommunityUrlRepository.deleteByCommunity(community);
        }
        communityRepository.delete(community);
        redisUtil.delete("Community:"+communityId);

        return new ApiResponseDto("커뮤니티 게시글 삭제", 200);
    }

    public void uploadImage(MultipartFile[] multipartFiles, Community community) {
        List<String> filePaths = s3Service.uploadFiles(multipartFiles);
        String fileUrls = "";
        for (String fileUrl : filePaths) {
            fileUrls = fileUrls + "," + fileUrl;
        }

        String fileUrlResult = fileUrls.replaceFirst("^,", "");
        AttachmentCommunityUrl file = new AttachmentCommunityUrl(fileUrlResult, community);
        attachmentCommunityUrlRepository.save(file);
    }

    @Override
    public CommunityDetailResponseDto getCommunityDetailInfo(User user, Long communityId) {
        Community community = findCommunity(communityId);
        AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);

        // 조회수 증가 로직
        if (redisViewCountUtil.communityCheckAndIncrementViewCount(communityId.toString(),
            user.getId().toString(), PostTypeEnum.COMMUNITY)) { // 조회수를 1시간이내에 올린적이 있는지 없는지 판단
            redisViewCountUtil.incrementPostViewCount(communityId.toString(),PostTypeEnum.COMMUNITY);
        }

        Double viewCount = redisViewCountUtil.getViewPostCount(communityId.toString(),PostTypeEnum.COMMUNITY);
        badgeService.addBadgeForCommunityView(community);

        Boolean existsLike = communityLikeRepository.existsByCommunityAndUser(community, user);

        if (attachmentCommunityUrl == null) {
            return new CommunityDetailResponseDto(community,viewCount, existsLike);
        } else {
            return new CommunityDetailResponseDto(community, attachmentCommunityUrl,viewCount, existsLike);
        }
    }

    @Override
    public List<CommunityResponseDto> getCommunityListInfo(Pageable pageable) {
        return communityRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(community -> {
                    AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);
                    double viewCount = redisViewCountUtil.getViewPostCount(community.getId().toString(),PostTypeEnum.COMMUNITY);
                    return (attachmentCommunityUrl == null) ? new CommunityResponseDto(community,viewCount) : new CommunityResponseDto(community, attachmentCommunityUrl,viewCount);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommunityListResponseDto searchCommunityList(Pageable pageable, String keyword,
        CommunityCategoryEnum category, String sort) {
        Page<Community> communityPage = communityRepository.searchItemList(pageable, keyword,
            category,sort); //전체 크기를 받아오기 위한 Page
        Long totalLen =  communityPage.getTotalElements();  //total길이

        List<CommunityResponseDto> communityResponseDtoList = communityRepository.searchItemList(pageable,keyword,
                category,sort)
            .stream().map(community -> {
                double viewCount = redisViewCountUtil.getViewPostCount(community.getId().toString(),PostTypeEnum.COMMUNITY);
                AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);
                return (attachmentCommunityUrl == null) ? new CommunityResponseDto(community,viewCount) : new CommunityResponseDto(community, attachmentCommunityUrl,viewCount);
            })
            .toList();

        return new CommunityListResponseDto(communityResponseDtoList, totalLen);
    }


    public Community findCommunity(Long id) {
        return communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
    }


    private void communityUserVerification(Community community, User user) {
        if (!community.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글 작성한 유저가 아닙니다");
        }
    }

    private CommunityRequestDto conversionRequestDto(String requestDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(requestDto,CommunityRequestDto.class);
    }
}
