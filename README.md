# AsWeMake - 상품 판매 / 관리 / 결제 서버

상품을 만들고 판매하며 간단하게 관리하고, 맘에 드는 상품을 주문 등록하고 결제까지 진행하는 서버
  
### 🗂️ 주요 기능
<details>
<summary>계정 관리</summary>

  ### 계정 생성
  #### [ Request ]  
  ![계정 생성 request](https://github.com/JayEsEichi/AsWeMake/assets/96898059/73376734-cbca-4fc9-802d-5a2f4a121f64)  
  - url : /awm/account/create
  - method : POST
  - 요청 데이터 형식 : request body (json)
    - accountEmail : 생성할 계정 이메일 아이디
    - accountPwd : 생성할 계정의 비밀번호
    - authority : 계정의 권한
   
  #### [ Response ]  
  ![계정 생성 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/c6db076c-bc23-4a24-881a-63780adfa3d8)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - accountEmail : 생성된 계정 이메일
          - authority : 생성된 계정의 권한
         
  ##

  ### 계정 로그인
  #### [ Request ]  
  ![로그인 request](https://github.com/JayEsEichi/AsWeMake/assets/96898059/11dfbf7c-dac9-47a3-ae90-529e368999d7)  
  - url : /awm/account/login
  - method : GET
  - 요청 데이터 형식 : request body (json)
    - accountEmail : 로그인할 계정 이메일 아이디
    - accountPwd : 로그인할 계정의 비밀번호
   
  #### [ Response ]  
  ![로그인 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/b5c1b32c-57a9-4c79-a7f9-5f9602a3d748)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - tokenId : 로그인 후 발급된 토큰의 id
          - grantType : Bearer 토큰 타입
          - accessToken : JWT 액세스 토큰
          - refreshToken : JWT 리프레시 토큰
          - accountId : 로그인한 계정의 고유 id

</details>
<details>
<summary>주문 관리</summary>

  ### 주문 등록
  #### [ Request ]  
  ![주문 상품 등록 request 1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/0d469d85-2220-440e-943c-0ff603e992e8)  
  ![주문 상품 등록 request 2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/34b589d5-9f14-4f86-96e8-868143639ec8)  
  - url : /awm/order/ordering
  - method : POST
  - HTTP Header :  
    - Authorization : JWT 액세스 토큰
    - Refresh-Token : JWT 리프레시 토큰
  - 요청 데이터 형식 : request part (json list) - 다수의 상품을 요청할 수 있습니다.
    - productCount : 주문 등록할 상품의 개수
    - productId : 주문 등록할 상품의 id
    
  #### [ Response ]  
  ![주문 상품 등록 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/90f7b374-3291-4ace-afd7-36d041ffe329)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - accountEmail : 주문 등록한 계정의 이메일
          - deliveryPay : 배달비 (배달비는 5000원으로 고정이며 모든 주문에 포함됩니다.)  
          - orderProductList : 주문 등록한 상품들  
            - productId : 주문 등록한 상품의 id
            - productName : 주문 등록한 상품의 이름
            - price : 주문 등록한 상품의 가격
            - productCount : 주문 등록한 상품의 요청 개수

  ##
  
  ### 초기 총 금액 계산 및 조회  
  #### [ Request ]  
  ![주문 총 금액 계산 및 조회 request](https://github.com/JayEsEichi/AsWeMake/assets/96898059/08b79296-2652-469a-b7ec-06b973ba8cf1)  
  - url : /awm/order/calculate
  - method : PATCH
  - 요청 데이터 형식 : request parameter
    - ordersId : 총 금액을 계산하고 조회할 주문의 id
   
  #### [ Response ]  
  ![주문 총 금액 계산 및 조회 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/0bb2d898-fe27-470c-a937-62593ac5d04a)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터. 선택한 주문의 총 금액 조회

</details>
<details>
<summary>상품 관리</summary>

  ### 상품 생성
  #### [ Request ]  
  ![상품 생성 request 1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/c90a56b5-0dc7-420b-8db1-ed09e7079fa1)  
  ![상품 생성 request 2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/51f3dfef-4c78-464f-894e-f61bdac08a12)  
  - url : /awm/product/create
  - method : POST
  - HTTP Header :  
    - Authorization : JWT 액세스 토큰
    - Refresh-Token : JWT 리프레시 토큰
  - 요청 데이터 형식 : request body (json)
    - productName : 생성할 상품의 이름
    - price : 생성할 상품의 가격
   
  #### [ Response ]  
  ![상품 생성 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/1536aa72-7426-44be-b393-43f076e84cde)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - productName : 생성된 상품의 이름
          - price : 생성된 상품의 가격
          
  ##
  
  ### 상품 가격 수정
  #### [ Request ]  
  ![상품 수정 request 1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/0aed828e-2e17-4949-bb20-6860f04d2a4e)  
  ![상품 수정 request 2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/43ce5cc2-b81f-4d59-813d-b40f85ab91c7)  
  - url : /awm/product/update
  - method : PATCH
  - HTTP Header :  
    - Authorization : JWT 액세스 토큰
    - Refresh-Token : JWT 리프레시 토큰
  - 요청 데이터 형식 : request body (json)
    - productId : 수정할 상품의 id
    - price : 수정할 가격
   
  #### [ Response ]  
  ![상품 수정 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/03fe6491-9219-4a6e-968b-26973000a76e)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - productName : 수정된 상품의 이름
          - price : 수정된 상품의 가격

  ##
  
  ### 상품 삭제
  #### [ Request ]  
  ![상품 삭제 request 1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/4de2c36c-7b16-419d-af53-b0d7ebd4f486)  
  ![상품 삭제 request 2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/3bc6a9bd-cf3c-4ab4-b328-440b6acc01a6)  
  - url : /awm/product/delete
  - method : DELETE
  - HTTP Header :  
    - Authorization : JWT 액세스 토큰
    - Refresh-Token : JWT 리프레시 토큰
  - 요청 데이터 형식 : request body (json)
    - productId : 삭제할 상품의 id
   
  #### [ Response ]  
  ![상품 삭제 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/5cafd163-9ddf-47ad-a952-f01ea95cecf6)
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터. 정상적으로 삭제되었음을 알리는 문구 반환.

  ##
  
  ### 상품 조회
  #### [ Request ]  
  ![상품 조회 request](https://github.com/JayEsEichi/AsWeMake/assets/96898059/428bb271-7384-45d8-b00b-5459a93992f9)  
  - url : /awm/product/get
  - method : GET
  - 요청 데이터 형식 : request parameter
    - productId : 조회할 상품의 id
    - getDataTime : 조회할 특정 시점 (필수가 아니며 없으면 최신의 데이터를 조회, 있으면 특정 시점의 데이터를 조회)
   
  #### [ Response ]  
  ![상품 조회 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/65b9b56f-6471-41c9-bc9a-26fdc8cac376)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - productName : 조회할 상품의 이름
          - price : 조회할 상품의 가격 (특정 시점이 존재하면 해당 시점의 가격을 조회)

</details>
<details>
<summary>결제</summary>

  ### 최종 결제
  #### [ Request ]  
  ![결제 request 1](https://github.com/JayEsEichi/AsWeMake/assets/96898059/d48a8023-6c56-419a-8f36-dccac0fba47f)  
  ![결제 request 2](https://github.com/JayEsEichi/AsWeMake/assets/96898059/c65aa07b-667f-44ef-846f-8b685b6ae3db)  
  - url : /awm/pay
  - method : POST
  - HTTP Header :  
    - Authorization : JWT 액세스 토큰
    - Refresh-Token : JWT 리프레시 토큰
  - 요청 데이터 형식 : request body (json)
    - ordersId : 결제할 주문의 id
    - paymentCost : 지불 금액
    - couponWhether : 쿠폰 사용 여부 (O / X)
    - couponId : 쿠폰을 사용할 경우 쿠폰 id
    - couponScope : 쿠폰 적용 범위 (ALL / SPECIFIC)
    - specificProductId : 쿠폰 적용 범위가 특정 상품(SPECIFIC)일 경우 해당 되는 특정 상품 id
   
  #### [ Response ]  
  ![결제 response](https://github.com/JayEsEichi/AsWeMake/assets/96898059/322752f0-8a9b-4de7-bf1c-7f243c18e97b)  
  - 반환 데이터 형식
    - statusMessage : 처리 상태 메세지
    - statusCode : 처리 상태 코드
    - data : 반환될 데이터
        - resultMessage : 응답 메세지
        - resultData : 최종 반환 데이터
          - totalPrice : 총 금액
          - paymentCost : 지불 금액
          - remainCost : 잔액
          - couponWhether : 쿠폰 사용 여부
          - discountPrice : 할인된 금액

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
!! 어플리케이션을 정상적으로 실행시킨 상태에서 위의 주소로 접속하게 되면 Swagger를 통한 API 명세를 확인할 수 있습니다.

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
<img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white"/> <img src="https://img.shields.io/badge/JWT Token-333333?style=for-the-badge&logo=JWT Token&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>  <img src="https://img.shields.io/badge/jpa-6DB33F?style=for-the-badge&logoColor=green"/>  <img src="https://img.shields.io/badge/SWAGGER-85EA2D?style=for-the-badge&logo=Swagger&logoColor=black"/>

##

### 🏗️ 프로젝트 아키텍처
![서비스 아키텍쳐 #중간발표](https://user-images.githubusercontent.com/112993031/204065939-8d25f487-30cb-43d0-ab3a-1a663ccf8335.png)
