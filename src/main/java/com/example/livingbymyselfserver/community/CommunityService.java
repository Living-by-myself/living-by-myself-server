package com.example.livingbymyselfserver.community;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityRequestDto;
import com.example.livingbymyselfserver.user.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

public interface CommunityService {
    ApiResponseDto createCommunity(User user, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException;

    ApiResponseDto updateCommunity(User user, Long communityId, String requestDto, MultipartFile[] multipartFiles) throws JsonProcessingException;

    ApiResponseDto deleteCommunity(User user, Long communityId);

    Community findCommunity(Long id);

    CommunityDetailResponseDto getCommunityDetailInfo(Long communityId);
}
