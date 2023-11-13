package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.community.AttachmentCommunityUrlRepository;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{
    private final CommunityRepository communityRepository;
    private final S3Service s3Service;
    private final AttachmentCommunityUrlRepository attachmentCommunityUrlRepository;
    @Override
    public ApiResponseDto createCommunity(User user, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException {
        CommunityRequestDto communityRequestDto = conversionRequestDto(requestDto);

        Community community = new Community(communityRequestDto, user);
        communityRepository.save(community);

        if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
            uploadImage(multipartFiles, community);
        }

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
    public CommunityDetailResponseDto getCommunityDetailInfo(Long communityId) {
        Community community = findCommunity(communityId);
        AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);

        if (attachmentCommunityUrl == null) {
            return new CommunityDetailResponseDto(community);
        } else {
            return new CommunityDetailResponseDto(community, attachmentCommunityUrl);
        }
    }

    @Override
    public List<CommunityListResponseDto> getCommunityListInfo(Pageable pageable) {
        return communityRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(community -> {
                    AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);
                    return (attachmentCommunityUrl == null) ? new CommunityListResponseDto(community) : new CommunityListResponseDto(community, attachmentCommunityUrl);
                })
                .collect(Collectors.toList());
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
