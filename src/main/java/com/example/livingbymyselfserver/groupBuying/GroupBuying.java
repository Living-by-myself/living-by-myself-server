package com.example.livingbymyselfserver.groupBuying;

import com.example.livingbymyselfserver.common.entity.TimeStamped;
import com.example.livingbymyselfserver.groupBuying.application.ApplicationUsers;
import com.example.livingbymyselfserver.groupBuying.dto.GroupBuyingRequestDto;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingCategoryEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingShareEnum;
import com.example.livingbymyselfserver.groupBuying.enums.GroupBuyingStatusEnum;
import com.example.livingbymyselfserver.groupBuying.pickLike.GroupBuyingPickLike;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "group_buying")
@NoArgsConstructor
@DynamicUpdate
public class GroupBuying extends TimeStamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String description;

  @Column
  private String itemLink; //구매물품 링크

  @Column(nullable = false)
  private Integer maxUser;  //모집하는 유저

  @Column(nullable = false)
  private Integer perUserPrice;  //인당 낼 돈

  @Enumerated(value = EnumType.STRING)
  private GroupBuyingShareEnum enumShare;  //나눔인지 공동구매인지

  @Enumerated(value = EnumType.STRING)
  private GroupBuyingStatusEnum enumStatus;  //마감상태

  @Enumerated(value = EnumType.STRING)
  private GroupBuyingCategoryEnum enumCategory;  //카테고리

  @Column(nullable = false)
  private double viewCnt = 0;

  @Column(nullable = false)
  private String address; //주소

  @Column(nullable = false)
  private String beobJeongDong; //주소

  @Column(nullable = false)
  private double lat;   //구매자 설정 위도

  @Column(nullable = false)
  private double lng;   //구매자 설정 경도

  @OneToMany(mappedBy = "groupBuying", cascade = CascadeType.REMOVE)
  private List<GroupBuyingPickLike> pickLikeList = new ArrayList<>();

  @OneToMany(mappedBy = "groupBuying", cascade = CascadeType.REMOVE)
  private List<ApplicationUsers> appUsers = new ArrayList<>();

  @ManyToOne
  @JoinColumn(name = "host_id")
  private User host;

  public void setStatus(GroupBuyingStatusEnum groupBuyingStatusEnum){
    this.enumStatus = groupBuyingStatusEnum;
  }
  public void setViewCnt(double viewCnt){
    this.viewCnt = viewCnt;
  }
  public GroupBuying(GroupBuyingRequestDto requestDto,User user){
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.itemLink = requestDto.getItemLink();
    this.maxUser = requestDto.getMaxUser();
    this.perUserPrice = requestDto.getPerUserPrice();
    this.address = requestDto.getAddress();
    this.beobJeongDong = requestDto.getBeobJeongDong();
    this.enumShare = requestDto.getEnumShare();
    this.enumStatus = GroupBuyingStatusEnum.ONGOING;
    this.enumCategory = requestDto.getEnumCategory();
    this.lat = requestDto.getLat();
    this.lng =requestDto.getLng();
    this.host = user;
  }
  public void updateGroupBuying(GroupBuyingRequestDto requestDto){
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.itemLink = requestDto.getItemLink();
    this.maxUser = requestDto.getMaxUser();
    this.perUserPrice = requestDto.getPerUserPrice();
    this.address = requestDto.getAddress();
    this.beobJeongDong = requestDto.getBeobJeongDong();
    this.enumShare = requestDto.getEnumShare();
    this.enumCategory = requestDto.getEnumCategory();
    this.lat = requestDto.getLat();
    this.lng =requestDto.getLng();
  }
}
