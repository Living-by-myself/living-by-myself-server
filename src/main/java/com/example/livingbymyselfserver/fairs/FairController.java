package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Fair API", description = "공동구매/나눔 게시물 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/home/fairs")
public class FairController{
  final private FairService fairService;
  @Operation(summary = "공구 게시글 등록")
  @PostMapping
  public ResponseEntity<ApiResponseDto> createFair(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FairRequestDto requestDto) {
    ApiResponseDto result = fairService.createFair(userDetails.getUser(), requestDto);


    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
  @Operation(summary = "공구 게시글 수정")
  @PutMapping("/{fairId}")
  public ResponseEntity<ApiResponseDto> updateFair(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long fairId, @RequestBody FairRequestDto requestDto) {
    ApiResponseDto result = fairService.updateFair(userDetails.getUser(),fairId, requestDto);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
  @Operation(summary = "공구 게시글 삭제")
  @DeleteMapping ("/{fairId}")   //공구게시글 등록
  public ResponseEntity<ApiResponseDto> deleteFair(@PathVariable Long fairId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
    ApiResponseDto result = fairService.deleteFair(fairId, userDetails.getUser());


    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
}
