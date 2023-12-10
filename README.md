# 실전 배당금 프로젝트
![main](https://github.com/JGoo99/dividend-info-project/assets/126454114/10079618-17c1-4124-a27d-049267a0156f)


## 목차

- [개요](#개요)
- [활용기술](#활용-기술)
- [DB 구조](#DB-구조)
- [API 구조](#API-구조)
- [정보](#정보)

<br/>

## 개요

위 프로젝트는 2023년 11월 30일 부터 2023년 12월 10일까지 진행했으며, 제로베이스 수료 중 개인과제로 제출했습니다.  
따라서 모든 저작권은 제로베이스에 있습니다.

<br/>

## 활용 기술

> Spring Boot 3.1.5
>
> Gradle - Groovy
>
> Java 1.8
>
> H2
>
> JPA
> 
> JWT
>
> Redis
> 
> Lombok

<br/>

## DB 구조

![erd](https://github.com/JGoo99/dividend-info-project/assets/126454114/1fc2542d-ad30-4d6c-9314-1f27c25e5233)

회사 및 배당금 데이터는 [Yahoo Finance](https://finance.yahoo.com/) 에서 스크랩하여 사용했습니다.


<br/>

## API

![api-doc1](https://github.com/JGoo99/dividend-info-project/assets/126454114/eaa60d22-a20a-47eb-bdbc-632e6b8b762c)

### Member

- POST - auth/signup
    - 회원가입 API (중복 ID 허용x, 패스워드는 암호화)


- POST - auth/signin
    - 로그인 API (회원가입이 되어있고, 아이디/패스워드 정보가 옳은 경우 JWT 발급)


### Company

- GET - company/autocomplete
    - 회사명의 prefix 를 자동완성한 회사명 리스트 중 10개를 반환한다.


- GET - company
    - 서비스에서 관리하고 있는 모든 회사 목록을 반환한다.


- POST - company
    - 회사의 ticker 를 입력으로 받아 새로운 회사와 배당금 정보를 스크랩핑하여 DB에 추가한다.


- DELETE - company/{ticker}
    - ticker 에 해당하는 회사 정보와 캐시를 모두 삭제한다.

### Finance

- GET - finance/dividend/{companyName}
  - 회사 이름을 받아서 해당 회사의 메타 정보와 배당금 정보를 반환한다.
