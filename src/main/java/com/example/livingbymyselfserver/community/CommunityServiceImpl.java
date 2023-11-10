package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.community.AttachmentCommunityUrlRepository;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        ObjectMapper objectMapper = new ObjectMapper();
        CommunityRequestDto communityRequestDto = objectMapper.readValue(requestDto,CommunityRequestDto.class);

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
        ObjectMapper objectMapper = new ObjectMapper();
        CommunityRequestDto communityRequestDto = objectMapper.readValue(requestDto,CommunityRequestDto.class);

        Community community = findCommunity(communityId);

        communityUserVerification(community, user);

        if (!Objects.equals(multipartFiles[0].getOriginalFilename(), "")) {
            updateS3Image(community, multipartFiles);
        }

        community.setTitle(communityRequestDto.getTitle());
        community.setDescription(communityRequestDto.getDescription());
        community.setCategory(communityRequestDto.getCategory());

        return new ApiResponseDto("커뮤니티 게시글 수정 완료", 200);
    }

    private void updateS3Image(Community community,  MultipartFile[] multipartFiles) {
        AttachmentCommunityUrl attachmentUrl = attachmentCommunityUrlRepository.findByCommunity(community);

        if (attachmentUrl != null) {
            String[] fileList = attachmentUrl.getFileName().split(",");

            for (String file : fileList) {
                s3Service.deleteFile(file);
            }
            attachmentUrl.setFileName("");

            if ((multipartFiles.length) > 5) {
                throw new IllegalArgumentException("사진의 최대개수는 5개 입니다.");
            }
            List<String> uploadFileNames = s3Service.uploadFiles(multipartFiles);
            String combineUploadFileName = s3Service.CombineString(uploadFileNames);

            String replaceUploadFileName = combineUploadFileName.replaceFirst("^,", "");
            String result = (replaceUploadFileName).replaceFirst("^,",
                    "");

            attachmentUrl.setFileName(result);
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
        List<Community> communities = communityRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<CommunityListResponseDto> responseDtos = new ArrayList<>();

        for (Community community : communities) {
            AttachmentCommunityUrl attachmentCommunityUrl = attachmentCommunityUrlRepository.findByCommunity(community);
            CommunityListResponseDto responseDto;
            if (attachmentCommunityUrl == null) {
                responseDto = new CommunityListResponseDto(community);
            } else {
                responseDto = new CommunityListResponseDto(community, attachmentCommunityUrl);
            }

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    public Community findCommunity(Long id) {
        return communityRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("찾는 게시글이 존재하지 않습니다."));
    }

    private void communityUserVerification(Community community, User user) {
        if (!community.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("게시글 작성한 유저가 아닙니다");
        }
    }
}
