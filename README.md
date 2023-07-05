# AsWeMake - 상품 판매 / 관리 서버

상품을 만들고 판매하며 간단하게 관리하고, 맘에 드는 상품을 주문 등록하고 결제까지 진행하는 서버 구축 프로젝트

### 🔍 주요 기능
<details>
<summary>계정 관리</summary>

  #### 계정 생성
  -  
  #### 계정 로그인
  - 

</details>
<details>
<summary>주문 관리</summary>

  #### 주문 등록
  -  
  #### 초기 총 금액 계산 및 조회
  - 

</details>
<details>
<summary>상품 관리</summary>

  #### 상품 생성
  -  
  #### 상품 가격 수정
  - 
  #### 상품 삭제
  - 
  #### 상품 조회
  - 

</details>
<details>
<summary>결제</summary>

  #### 최종 결제
  -  

</details>

### 🛠️ 시나리오
#### 1. 계정을 생성합니다. - /awm/account/create
   `MART` : 상품 생성, 가격 수정, 상품 삭제, 상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제 가능 권한의 계정
   `USER` : 상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제 가능 권한의 계정

#### 2. 만든 계정으로 로그인 합니다. - /awm/account/login
   로그인과 동시에 JWT 토큰이 발급됩니다. 
   해당 토큰은 인증이 필요한 api를 호출할 때마다 HTTP 요청과 함께 같이 요청됩니다.

#### 3. 


### 🗂️ ERD


### 🗂️ 기술스택
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/JWT Token-333333?style=for-the-badge&logo=JWT Token&logoColor=white"/>  
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"/> <img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=QueryDSL&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"/> <img src="https://img.shields.io/badge/Amazon S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white"/>  
<img src="https://img.shields.io/badge/Amazon EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/> <img src="https://img.shields.io/badge/WebRTC-333333?style=for-the-badge&logo=WebRTC&logoColor=white"/> <img src="https://img.shields.io/badge/openVidu-06d362?style=for-the-badge&logo=oepnVidu&logoColor=white"/> <img src="https://img.shields.io/badge/sockjs-333333?style=for-the-badge&logo=sockjs&logoColor=white"/> <img src="https://img.shields.io/badge/stomp-333333?style=for-the-badge&logo=stomp&logoColor=white"/>

### 🏗️ 프로젝트 아키텍처
![서비스 아키텍쳐 #중간발표](https://user-images.githubusercontent.com/112993031/204065939-8d25f487-30cb-43d0-ab3a-1a663ccf8335.png)
서비스 아키텍쳐 설명 <노션 링크> ▶ https://www.notion.so/Service-Architecture-a0a8b52c030641d59b1ad31fce0893a0
