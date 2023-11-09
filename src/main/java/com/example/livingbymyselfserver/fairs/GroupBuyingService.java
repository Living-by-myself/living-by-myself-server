package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.fairs.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface GroupBuyingService {
  ApiResponseDto createGroupBuying(User user, GroupBuyingRequestDto requestDto);
  ApiResponseDto updateGroupBuying(User user, Long groupBuyingId, GroupBuyingRequestDto requestDto);
  ApiResponseDto deleteGroupBuying(Long id, User user);
  GroupBuying findGroupBuying(Long id);

  GroupBuyingResponseDto getGroupBuying(User user, Long groupBuyingId);

  ApiResponseDto createApplication(User user, Long groupBuyingId);

  ApiResponseDto deleteApplication(User user, Long groupBuyingId);

  ApiResponseDto closeGroupBuying(Long groupBuyingId, User user);

  List<GroupBuyingResponseDto> getGroupBuyingList(User user, Pageable pageable);
}
