package com.example.livingbymyselfserver.groupBuying;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.user.User;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface GroupBuyingService {
  ApiResponseDto createGroupBuying(User user, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException;
  ApiResponseDto updateGroupBuying(User user, Long groupBuyingId, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException;
  ApiResponseDto deleteGroupBuying(Long id, User user);
  GroupBuying findGroupBuying(Long id);

  GroupBuyingResponseDto getGroupBuying(User user, Long groupBuyingId);

  ApiResponseDto createApplication(User user, Long groupBuyingId);

  ApiResponseDto deleteApplication(User user, Long groupBuyingId);

  ApiResponseDto closeGroupBuying(Long groupBuyingId, User user);

  List<GroupBuyingResponseDto> getGroupBuyingList(User user, Pageable pageable);
}
