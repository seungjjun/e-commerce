# E-commerce Server

TDD 기반 e-커머스 서버 구축 프로젝트 입니다.

# 목차

- [활용 기술](#활용-기술)
- [ERD](#erd)
- [작업 내용](#작업-내용)
- [아키텍처 설계](#architecture-설계)
- [테스트](#테스트)
- [프로젝트 회고](#프로젝트-회고)
- [Git branch 전략](https://github.com/seungjjun/e-commerce/wiki/Git-Branch-%EC%A0%84%EB%9E%B5)
- [API 명세](https://github.com/seungjjun/e-commerce/wiki/E%E2%80%90Commerce-API-Docs)
- [시퀀스 다이어그램](https://github.com/seungjjun/e-commerce/wiki/%EC%8B%9C%ED%80%80%EC%8A%A4-%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8)
- [트러블 슈팅]()
    - [동시성 문제 유즈케이스 및 해결](https://seungjjun.tistory.com/332)
    - [쿼리 분석 및 인덱스 설계](https://seungjjun.tistory.com/334)
    - [캐시 적용하여 성능 개선](https://seungjjun.tistory.com/335)
    - [주문 결제 시스템 이벤트 기반 아키텍처 전환 관련 트러블 슈팅](https://seungjjun.tistory.com/328)

## [마일스톤](https://github.com/seungjjun/e-commerce/milestones)

- [Roadmap](https://github.com/users/seungjjun/projects/4/views/4)

# 활용 기술

- Java 17
- Spring Boot 3.2.4
- JPA
- MySQL
- Redis
- H2

# ERD

<img width="820" alt="image" src="https://github.com/seungjjun/e-commerce/assets/104769120/6cce8492-c613-44ef-99a0-66f2c7dd5323">

---

# 작업 내용

- API 설계 (Github wiki 및 swagger를 통하여 API Docs 제공)
- 서비스 로직 개발 (주문/충전/메뉴 조회/인기메뉴 조회)
- 동시성 이슈 / 데이터 일관성 보장을 고려한 서비스 개발
- 동시에 같은 상품에 대한 주문 시, 상품 재고 및 포인트의 일관성이 깨지는 현상을 막기 위하여 Lock을 활용
- 인기메뉴를 조회하는 쿼리의 비용을 줄이기 위한 캐시 활용
- 트랜잭션 범위 최소화 하기 위해 ApplicationEvent 활용
- 트랜잭션이 커밋된 이후 이벤트 발행을 하기 위한 TransactionalEventListener 활용

--- 

# Architecture 설계

기존 자주 사용하던 Layered Architecture에서는 Service 계층이 Repository의 구현체에 직접 의존하고 있어, Repository의 변화가 Service 계층에도 수정을 필요로 하는 구조가
문제  
-> 이는 "하위 계층의 변경은 상위 계층으로 전파되지 않아야 한다"는 원칙에 위배

그래서 하위 계층인 Repository의 변경이 상위 계층으로 전파되지 않도록 추상화된 Repository 인터페이스를 도입하여 구현.  
Service 계층은 구체적인 Repository 구현체 대신 추상회 된 인터페이스에만 의존하므로, 하위 계층의 변경이 Service 계층에 영향 X

더하여 애플리케이션의 핵심인 비즈니스 로직의 안정성을 보장하기 위해, Presentation 계층은 도메인을 API로 서빙하는 역할을, DataSource 계층은 도메인이 필요로 하는 데이터 관련 기능을 제공하는
역할을 수행

도메인 로직을 안전하게 보호함과 각 계층을 독립적으로 분리함으로 자신의 역할에 집중하도록 설계함.  
이 구조는 **DIP와 OCP**을 준수하여 **확장에는 열려 있고 수정에는 닫혀 있는 아키텍처**를 설계.

---

# 테스트

- 주요 도메인 및 컨트롤러 단위 테스트
- 레포지토리 테스트
- 통합 테스트
    - 상품 주문 시 정상적으로 재고 차감 포인트 차감(결제)가 작동하는지 통합 테스트
    - 주문 동시성 테스트
    - 재고 부족 시 롤백 테스트
    - 잔액 부족 시 롤백 테스트

---

# 프로젝트 회고

- [프로젝트 회고 글](https://seungjjun.tistory.com/329)
