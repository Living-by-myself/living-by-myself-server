package com.example.livingbymyselfserver.community;
import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityDetailResponseDto;
import com.example.livingbymyselfserver.community.dto.CommunityListResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @GetMapping
    public ResponseEntity<List<CommunityListResponseDto>> getCommunityListInfo(Pageable pageable) {
        List<CommunityListResponseDto> result = communityService.getCommunityListInfo(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityDetailResponseDto> getCommunityDetailInfo(@PathVariable Long communityId) {
        CommunityDetailResponseDto result = communityService.getCommunityDetailInfo(communityId);

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