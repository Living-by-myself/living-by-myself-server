package com.example.livingbymyselfserver.groupBuying.dto;

import com.example.livingbymyselfserver.attachment.entity.AttachmentGroupBuyingUrl;
import com.example.livingbymyselfserver.groupBuying.GroupBuying;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class GroupBuyingUserResponseDto {
  private Long groupBuyingId;
  private String title;
  private Integer maxUser; // 모아야되는 사람
  private Integer currentUser;
  private String fileUrls;


  public GroupBuyingUserResponseDto(GroupBuying groupBuying,
      AttachmentGroupBuyingUrl attachmentGroupBuyingUrl) {
    this.groupBuyingId = groupBuying.getId();
    this.title = groupBuying.getTitle();
    this.maxUser = groupBuying.getMaxUser();
    this.currentUser = groupBuying.getAppUsers().size();
    this.fileUrls = attachmentGroupBuyingUrl.getFileName().split(",")[0];
  }

  public GroupBuyingUserResponseDto(GroupBuying groupBuying) {
    this.groupBuyingId = groupBuying.getId();
    this.title = groupBuying.getTitle();
    this.maxUser = groupBuying.getMaxUser();
    this.currentUser = groupBuying.getAppUsers().size();
  }
}
