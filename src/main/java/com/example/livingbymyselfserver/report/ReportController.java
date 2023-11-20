package com.example.livingbymyselfserver.report;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.security.UserDetailsImpl;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "신고 API", description = "신고관련 기능들")
@RestController
@RequiredArgsConstructor
@RequestMapping("/home/report")
public class ReportController {

  private final ReportService reportService;

  //유저가 신고하는 기능
  @Operation(summary = "유저신고")
  @PostMapping("/{userId}")
  public ResponseEntity<ApiResponseDto> userReport(@AuthenticationPrincipal UserDetailsImpl userDetails,@RequestBody ReportRequestDto requestDto, @PathVariable Long userId) {
    ApiResponseDto result = reportService.userReport(userDetails.getUser(), requestDto,userId);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  //어드민이 신고를 승인하는 기
  @Operation(summary = "신고 승인(어드민)")
  @PatchMapping("/{reportId}")
  public ResponseEntity<ApiResponseDto> reportApproval(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long reportId) {
    ApiResponseDto result = reportService.reportApproval(userDetails.getUser(),reportId);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }
}
