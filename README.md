```
routy-back/
├── src/
│ ├── main/java/com/routy/routyback/
│ │ ├── config/ # 설정 관련 (CORS, DB, 보안 등)
│ │ ├── controller/ # API 엔드포인트
│ │ │ ├── admin/ # 관리자 전용 API
│ │ │ └── user/ # 사용자 전용 API
│ │ ├── domain/ # 엔티티 클래스 (VO, Entity 등)
│ │ ├── dto/ # 요청·응답 DTO
│ │ ├── service/ # 비즈니스 로직
│ │ │ ├── admin/
│ │ │ └── user/
│ │ ├── mapper/ # MyBatis 매퍼 인터페이스
│ │ │ ├── admin/
│ │ │ └── user/
│ │ ├── exception/ # 예외 처리 클래스
│ │ ├── RoutyBackApplication.java # 메인 클래스
│ │ └── ServletInitializer.java # 서블릿 초기화 설정
│ └── resources/
│ ├── mapper/ # MyBatis 매퍼 XML 파일
│ │ ├── admin/
│ │ └── user/
│ ├── static.data/ # 정적 리소스 (데이터 등)
│ ├── application.properties # 기본 환경 설정
│ ├── application-admin.properties # 관리자용 설정
│ └── mybatis-config.xml # MyBatis 설정 파일
├── pom.xml # Maven 설정 파일
└── .env # 환경 변수 파일
```
