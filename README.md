# <img src="https://user-images.githubusercontent.com/52437364/185876019-f544dd02-ab2e-4f10-af4e-43a5d970e4cd.png" align="left" width="40" height="40"> 좋구만유 - 문화유산 동행 APP
> **Cultural Heritage Companion Application, Joekoomanyoo**


<img src="https://user-images.githubusercontent.com/97875998/186781871-0b0237d8-7153-4dda-be06-d6125e05a6cc.png" align="left" width="90%" height="50%">

<br><br><br><br><br><br><br><br><br><br><br><br>

## 🗿 **Introduce**

> **좋구만유**는 사용자의 실시간 위치를 기반으로 문화 유산 정보 및 동행 서비스를 제공하는 프로젝트 입니다.

<br>

## 🧛 **팀원**
<table>
 <tr>
    <td align="center"><a href="https://github.com/dttmm"><img src="https://avatars.githubusercontent.com/dttmm" width="80px;" alt=""></td>
    <td align="center"><a href="https://github.com/mxxxxxji"><img src="https://avatars.githubusercontent.com/mxxxxxji" width="80px;" alt=""></td>
    <td align="center"><a href="https://github.com/kettle4ot"><img src="https://avatars.githubusercontent.com/kettle4ot" width="80px;" alt=""></td>
    <td align="center"><a href="https://github.com/us13579"><img src="https://avatars.githubusercontent.com/us13579" width="80px;" alt=""></td>
    <td align="center"><a href="https://github.com/Jaehwany"><img src="https://avatars.githubusercontent.com/Jaehwany" width="80px;" alt=""></td>
  </tr>
  <tr>
    <td align="center">팀장, AOS</td>
    <td align="center">AOS</td>
    <td align="center">AOS, UI 설계</td>
    <td align="center">Backend</td>
    <td align="center">Backend, Infra</td>
  </tr>
     <tr>
    <td align="center"><a href="https://github.com/mxxxxxji"><sub><b>김명지</b></td>
    <td align="center"><a href="https://github.com/dttmm"><sub><b>강태웅</b></td>
    <td align="center"><a href="https://github.com/kettle4ot"><sub>김수빈</b></td>
    <td align="center"><a href="https://github.com/us13579"><sub><b>김지수</b></td>
    <td align="center"><a href="https://github.com/Jaehwany"><sub><b>이재환</b></td>
  </tr>

</table>
     
<br>     

## 🕘 **프로젝트 진행 기간**

> **2022.07.07(목) ~ 2022.08.19(금)**

<br>

### 🎥 UCC 영상

