# MSA project - 오픈마켓

# 사용기술
 - SpringBoot : Java11, Spring Boot2.7.13, Gradle, Spring Security
 - Spring Cloud : Eureka, Gateway, OpenFeign, Config
 - Autheticate : JWT, OAuth2.0
 - ORM : JPA, QueryDsl
 - Message Queue : Kafka
 - DataBase : MySQL 8.0.33
 - Test : Junit5

# 도메인 설계
<img width="762" alt="스크린샷 2023-12-04 오후 6 47 13" src="https://github.com/whwp4151/msa-OpenMarket/assets/91665074/87c89ee2-1736-491b-8ff5-610206e9d4cc">
<img width="763" alt="스크린샷 2023-12-04 오후 6 47 29" src="https://github.com/whwp4151/msa-OpenMarket/assets/91665074/db6c0370-968a-4a46-84d6-344a3b1a18db">

# ERD 설계
<img width="630" alt="스크린샷 2023-12-05 오후 7 22 43" src="https://github.com/whwp4151/msa-OpenMarket/assets/91665074/fb288706-9a51-4ebb-87ae-a7acba3967e5">

# 서비스 시나리오
1. 회원은 회원가입 및 로그인을 한다.
2. 회원은 상품을 선택 후 주문 및 결제를 한다.
2. 해당 상품의 재고를 확인 한 후 차감한다.
3. 판매자가 주문을 확인 후 주문 상태를 배송 준비중으로 변경한다.
4. 판매자가 상품을 발송한 후 주문 상태를 발송완료 상태로 변경한다.
5. 이후 배송이 완료되면 배치를 통해 주문 상태를 배송 완료 상태로 변경한다.
6. 판매자가 쇼핑몰에 입점 요청한다.
7. 쇼핑몰 관리자가 판매자에게 예치금 입금 요청 한다.
8. 판매자가 예치금을 입금한 후 확인 요청을 보낸다.
9. 쇼핑몰 관리자가 예치금 입금을 확인한 후 입점 승인한다.
10. 판매자가 상품을 등록한다.

# 개발 목표
> - Spring Cloud Eureka 사용하여 개발한 마이크로 서비스 등록 및 Service discovery 적용
> - Spring Cloud Gateway 사용하여 마이크로 서비스 API 라우팅 설정
> - Spring Cloud OpenFeign 사용하여 각 마이크로 서비스 HTTP  Client 개발
> - Kafka 사용하여 마이크로 서비스 이벤트 발행 및 구독 개발
> - Redisson 사용하여 중복 요청 방지

# 트랜잭션 처리
* 주문 시 재고 확인 : 주문 시 상품의 재고가 부족하면 보상 트랜잭션을 통한 rollback event 발행시켜 트랜잭션 관리

# 중복주문 방지
> - kafka 멱등성 프로듀서 적용
> - Redisson Lock 적용
