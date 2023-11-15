package com.example.livingbymyselfserver.groupBuying;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingListResponseDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingResponseDto;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "GroupBuying API", description = "공동구매/나눔 게시물 API")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/home/group-buying")
public class GroupBuyingController {

  final private GroupBuyingService groupBuyingService;

  @Operation(summary = "공구 게시글 전체 조회")
  @GetMapping
  public ResponseEntity<List<GroupBuyingResponseDto>> getGroupBuyingList(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      Pageable pageable){
    List<GroupBuyingResponseDto> result = groupBuyingService.getGroupBuyingList(userDetails.getUser(),pageable);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  @Operation(summary = "공구 검색기능")
  @GetMapping("/search")
  public ResponseEntity<GroupBuyingListResponseDto> searchGroupBuyingList(Pageable pageable,
      @RequestParam(value = "keyword", required = false) String keyword,
      @RequestParam(value = "category", required = false) GroupBuyingCategoryEnum category,
      @RequestParam(value = "category_share", required = false) GroupBuyingShareEnum enumShare,
      @RequestParam(value = "category_status", required = false) GroupBuyingStatusEnum status,
      @RequestParam(value = "address", required = false) String beobJeongDong,
      @RequestParam(value = "sort", required = false) String sort)// 정렬시 asc는 오름, desc는 내림
  {
    GroupBuyingListResponseDto result = groupBuyingService.searchGroupBuyingList(pageable, keyword, category,
        enumShare, status,beobJeongDong, sort);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 게시글 등록")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createGroupBuying(
          @AuthenticationPrincipal UserDetailsImpl userDetails,
          @RequestPart("requestDto") String requestDto,
          @RequestPart("fileName") MultipartFile[] multipartFiles) throws JsonProcessingException {
    ApiResponseDto result = groupBuyingService.createGroupBuying(userDetails.getUser(), requestDto, multipartFiles);

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @Operation(summary = "공구 신청")
  @PostMapping("/{groupBuyingId}/application")
  public ResponseEntity<ApiResponseDto> createApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long groupBuyingId) {
    ApiResponseDto result = groupBuyingService.createApplication(userDetails.getUser(),groupBuyingId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 지원 취소")
  @DeleteMapping("/{groupBuyingId}/application")
  public ResponseEntity<ApiResponseDto> deleteApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long groupBuyingId) {
    ApiResponseDto result = groupBuyingService.deleteApplication(userDetails.getUser(),groupBuyingId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 게시글 상세조회")
  @GetMapping("/{groupBuyingId}")
  public GroupBuyingResponseDto getGroupBuying(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long groupBuyingId) {


   return groupBuyingService.getGroupBuying(userDetails.getUser(), groupBuyingId);
  }

  @Operation(summary = "공구 게시글 수정")
  @PutMapping("/{groupBuyingId}")
  public ResponseEntity<ApiResponseDto> updateGroupBuying(
          @AuthenticationPrincipal UserDetailsImpl userDetails,
          @PathVariable Long groupBuyingId,
          @RequestPart("requestDto") String requestDto,
          @RequestPart("fileName") MultipartFile[] multipartFiles) throws JsonProcessingException {
    ApiResponseDto result = groupBuyingService.updateGroupBuying(userDetails.getUser(),groupBuyingId, requestDto, multipartFiles);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 게시글 삭제")
  @DeleteMapping ("/{groupBuyingId}")   //공구게시글 등록
  public ResponseEntity<ApiResponseDto> deleteGroupBuying(@PathVariable Long groupBuyingId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = groupBuyingService.deleteGroupBuying(groupBuyingId, userDetails.getUser());


    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 마감")
  @PatchMapping("/{groupBuyingId}/close")   //공구 마감
  public ResponseEntity<ApiResponseDto> closeGroupBuying(@PathVariable Long groupBuyingId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = groupBuyingService.closeGroupBuying(groupBuyingId, userDetails.getUser());

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
