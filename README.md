![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=200&section=header&text=혼자살때&fontSize=70)


스프링 부트 자취생 공동구매/꿀팁공유 사이트
----
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FBSB99%2FLiving-MySelf&count_bg=%23555B51&title_bg=%2367DA22&icon=spring.svg&icon_color=%23E7E7E7&title=view&edge_flat=false)](https://hits.seeyoufarm.com)

---

## 💫프로젝트 팀원

##### 김슬기

* https://github.com/wisdomSG
* https://wisdomsg.tistory.com/
* 추가사항

##### 백승범

* https://github.com/BSB99?tab=repositories
* https://an-unreachable-world.tistory.com/
* 추가사항

##### 강정훈

* https://github.com/skah1061
* https://codeplace.tistory.com/
* 추가사항

## 📦혼자살때

---

> ##### 스파르타 커리어톤 11월 Spring
> ##### 개발기간 : 2023/11/06 ~ 2023/11/17

## 📇배포 주소

---
> 웹사이트 주소 : https://tracelover.shop/


## 🙇‍♂️프로젝트 소개

---

#### 혼자살때

###### 자취 꿀팁을 알려주는 커뮤니티 및 공동 구매가 가능한 쇼핑을 할 수 있는 서비스

## 🔥버전

---

### Requirements

###### For building and running the application you need

* SpringBoot 3.1.2
* Java 17
* Redis
* Jwt 0.11.5
* S3 2.2.6
* coolsms 2.2
* WebSocket 1.1.2
* StompWebsocket 2.3.3-1

## 🐈‍⬛STACK

---

### Installation

    $ git clone https://github.com/Living-by-myself/living-by-myself-server.git
    $ cd Living-by-myself

##### Environment

<img src="https://img.shields.io/badge/gitHub-'181717'?style=for-the-badge&logo=gitHub&logoColor=white">    <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">    <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">

##### Development

<img src="https://img.shields.io/badge/Java-61DAFB?style=for-the-badge&logo=Java&logoColor=black">    <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">    <img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">    <img src="https://img.shields.io/badge/apachekafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white">    <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">    <img src="https://img.shields.io/badge/awslambda-FF9900?style=for-the-badge&logo=awslambda&logoColor=white">

##### Data base

<img src="https://img.shields.io/badge/MySql-4479A1?style=for-the-badge&logo=MySql&logoColor=white">    <img src="https://img.shields.io/badge/amazonrds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

##### Communication

<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">    <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">    <img src="https://img.shields.io/badge/kakaotalk-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=white">

##### AWS

<img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">    <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">    <img src="https://img.shields.io/badge/amazonroute53-8C4FFF?style=for-the-badge&logo=amazonroute53&logoColor=white">    <img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">

## 📦Trouble Shooting

---

- CORS
  - 기존 방식 및 문제점
    - 서버에서 기본적으로 모든 요청을 허용하는 Default CORS 설정을 사용.
    - 프론트에서 Token을 이용한 API 요청을 보낼 때, CORS Preflight 에러가 발생하는 상황 발견.
  - 해결 방안: `서버에 CORS Preflight 관련 설정 추가`
    - 서버에 CORS Preflight 관련 설정을 추가하여 보다 정교한 CORS 관리를 실시.
    - 프론트 측의 URL만이 요청을 할 수 있도록 제한 설정을 적용.
    - Preflight 설정을 통해 보안 측면에서 안정적인 통신으로 변경.

- Docker와 Github Action을 사용한 CI/CD
  - 기존 방식 및 문제점
    - 기존에는 백엔드 서버만을 컨테이너로 실행, **`Redis`**는 직접 서버에 설치하는 방식을 채용 했지만 이 방법은 도커의 다양한 기능을 활용하지 않고, 서비스가 많아 질 때 버전별로 관리하기가 어렵다는 문제가 발생
  - 해결 방안: `Docker-Compose를 이용한 다중 컨테이너 작업`
    - Docker-Compose 도입 :**`Docker-Compose`**를 도입하여 여러 컨테이너를 효율적으로 관리, **`kafka`**와 같은 다른 서비스도 손쉽게 통합할 수 있는 환경을 구축, 이를 통해 서버 배포의 효율성과 확장성을 크게 향상

- Kafka, SSE 결합한 알림기능
  - 기술 도입 배경:
    - 실시간 이벤트 전달 필요성 : 다양한 이벤트들을 실시간으로 전달해야 함
    - 알림 메시지 안전한 관리와 데이터 백업 필요성
    - SSE의 간편성과 효율성
      - SSE는 WebSocket에 비해 구현이 간단하고 유지보수가 쉬움
      - 단방향 통신으로 서버에서 클라이언트로 메시지를 보낼 수 있어, 실시간 알림 기능에 적합
  - 해결 방안
    - Kafka와 SSE의 통합 활용 및 상호작용
      - Kafka를 활용하여 대량의 이벤트 데이터를 안정적으로 수집하고 분산환경에서 처리
      - Kafka에서 발생한 이벤트를 SSE로 실시간으로 전달하는 파이프라인을 구축

- WebSocket 다대다 채팅을 위한 다대다 연관관계
  - 기존 방식 및 문제점
    - 이전 프로젝트에서는 채팅 기능이 두 개의 사용자 엔터티 **`guest_id`** 및 **`host_id`**를 결합하여 구현된 일대일 상호 작용으로 제한되었고, 이 디자인 선택은 심각한 문제를 야기했습니다. 일대일 이상의 채팅은 지원되지 않았습니다.
  - 해결방안
    - 다대다 연결을 지원하도록 시스템을 재구성하여 여러 사용자가 채팅방에 참여할 수 있도록 했습니다.
    - 'Set' 데이터 구조를 활용하여 사용자 연결을 관리하고 중복 항목을 효과적으로 제거했습니다.
    - 한 채팅방에서 여러 유저가 Chat메세지를 등록 할 수 있도록 연관관계를 수정.
    - 채팅 경험을 향상하고 제한 사항을 해결하기 위해 다음 솔루션이 구현되었습니다.

## 📦주요기능

---


- 회원 기능
    - 회원가입, 로그인, 로그아웃 ( Redis 사용 )
    - 카카오, 구글 로그인 ( OAuth 로그인 )
    - 휴대폰 인증 (비밀번호 찾기)
    - cash 기능
    - user 프로필 조회 및 수정 기능
    - 뱃지와 레벨 기능
- 알림 기능 ( Kafka, SSE를 결합)
- 실시간 채팅 기능
- 커뮤니티 - 자취꿀팁 공유
    - 게시물 CRUD, 댓글 CRUD
    - 좋아요 기능
    - 특정 조건 달성시 뱃지 추가 기능
- 공동 구매 기능
    - 게시물 CRUD
    - 관심등록 기능 (찜)
    - 공동 구매 참여 기능
    - 결제 기능
    - 특정 조건 시 경험치를 얻어 레벨이 올라가는 기능
- Lambda를 이용한 이미지 리사이징 기능
- S3를 이용한 이미지 업로드 기능

## API

---

> #### PostManUrl : https://documenter.getpostman.com/view/27465259/2s9YXiaNJo 

## 🗺️ERD

---

![혼자살때 ERD](https://github.com/Living-by-myself/living-by-myself-server/assets/81159848/4292bc17-5740-4923-8917-7938f9efb1b8)

[//]: # (## 🧭서비스 아키텍처)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (![meongnyangbook]&#40;https://github.com/BSB99/Project-MeongNyangBook/assets/81159848/724b180b-13d0-4199-a692-304860718a89&#41;)