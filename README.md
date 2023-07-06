# AsWeMake - 상품 판매 / 관리 / 결제 서버

상품을 만들고 판매하며 간단하게 관리하고, 맘에 드는 상품을 주문 등록하고 결제까지 진행하는 서버
  
### 🗂️ 주요 기능
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

##

### 🗂️ 시나리오
#### 1. 계정을 생성합니다.
   이메일, 비밀번호, 권한 정보를 토대로 계정이 생성됩니다.  
   - `MART`  
   상품 생성, 가격 수정, 상품 삭제, 상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제 가능 권한의 계정  
   - `USER`  
   상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제 가능 권한의 계정

#### 2. 만든 계정으로 로그인 합니다.
   로그인과 동시에 JWT 토큰이 발급됩니다.  
   해당 토큰은 인증이 필요한 api를 호출할 때마다 HTTP 요청과 함께 같이 요청됩니다.

#### 3. MART 권한의 계정으로 로그인한 경우 / USER 권한의 계정으로 로그인한 경우
  - `MART 계정`  
  상품 생성, 가격 수정, 상품 삭제, 상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제 모든 기능을 사용할 수 있습니다.  
  - `USER 계정`  
  상품 조회, 주문 등록, 초기 총 금액 계산, 최종 결제와 같은 일부 기능들을 사용할 수 있습니다.
  
#### 4. 상품 관리
  - `상품 생성`  
  상품 이름, 가격 정보를 가지고 상품이 생성됩니다.  
  - `상품 가격 수정`  
  상품 id, 수정할 가격 정보들을 가지고 해당 상품의 가격을 수정합니다.  
  수정을 진행하게되면 상품 이력 정보(ProductUpdateDetails)가 추가됩니다.  
  - `상품 삭제`  
  상품 id를 가지고 해당 상품을 삭제합니다.  
  - `상품 조회`  
  조회할 상품 id, 조회할 상품의 특정 시점 정보를 가지고 해당 상품을 조회합니다.  
  특정 시점 정보는 필수가 아니며, 없을 경우 가장 최신의 상품의 가격 및 상품 정보를 조회합니다.  
  특정 시점 정보가 존재할 경우, 상품 정보 이력 중에서 해당 시점의 가격 및 상품 정보를 조회합니다.  

#### 5. 주문 관리 
  - `주문 등록`  
  모든 계정들은 선택한 여러 상품들의 id와 개수 정보를 가지고 주문 등록시킬 수 있습니다.  
  하나의 상품이든 다수의 상품이든 상관없이 등록시킬 수 있습니다.  
  - `주문 총 금액 계산 및 조회`  
  모든 계정들은 선택한 주문 id 정보를 가지고 해당 주문의 총 금액을 계산하고 조회할 수 있습니다.  
  명심해야할 것은, 쿠폰이 적용되지 않은 초기 총 금액이라는 것입니다.  

#### 6. 쿠폰
  할인할 수 있는 쿠폰 두 개가 준비되어있습니다.  
  - `비율 할인 쿠폰`  
  상품의 가격에 일정 비율을 할인합니다.  
  - `고정 할인 쿠폰`  
  상품의 가격에 고정된 일정 금액을 할인합니다.  
  
#### 7. 결제
  모든 계정들은 선택한 주문의 id, 지불 금액, 쿠폰 사용 여부, 사용할 쿠폰 id, 쿠폰 적용 범위, 특정 상품에만 적용할 경우 필요한 특정 상품 id 정보들을 가지고 최종 결제를 진행하게 됩니다.  
    쿠폰 사용 여부가 "O" 일 경우에는 사용할 쿠폰 id, 쿠폰 적용 범위가 반드시 포함되어있습니다.  
  쿠폰 적용 범위가 전체(ALL)일 경우에 특정 상품 id 정보값은 0이며 선택 주문의 총 금액에 쿠폰 할인이 적용됩니다.  
  쿠폰 적용 범위가 특정(SPECIFIC)일 경우에 특정 상품 id 정보값이 존재하며 주문의 선택 특정 상품의 금액에 쿠폰 할인이 적용됩니다.  
  쿠폰 사용 여부가 "X" 일 경우에는 쿠폰 사용 여부, 사용할 쿠폰 id, 쿠폰 적용 범위, 특정 상품 id 정보들이 필요하지 않습니다.  
  만약 지불 금액이 계산된 총 금액보다 낮을 경우 결제할 수 없습니다.  
  정상적으로 결제가 될 경우에 총 금액, 지불 금액, 잔액, 쿠폰 사용 여부, 할인된 가격 정보들이 조회되게 됩니다.  
  또한, 결제 내역(PaymentDetails)가 저장됩니다.

##

