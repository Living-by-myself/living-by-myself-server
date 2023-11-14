package com.example.livingbymyselfserver.user.profile;

import com.example.livingbymyselfserver.attachment.S3Service;
import com.example.livingbymyselfserver.attachment.entity.AttachmentCommunityUrl;
import com.example.livingbymyselfserver.attachment.entity.AttachmentUserUrl;
import com.example.livingbymyselfserver.attachment.user.AttachmentUserUrlRepository;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserService;
import com.example.livingbymyselfserver.user.badge.dto.BadgeResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.OtherUserProfileResponseDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileRequestDto;
import com.example.livingbymyselfserver.user.profile.dto.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

  private final UserRepository userRepository;
  private final AttachmentUserUrlRepository attachmentUserUrlRepository;
  private final S3Service s3Service;

  @Override
  public ProfileResponseDto getUserProfile(User user) {
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    return (attachmentUserUrl != null) ?
            new ProfileResponseDto(user, attachmentUserUrl) :
            new ProfileResponseDto(user);
  }

  @Override
  @Transactional
  public ApiResponseDto updateUserProfileImage(User user, MultipartFile multipartFiles) {
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    if (attachmentUserUrl == null) {
      String fileUrlResult = profileImageUpload(multipartFiles, user);

      AttachmentUserUrl file = new AttachmentUserUrl(user, fileUrlResult);

      attachmentUserUrlRepository.save(file);
    } else {
      String file = attachmentUserUrl.getFileName();
      s3Service.deleteFile(file);

      String replaceUploadFileName = profileImageUpload(multipartFiles, user);
      attachmentUserUrl.setFileName(replaceUploadFileName);
    }

    return new ApiResponseDto("프로필 이미지 설정", 200);
  }

  private String profileImageUpload(MultipartFile multipartFiles, User user) {
    String filePath = s3Service.uploadFile(multipartFiles);

    return filePath.replaceFirst("^,", "");
  }

  @Override
  @Transactional
  public ApiResponseDto updateUserProfile(ProfileRequestDto requestDto, User user) {
    user.setNickname(requestDto.getNickName());
    user.setAddress(requestDto.getAddress());

    return new ApiResponseDto("프로필 수정 완료", 200);
  }

  @Override
  public OtherUserProfileResponseDto getOtherUserProfile(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    AttachmentUserUrl attachmentUserUrl = attachmentUserUrlRepository.findByUser(user);

    return (attachmentUserUrl != null) ?
            new OtherUserProfileResponseDto(user, attachmentUserUrl) :
            new OtherUserProfileResponseDto(user);
  }
}
