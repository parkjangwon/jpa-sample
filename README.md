# JPA 학습 게시판 프로젝트

스프링 부트 + JPA + Vue.js 3로 구현한 게시판 애플리케이션입니다. JPA 학습을 목적으로 만들어졌으며, 게시물의 CRUD, 페이징, 검색 기능을 포함합니다.

## 🚀 주요 기능

### 백엔드 (Spring Boot + JPA)

- **게시물 CRUD**: 생성, 조회, 수정, 삭제
- **페이징**: 페이지 단위로 게시물 목록 조회
- **검색**: 제목, 내용, 작성자별 검색 및 통합 검색
- **정렬**: 최신순, 인기순(조회수) 정렬
- **조회수**: 게시물 조회 시 자동 증가
- **입력값 검증**: Bean Validation을 통한 데이터 검증
- **오류 처리**: 포괄적인 예외 처리 및 적절한 HTTP 상태 코드 반환
- **파라미터 검증**: 페이징, 검색 파라미터의 유효성 검사

### 프론트엔드 (Vue.js 3)

- **반응형 UI**: 모던하고 사용자 친화적인 인터페이스
- **모달 기반**: 게시물 작성/수정/상세보기를 모달로 구현
- **실시간 검색**: 다양한 조건으로 게시물 검색
- **페이지네이션**: 직관적인 페이지 이동
- **알림 시스템**: 작업 결과에 대한 사용자 피드백

## 🛠 기술 스택

### 백엔드

- **Spring Boot 3.2.0**: 웹 애플리케이션 프레임워크
- **Spring Data JPA**: 데이터 액세스 계층
- **H2 Database**: 인메모리 데이터베이스 (개발용)
- **Bean Validation**: 입력값 검증
- **Gradle**: 의존성 관리 및 빌드 도구

### 프론트엔드

- **Vue.js 3**: 프론트엔드 프레임워크
- **Axios**: HTTP 클라이언트
- **CSS3**: 반응형 디자인

## 📁 프로젝트 구조

```
jpa-sample/
├── src/
│   ├── main/
│   │   ├── java/com/example/jpaboard/
│   │   │   ├── controller/          # REST API 컨트롤러
│   │   │   ├── service/             # 비즈니스 로직
│   │   │   ├── repository/          # 데이터 액세스 계층
│   │   │   ├── entity/              # JPA 엔티티
│   │   │   ├── dto/                 # 데이터 전송 객체
│   │   │   └── JpaBoardApplication.java  # 메인 애플리케이션
│   │   └── resources/
│   │       ├── static/              # 정적 파일 (HTML, CSS, JS)
│   │       └── application.yml      # 애플리케이션 설정
├── build.gradle                     # Gradle 설정
└── README.md                        # 프로젝트 설명
```

## 🚀 실행 방법

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd jpa-sample
```

### 2. 애플리케이션 실행

```bash
# Gradle을 사용하여 실행
./gradlew bootRun

# 또는 JAR 파일로 빌드 후 실행
./gradlew build
java -jar build/libs/jpa-board-0.0.1-SNAPSHOT.jar
```

### 3. 웹 브라우저에서 접속

- **게시판**: http://localhost:8080
- **H2 콘솔**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (비어있음)

## 📚 API 문서

### 게시물 관리 API

#### 1. 게시물 생성

```http
POST /api/posts
Content-Type: application/json

{
  "title": "게시물 제목",
  "content": "게시물 내용",
  "author": "작성자"
}
```

#### 2. 게시물 목록 조회

```http
GET /api/posts?page=0&size=10
```

#### 3. 게시물 상세 조회

```http
GET /api/posts/{id}
```

#### 4. 게시물 수정

```http
PUT /api/posts/{id}
Content-Type: application/json

{
  "title": "수정된 제목",
  "content": "수정된 내용",
  "author": "작성자"
}
```

#### 5. 게시물 삭제

```http
DELETE /api/posts/{id}
```

### 검색 API

#### 1. 키워드 검색 (제목, 내용, 작성자)

```http
GET /api/posts/search/keyword?keyword=검색어&page=0&size=10
```

#### 2. 제목 검색

```http
GET /api/posts/search/title?title=제목&page=0&size=10
```

#### 3. 내용 검색

```http
GET /api/posts/search/content?content=내용&page=0&size=10
```

#### 4. 작성자 검색

```http
GET /api/posts/search/author?author=작성자&page=0&size=10
```

### 정렬 API

#### 1. 최신순 조회

```http
GET /api/posts/latest?page=0&size=10
```

#### 2. 인기순 조회 (조회수)

```http
GET /api/posts/popular?page=0&size=10
```

## 🎯 JPA 학습 포인트

### 1. 엔티티 설계

- `@Entity`, `@Table` 어노테이션 사용
- `@Id`, `@GeneratedValue`로 기본키 설정
- `@Column`으로 컬럼 속성 정의
- `@PrePersist`, `@PreUpdate`로 자동 시간 설정

### 2. Repository 패턴

- `JpaRepository` 상속으로 기본 CRUD 메서드 제공
- 메서드명 규칙을 통한 쿼리 자동 생성
- `@Query` 어노테이션으로 JPQL 직접 작성
- 페이징과 정렬 기능 활용

### 3. 서비스 계층

- `@Service` 어노테이션으로 서비스 컴포넌트 정의
- `@Transactional`로 트랜잭션 관리
- DTO를 통한 계층 간 데이터 전송
- 비즈니스 로직 구현

### 4. 컨트롤러 계층

- `@RestController`로 REST API 구현
- `@RequestMapping`으로 URL 매핑
- `@Valid`로 입력값 검증
- HTTP 상태 코드 적절히 반환

### 5. 페이징과 검색

- `Pageable` 인터페이스 활용
- `PageRequest`로 페이징 정보 생성
- 다양한 검색 조건 구현
- 정렬 기능 제공

## 🔧 설정 파일 설명

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb # H2 인메모리 데이터베이스
    driver-class-name: org.h2.Driver
    username: sa
    password:

  h2:
    console:
      enabled: true # H2 콘솔 활성화
      path: /h2-console

  jpa:
    hibernate:
      ddl-auto: create-drop # 애플리케이션 시작 시 테이블 생성, 종료 시 삭제
    show-sql: true # SQL 로그 출력
    properties:
      hibernate:
        format_sql: true # SQL 포맷팅
        use_sql_comments: true # SQL 파라미터 로그

server:
  port: 8080 # 서버 포트

logging:
  level:
    org.hibernate.SQL: DEBUG # Hibernate SQL 로그 레벨
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE # SQL 파라미터 로그
```

## 📝 학습 목표

이 프로젝트를 통해 다음을 학습할 수 있습니다:

1. **JPA 기본 개념**: 엔티티, 영속성 컨텍스트, 트랜잭션
2. **Spring Data JPA**: Repository 패턴, 쿼리 메서드
3. **페이징과 정렬**: Pageable, Sort 활용
4. **검색 기능**: 다양한 검색 조건 구현
5. **REST API 설계**: HTTP 메서드와 상태 코드 활용
6. **입력값 검증**: Bean Validation 사용
7. **프론트엔드 연동**: Vue.js와 Spring Boot 연동

## 🤝 기여하기

이 프로젝트는 JPA 학습을 목적으로 만들어졌습니다. 개선 사항이나 버그 리포트는 언제든 환영합니다!

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.