### 🗂️ 실행 방법
!! 개발 툴은 IntellJ로 진행하였습니다.
#### (1) 프로젝트를 clone
#### (2) application.properties 파일 설정  
![ddd](https://github.com/JayEsEichi/AsWeMake/assets/96898059/1efe2b61-e6b3-42f1-abe0-895606de1290)
MySQL 드라이버 설정과 datasource.url, DB 계정명, DB 비밀번호와 같은 전체적인 MySQL 설정을 해줍니다.  
JWT를 사용하고 있기 때문에 JWT 암호화를 위한 secret key도 넣어주었습니다.
#### (3) MySQL 데이터 베이스 연동  
![dfsfd](https://github.com/JayEsEichi/AsWeMake/assets/96898059/802b5727-c3e7-4a5d-962d-726b258dcb22)
오른쪽 사이트 탭에 보이는 Database 탭을 클릭하고 + 버튼을 눌러 MySQL 드라이버를 찾아 선택해줍니다.  
`Name` : mysql 드라이버 명을 입력해줍니다.  
`Host` : 로컬에서 진행하는 것이니만큼 localhost 와 포트 번호 3306으로 지정해줍니다.  
`User` : 데이터 베이스 아이디를 입력합니다.  
`Password` : 데이터 베이스 비밀번호를 입력합니다.  
`Database` : application.properties 파일의 url 부분에 기입했던 데이터베이스 명과 동일한 데이터베이스를 입력해줍니다.  
#### (4) Application 실행
어플리케이션을 실행해줍니다.  
이제 실행이 된 상태에서 시나리오에 기입한 내용 순서대로 api를 호출하여 서비스를 실행하면 됩니다.  
서비스 실행 테스트는 PostMan으로 진행하였습니다.  
#### (5) PostMan 서비스 실행  
![1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/e7cb145a-f4b4-43bd-ab3a-2085817f1570)  
postman에 import를 눌러줍니다.  
![2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/2dd1d6fe-5512-4646-8e55-fc20b1a48542)  
첨부드린 json 파일을 그대로 import 해줍니다.  
![3](https://github.com/JayEsEichi/AsWeMake/assets/96898059/0517a72b-2588-47ca-8dbe-800af56d8ebe)  
collection이 그대로 생성되게 되며 어플리케이션 실행 후 각 서비스를 호출하여 시나리오 대로 실행하면 됩니다.




##

### 🗂️ API 명세
http://localhost:8080/swagger-ui/index.html#/  
(어플리케이션을 정상적으로 실행시킨 상태에서 위의 주소로 접속하게 되면 Swagger를 통한 API 명세를 확인할 수 있습니다.)

##

### 🗂️ ERD
![erd](https://github.com/JayEsEichi/AsWeMake/assets/96898059/641b49ac-2233-4653-9ae0-0f301c502f00)
<details>
<summary>Account</summary>
  
  - `accountId` : 계정 고유 id  
  - `accountEmail` : 계정 이메일 아이디  
  - `accountPwd` : 계정 비밀번호
  
</details>
<details>
<summary>AccountAithority</summary>
  
  - `accountAccountId` : 계정 고유 id
  - `authority` : 계정 권한
  
</details>
<details>
<summary>Token</summary>
  
  - `tokenId` : 토큰 고유 id  
  - `grantType` : Bearer 권한 타입  
  - `accessToken` : 액세스 토큰
  - `refreshToken` : 리프레시 토큰
  - `accountId` : 토큰이 발급된 계정 고유 id (Foreign Key)
  
</details>
<details>
<summary>Product</summary>
  
  - `productId` : 상품 고유 id
  - `productName` : 상품 이름
  - `price` : 상품 가격
  - `createdAt` : 생성 일자
  - `modifiedAt` : 변경 일자
  - `accountId` : 계정 고유 id (Foreign Key)  
  
</details>
<details>
<summary>ProductUpdateDetails</summary>
  
  - `productUpdateDetailsId` : 상품 정보 이력 고유 id
  - `productName` : 상품 이름
  - `price` : 상품 가격
  - `updateTime` : 상품 가격 업데이트 일자
  - `productId` : 상품 고유 id (Foreign Key)
  
</details>
<details>
<summary>Orders</summary>
  
  - `ordersId` : 주문 고유 id
  - `deliveryPay` : 배달비
  - `totalPrice` : 초기 총 금액
  - `accountId` : 주문 등록한 계정의 고유 id (Foreign Key)

</details>
<details>
<summary>OrdersProducts</summary>
  
  - `ordersOrdersId` : 주문 고유 id
  - `productKey` : 주문에 등록된 상품의 고유 id
  - `productCount` : 주문 상품 개수  

</details>
<details>
<summary>coupon</summary>
  
  - `couponId` : 쿠폰 고유 id  
  - `couponType` : 쿠폰 타입 (비율 / 고정)
  - `discountContent` : 할인 금액 내용
  
</details>
<details>
<summary>PaymentDetails</summary>
  
  - `paymentDetailsId` : 결제 내역 고유 id  
  - `totalPrice` : 총 금액 
  - `paymentCost` : 지불 금액
  - `reaminCost` : 잔액
  - `couponWheter` : 쿠폰 사용 여부 (O / X)
  - `discountPrice` : 할인 금액
  - `ordersId` : 결제할 주문의 고유 id
  - `couponId` : 쿠폰을 사용할 경우의 해당 쿠폰 고유 id
  
</details>

##

### 🗂️ 기술스택
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/JWT Token-333333?style=for-the-badge&logo=JWT Token&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> jpa Swagger  
https://shields.io/badges

##

### 🏗️ 프로젝트 아키텍처
![서비스 아키텍쳐 #중간발표](https://user-images.githubusercontent.com/112993031/204065939-8d25f487-30cb-43d0-ab3a-1a663ccf8335.png)