- [UCC 바로가기](https://youtu.be/YujF8wheJro)

<br>

### 🧾 프로젝트 산출물

- [ERD](/docs/ERD.png)
- [API](/docs/API%20설계/API%20명세서.png)
- [시퀀스다이어그램](/docs/좋구만유%20시퀀스%20다이어그램.png)
- [와이어프레임](/docs/좋구만유%20와이어프레임.png)
- [플로우 차트](/docs/좋구만유%20플로우%20차트.png)
- **[중간 발표 PPT](/docs/중간%20발표%20PPT.pdf)**
- **[최종 발표 PPT](/docs/최종%20발표%20PPT.pdf)**

<br>   

## 🏴 아키텍처

![image](/docs/좋구만유%20아키텍쳐.png)

<br>

## 🏴 기술스택

| 기술스택      | 개발환경                                                     |
| ------------- | ------------------------------------------------------------ |
| Android      | - Kotlin 1.7.0 </br> - Room 2.4.2 </br>  - Retrofit 2.9.0 </br>  - Glide 4.13.2                                 |
| Spring Boot   | - Java 11 </br> - Gradle </br> - Spring Framwork 2.6.3 </br>  - Spring Data Jpa </br> - Spring Security |
| Authenticate  | - JWT (Json Web Token) </br>                                 |
| ORM           | - JPA </br> - QueryDsl                                       |
| Message       | - Firebase </br> - Stomp                                                     |
| Database      | - MySQL </br>                                  |


     
<br>   

 ## 🏴 주요 기능

- **문화 유산**

![image](https://user-images.githubusercontent.com/97875998/195622201-68b92b4f-b56a-4a69-b341-1354be2106c1.png)

     1. 사용자 위치 정보를 바탕으로 거리순으로 문화재 리스트 확인이 가능합니다.
      
     2. 문화 유산 상세 정보를 확인할 수 있습니다.
     
     3. 지도에서 문화 유산 위치를 확인할 수 있습니다

     4. 마음에 드는 문화 유산을 스크랩할 수 있습니다

     5. 문화 유산을 모임에 공유할 수 있습니다

     6. 문화 유산에 대한 리뷰를 작성할 수 있습니다

<br>

- **모임**

![image](https://user-images.githubusercontent.com/97875998/195621006-a4eb804c-5dbd-4b26-ba31-b94c7249a4d2.png)


     1. 문화유산 동행을 위한 모임 개설 or 가입이 가능합니다

     2. 모임원들과 실시간 채팅을 통해 모임 계획을 세울 수 있습니다

     3. 모임 일정을 등록하고, 모임 목적지를 설정할 수 있습니다

     4. 모임 후 모임원 간 상호 평가를 통해 모임원을 칭찬할 수 있습니다

<br>

- **사진 피드**

![image](https://user-images.githubusercontent.com/97875998/195621212-0fdeefc3-853b-45ba-b5de-37210d6cff11.png)

     1. 사진 피드를 등록하여 자신이 찍은 문화 유산 사진을 다른 사용자와 공유할 수 있습니다

     2. 좋아요 기능을 통해 사진에 대한 호감을 표할 수 있습니다



<br>

- **AR 방문 인증**

![image](https://user-images.githubusercontent.com/97875998/195621406-6424ec28-01c3-4371-b0fb-7bd90f10cebc.png)

     1. AR 기능을 이용하여 등록된 문화 유산 스탬프를 수집할 수 있습니다

     2. 도감을 통해 문화유산 카테고리별로 스탬프 수집 현황을 볼 수 있습니다

     3. 순위 기능을 통해 스탬프 수집에 대한 성취감을 얻을 수 있습니다

</br></br>

 ## 🏴 서비스 시연

</br>

> **문화 유산** - 위치 기반 목록 조회, 카테고리, 리뷰, 검색 기능

<div>
  <img src="https://user-images.githubusercontent.com/66373647/186418969-96825ca7-25be-449e-bee1-e360bb392e58.gif" width="180px" height="360px;" alt="">
  <img src="https://user-images.githubusercontent.com/66373647/186419085-b95ec474-1dcb-4b14-99ba-a65007d15543.gif" width="180px" height="360px;" alt="">
  <img src="https://user-images.githubusercontent.com/66373647/186419365-9c77b5c7-18cd-4c03-926b-e0e0e0ca6104.gif" width="180px" height="360px;" alt="">
  <img src="https://user-images.githubusercontent.com/66373647/186419826-f30e6189-91d1-4de1-b2d0-ad8f001f919e.gif" width="180px" height="360px;" alt="">
</div>

<br><br>

 > **모임 기능** - 모임 카테고리별 검색, 모임 참가, 모임 목적지 등록, 모임 채팅, 모임원간 상호평가


<div>
  <img src="https://user-images.githubusercontent.com/66373647/186419176-2f866652-61aa-401b-ad57-194128a1ce1b.gif" width="180px" height="360px;" alt="">
  <img src="https://user-images.githubusercontent.com/66373647/186419431-e6893d09-e6ef-4af3-82df-17c8e0cda515.gif" width="180px" height="360px;" alt="">
</div>

  <br><br>

 > **사진 피드** - 해쉬태그 검색, 좋아요, 피드 조회


<div>
  <img src="https://user-images.githubusercontent.com/66373647/186419212-b93f76d8-1509-4932-80c7-9d9f3afa4158.gif" width="180px" height="360px;" alt="">
  <img src="https://user-images.githubusercontent.com/66373647/186419248-f0f013b0-5da6-4ba0-96bc-95bbc3aa4d44.gif" width="180px" height="360px;" alt="">
</div>

   <br><br>

 > **AR 기능** - AR 유물 스탬프 찾기, AR 도감, AR 스탬프 랭킹



<div>
   <img src="https://user-images.githubusercontent.com/66373647/186428945-89fce42b-b23e-4bdd-aeb1-bd26a4ca94fc.gif" width="180px" height="360px;" alt="">
   <img src="https://user-images.githubusercontent.com/66373647/186429270-b222c4d2-eb58-48c0-8a9f-aefd92697348.gif" width="180px" height="360px;" alt="">
</div>
