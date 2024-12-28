# Todo App 백엔드

Spring Boot 기반의 Todo 애플리케이션 백엔드 서버입니다.

## 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT 인증
- AWS S3
- MikroORM

## 주요 기능

### 1. 인증 시스템 (Authentication)

#### JWT 기반 인증
- Access Token과 Refresh Token을 사용한 이중 토큰 시스템
- Access Token: 15분 유효
- Refresh Token: 7일 유효
- HttpOnly 쿠키를 통한 Refresh Token 관리

#### 보안 기능
- 비밀번호 암호화 (BCrypt)
- CORS 설정
- Spring Security 기반 엔드포인트 보호

### 2. 사용자 관리

#### 계정 기능
- 회원가입
- 로그인/로그아웃
- 비밀번호 변경
- 프로필 관리

#### 프로필 이미지 관리
- 프로필 이미지 업로드/수정
- 배경 이미지 업로드/수정
- AWS S3 스토리지 연동

## API 엔드포인트

### 인증 관련 (/api/auth)
- POST /api/auth/login - 로그인
- POST /api/auth/register - 회원가입
- POST /api/auth/refresh - 토큰 갱신
- GET /api/auth/logout - 로그아웃

### 회원 관리 (/api/member)
- GET /api/member - 프로필 조회
- PUT /api/member/password - 비밀번호 변경
- POST /api/member/profileImg - 프로필 이미지 업로드
- POST /api/member/bgImg - 배경 이미지 업로드

## 프로젝트 구조
src/main/java/com/sunniesfish/todo_app/
```
├── auth/
│ ├── controller/ # API 엔드포인트 컨트롤러
│ ├── dto/ # 데이터 전송 객체
│ ├── entity/ # 데이터베이스 엔티티
│ ├── repository/ # 데이터 접근 계층
│ ├── service/ # 비즈니스 로직
│ └── util/ # JWT 관련 유틸리티
├── config/ # 설정 클래스
└── global/ # 전역 컴포넌트
```

## 보안 특징

1. **토큰 관리**
   - Access Token: API 요청 인증
   - Refresh Token: 데이터베이스 저장 및 쿠키 관리
   - 토큰 만료 시 자동 갱신 메커니즘

2. **비밀번호 정책**
   - 7-13자 길이 제한
   - 문자, 숫자, 특수문자 포함 필수
   - BCrypt 암호화 저장

3. **CORS 설정**
   - 허용된 오리진만 접근 가능
   - 안전한 HTTP 메소드만 허용
   - 인증 헤더 처리

## 이미지 업로드 제한

- 프로필 이미지: 최대 15MB
- 배경 이미지: 최대 30MB
- 지원 형식: PNG, JPEG, JPG

## 개발 가이드

1. **환경 설정**
   - AWS 자격 증명 설정
   - JWT 시크릿 키 설정
   - 데이터베이스 연결 설정

2. **빌드 및 실행**
   ```bash
   ./gradlew build
   java -jar build/libs/todo_app-0.0.1-SNAPSHOT.jar
   ```

---
