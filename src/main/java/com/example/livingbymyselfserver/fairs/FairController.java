package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home/fairs")
public class FairController{
  final private FairService fairService;
  @PostMapping    //공구게시글 등록
  public ResponseEntity<ApiResponseDto> createFair(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FairRequestDto requestDto) {
    ApiResponseDto result = fairService.createFair(userDetails.getUser(), requestDto);


    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
}
