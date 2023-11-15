package com.example.livingbymyselfserver.groupBuying;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingListResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.user.User;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface GroupBuyingService {
  GroupBuyingListResponseDto searchGroupBuyingList(Pageable pageable,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "category", required = false) GroupBuyingCategoryEnum category,
      @RequestParam(value = "category_share", required = false) GroupBuyingShareEnum enumShare,
      @RequestParam(value = "category_status", required = false) GroupBuyingStatusEnum status,
      @RequestParam(value = "address", required = false) String beobJeongDong,
      @RequestParam(value = "sort", required = false) String sort);// 정렬시 asc는 오름, desc는 내림


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
