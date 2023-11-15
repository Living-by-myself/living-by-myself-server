package com.example.livingbymyselfserver.community;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingListResponseDto;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home/communities")
public class CommunityController {
    private final CommunityService communityService;
    @Operation(summary = "커뮤니티 조건검색")
    @GetMapping("/search")
    public ResponseEntity<CommunityListResponseDto> searchCommunityList(Pageable pageable,
        @RequestParam(value = "keyword", required = false) String keyword,
        @RequestParam(value = "category", required = false) CommunityCategoryEnum category,
        @RequestParam(value = "sort", required = false) String sort)// 정렬시 asc는 오름, desc는 내림
    {
        CommunityListResponseDto result = communityService.searchCommunityList(pageable, keyword, category,
            sort);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping
    public ResponseEntity<List<CommunityResponseDto>> getCommunityListInfo(Pageable pageable) {
        List<CommunityResponseDto> result = communityService.getCommunityListInfo(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityDetailResponseDto> getCommunityDetailInfo(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId) {
        CommunityDetailResponseDto result = communityService.getCommunityDetailInfo(userDetails.getUser(),communityId);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto> createCommunity(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart("requestDto") String requestDto,
            @RequestPart("fileName") MultipartFile[] multipartFiles
    ) throws JsonProcessingException {
        ApiResponseDto result = communityService.createCommunity(userDetails.getUser(), requestDto, multipartFiles);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<ApiResponseDto> updateCommunity(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long communityId,
            @RequestPart("requestDto") String requestDto,
            @RequestPart("fileName") MultipartFile[] multipartFiles) throws JsonProcessingException {
        ApiResponseDto result = communityService.updateCommunity(userDetails.getUser(),communityId, requestDto, multipartFiles);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<ApiResponseDto> deleteCommunity(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long communityId) {
        ApiResponseDto result = communityService.deleteCommunity(userDetails.getUser(), communityId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}