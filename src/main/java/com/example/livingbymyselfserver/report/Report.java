package com.example.livingbymyselfserver.report;

import com.example.livingbymyselfserver.common.entity.TimeStamped;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_reports")
public class Report extends TimeStamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id")
  private Long id;

  @Column(name = "description", nullable = false)
  private String description;

  //신고 당한 사람
  @ManyToOne
  @JoinColumn(name = "defendant_id")
  private User defendant; //피고, 신고당한 사람

  @ManyToOne
  @JoinColumn(name = "plaintiff_id")
  private User plaintiff;//원고, 신고한 사람

  @Enumerated(value = EnumType.STRING)
  private ApprovalEnum approvalEnum;

  public void setApprovalEnum(ApprovalEnum approvalEnum){
    this.approvalEnum = approvalEnum;
  }

  public Report(User defendant, User plaintiff, String description, ApprovalEnum approvalEnum) {
    this.description = description;
    this.defendant = defendant;
    this.plaintiff = plaintiff;
    this.approvalEnum = approvalEnum;
  }
}