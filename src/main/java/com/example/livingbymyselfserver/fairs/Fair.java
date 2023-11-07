package com.example.livingbymyselfserver.fairs;

import com.example.livingbymyselfserver.fairs.dto.FairRequestDto;
import com.example.livingbymyselfserver.like.entity.FairLikePick;
import com.example.livingbymyselfserver.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "fair")
@NoArgsConstructor
@DynamicUpdate
public class Fair {
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
  private FairShareEnum enumShare;  //나눔인지 공동구매인지

  @Column(nullable = false)
  private String address; //주소

  @Column(nullable = false)
  private double lat;   //구매자 설정 위도

  @Column(nullable = false)
  private double lng;   //구매자 설정 경도

  @OneToMany(mappedBy = "fair", cascade = CascadeType.REMOVE)
  private List<FairLikePick> likePickList = new ArrayList<>();


  @ManyToOne
  @JoinColumn(name = "user_id")
  private User host;
  public Fair(FairRequestDto requestDto,User user){
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.itemLink = requestDto.getItemLink();
    this.maxUser = requestDto.getMaxUser();
    this.perUserPrice = requestDto.getPerUserPrice();
    this.address = requestDto.getAddress();
    this.enumShare = requestDto.getEnumShare();
    this.lat = requestDto.getLat();
    this.lng =requestDto.getLng();
    this.host = user;
  }
  public void updateFair(FairRequestDto requestDto){
    this.title = requestDto.getTitle();
    this.description = requestDto.getDescription();
    this.itemLink = requestDto.getItemLink();
    this.maxUser = requestDto.getMaxUser();
    this.perUserPrice = requestDto.getPerUserPrice();
    this.address = requestDto.getAddress();
    this.enumShare = requestDto.getEnumShare();
    this.lat = requestDto.getLat();
    this.lng =requestDto.getLng();
  }
}
