# E-commerce Server

TDD 기반 e-커머스 서버 구축 프로젝트 입니다.

# 목차
- [마일스톤](#마일스톤)
- [ERD](#erd)
- [API 명세](#api-명세)
    - [상품 리스트 조회 API](#상품-리스트-조회-api)
    - [상품 상세 조회 API](#상품-상세-조회-api)
    - [잔액 충전 API](#잔액-충전-api)
    - [잔액 조회 API](#잔액-조회-api)
    - [주문 및 결제 API](#주문-및-결제-api)
- 시퀀스 다이어그램
    - [상품 조회](#시퀀스-다이어그램-상품-조회)
    - [잔액 충전 및 조회](#시퀀스-다이어그램-잔액-충전-및-조회)
    - [주문 및 결제](#시퀀스-다이어그램-주문-및-결제)
- [Swagger API Docs](#swagger-api-docs)
- [프로젝트 회고](#프로젝트-회고)


## [마일스톤](https://github.com/seungjjun/e-commerce/milestones)
- [Roadmap](https://github.com/users/seungjjun/projects/4/views/4)

# ERD

<img width="820" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/6cce8492-c613-44ef-99a0-66f2c7dd5323">

---

# API 명세

## 상품 리스트 조회 API

### 시퀀스 다이어그램 (상품 조회)
<img width="660" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/b4b4a1f1-44a7-4592-a6b0-4b4ff5f6dc44">

`Endpoint`

```
GET /products
```

<br/>

`Response`

```json
{
  "products": [
    {
      "id": 1,
      "name" : "후드티",
      "price": 52000
    },
    ...
  ]
}
```

`HTTP Status Code`

HTTP Status Code | Description
---------------- | -----------
200 OK           | 상품 리스트를 정상적으로 불러옴

## 상품 상세 조회 API

`Endpoint`

```
GET /products/{productId}
```

<br/>

`Response`
```json
{
  "id": 1,
  "name": "후드티",
  "price": 52000,
  "stockQuantity": 7,
  "description": "편한 후드티"
}
```

`HTTP Status Code`

HTTP Status Code | Description
-- | --
200 OK | 상품 정보를 정상적으로 불러옴
404 Not Found | 상품 정보를 찾지 못함


## 잔액 충전 API

### 시퀀스 다이어그램 (잔액 충전 및 조회)

<img width="660" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/e856e939-a34d-43ca-87b5-dd0205eae5de">


`Endpoint`

```
PATCH /accounts/{userId}/charge
```

<br/>

`Request`

```json
{
  "amount": 5000
}
```

**patch variable**

항목 | 필수 여부 | 설명
--  |-------| --
userId | Y  | 잔액을 충전할 사용자의 id 

<br/>

`Response`

```json
{
  "balance": 5000
}
```

<br/>

`HTTP Status Code`

HTTP Status Code | Description
-- | --
200 OK | 정상적으로 충전 성공
400 Bad Request | 충전 금액이 잘못됨 (e.g 음수 or 0)
400 Bad Request | 사용자를 id 값이 올바르지 않음

<br/>

`Error`
- 잘못된 userId값을 요청받았은 경우
- 충전 요청 금액이 음수 or 0일 경우

## 잔액 조회 API

`Endpoint`

```
GET /accounts/{userId}/balance
```

<br/>

`Request`

**patch variable**

항목 | 필수 여부 | 설명
--  |-------| --
userId | Y  | 잔액을 조회할 사용자의 id 

<br/>

`Response`

```json
{
  "balance": 5000
}
```

<br/>

`HTTP Status Code`

HTTP Status Code | Description
-- | --
200 OK | 잔액을 정상적으로 반환
400 Bad Request | 충전 금액이 잘못됨 (e.g 음수 or 0)
400 Bad Request | 사용자를 id 값이 올바르지 않음

## 주문 및 결제 API

### 시퀀스 다이어그램 (주문 및 결제)
<img width="660" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/006ad1b1-1e6f-42a6-a93a-2cb7cf36088c">

`Endpoint`

```
POST /orders/{userId}
```

<br/>

`Request`

```json
{
  "receiver" : {
    "name" : "홍길동",
    "address" : "서울시 송파구",
    "phoneNumber" : "01012345678"
  },
  "products" : [
    {
      "id" : 1,
      "quantity" : 1
    },
    ...
  ],
  "paymentAmount" : 10000,
  "paymentMethod" : "CARD"
}
```

**patch variable**

항목 | 필수 여부 | 설명
--  |-------| --
userId | Y  | 주문 요청하는 사용자의 id

<br/>

`Response`

```json
{
  "orderId": 1,
  "paymentId": 1,
  "payAmount": 10000,
  "receiver": {
    "name": "홍길동",
    "address": "서울시 송파구",
    "phoneNumber": "01012345678"
  },
  "paymentMethod": "CARD",
  "orderedAt": "2024-04-11 20:57:05",
  "paidAt": "2024-04-11 20:57:05"
}
```

<br/>

`HTTP Status Code`

HTTP Status Code | Description
-- | --
201 Created | 주문 결제 성공
400 Bad Request | 재고 부족으로 인한 주문 실패
400 Bad Request | 잔액 부족으로 인한 주문 실패
404 Not Found | 상품 정보를 찾지 못함

<br/>

`Error`
- 잘못된 userId값을 요청받았은 경우
- 주문하려는 상품의 재고가 부족한 경우
- 잔액이 부족한 경우

### Swagger Api Docs

<img width="800" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/dbec503f-ccf0-4096-a43d-49b87b08f434">

# 프로젝트 회고
- [프로젝트 회고 글](https://seungjjun.tistory.com/329)
- [주문 결제 시스템 이벤트 기반 아키텍처 전환 관련 트러블 슈팅](https://seungjjun.tistory.com/328)