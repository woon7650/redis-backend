# redis-backend

- Spring Boot 3.3.2
- Java 17
- Spring Security 6.3.1
- Spring Data JPA 3.3.2
- Spring Redis 3.3.2
- Postgresql 12

Redis를 이용한 로그인 구현(인증) + 권한 추가(인가) + ElasticSearch를 통한 Full-Text-Search 구현


1. Redis를 이용한 로그인 구현(인증)

- Login API
    - request : userDto
    - accessToken : Vue store 변수로 저장
    - refreshToken : Http-Only Cookie로 저장

- Reissue API(Access Token 기간 만료 시)
    - request : header(AccessToken) + Cookie(RefreshToken)
    - AccessToken으로 찾은 Redis RefreshToken 과 Cookie에 존재하는 RefreshToken 비교
        1. 유효하지 않음 or 유효하고 일치하지 않음 : logout
        2. 유효하고 일치함 : accessToken, refreshToken 재발급

- Login과 Reissue를 제외한 모든 API 요청
    - request : header(AccessToken) + data
    - Filter 에서 Access Token 유효성 확인
        1. 유효 : 통과
        2. 유효하지 않음 : logout
        3. 기간 만료

- Logout API
    - accessToken : Vue store 변수에서 제거
    - refreshToken : Redis에서 해당 key-value 제거 + cookie에서 삭제
    

2. 권한 추가(인가) 진행 중
3. Full-Text-Search 진행 중
