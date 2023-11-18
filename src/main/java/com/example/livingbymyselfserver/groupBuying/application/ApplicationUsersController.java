package com.example.livingbymyselfserver.groupBuying.application;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ApplicationUser API", description = "유저 공구 신청 조회 취소")
@RequiredArgsConstructor
@RestController
@RequestMapping("/home/group-buying")
public class ApplicationUsersController{

  private final ApplicationUsersService applicationUsersService;

  @Operation(summary = "공구 신청")
  @PostMapping("/{groupBuyingId}/application")
  public ResponseEntity<ApiResponseDto> createApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long groupBuyingId) {
    ApiResponseDto result = applicationUsersService.createApplication(userDetails.getUser(),groupBuyingId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @Operation(summary = "공구 지원 취소")
  @DeleteMapping("/{groupBuyingId}/application")
  public ResponseEntity<ApiResponseDto> deleteApplication(@AuthenticationPrincipal UserDetailsImpl userDetails,@PathVariable Long groupBuyingId) {
    ApiResponseDto result = applicationUsersService.deleteApplication(userDetails.getUser(),groupBuyingId);

    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
