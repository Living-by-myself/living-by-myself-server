package com.example.livingbymyselfserver.report;

import com.example.livingbymyselfserver.common.ApiResponseDto;
import com.example.livingbymyselfserver.user.User;
import com.example.livingbymyselfserver.user.UserRepository;
import com.example.livingbymyselfserver.user.UserRoleEnum;
import com.example.livingbymyselfserver.user.UserService;
import com.example.livingbymyselfserver.user.dto.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

  private final UserService userService;
  private final ReportRepository reportRepository;
  @Override
  public ApiResponseDto userReport(User user, ReportRequestDto requestDto, Long userId) {

    User defendant = userService.findUser(userId);
    Report report = new Report(defendant, user,requestDto.getDescription(),ApprovalEnum.DO_NOT_APPROVAL);

    reportRepository.save(report);

    return new ApiResponseDto("신고 성공", 201);
  }

  @Override
  @Transactional
  public ApiResponseDto reportApproval(User user, Long reportId) {

    if(!user.getRole().equals(UserRoleEnum.ADMIN))
      throw new IllegalArgumentException("관리자가 아니면 접근할 수 없는 서비스 입니다.");

    Report report = findReport(reportId);

    if(report.getApprovalEnum().equals(ApprovalEnum.DO_APPROVAL))
      throw new IllegalArgumentException("이미 승인된 신고건 입니다.");

    report.setApprovalEnum(ApprovalEnum.DO_APPROVAL);//신고 상태 바꿔주기

    User defendant = report.getDefendant(); //신고당한사람 가져오기

    defendant.setCurrentExp(defendant.getCurrentExp()-10L);  //경험치 10 감소

    if(defendant.getCurrentExp() < 0L) { //감소된 경험치가 0보다 낮아질 시 레벨을 감소시키고 경험치 재설정
      defendant.setLevel(defendant.getLevel()-1L);
      defendant.setCurrentExp(100L-defendant.getCurrentExp());
    }

    return new ApiResponseDto("승인했습니다.", 201);
  }

  private Report findReport(Long reportId){
    return reportRepository.findById(reportId).orElseThrow(()->
        new IllegalArgumentException("신고내역을 찾을 수 없습니다."));
  }

}
