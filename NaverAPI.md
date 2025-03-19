# NAVER API 설정 가이드

## 준비완료
1. 네이버 클라우드 플랫폼 가입 ✅
    - 지도 API 이용가능
2. 네이버 Developers 계정 확인 ✅
    - 검색 API 이용가능

## 지역 검색 API
- 네이버 지역 서비스에 등록된 업체 및 기관 검색 기능
- RESTful API 방식
- 하루 호출 한도: 25,000회
- 비로그인 방식, 오픈 API
- 반환 형식: XML 또는 JSON
- 가이드문서 : https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD
- 예제(JAVA) -> example.ApiSearchBlogExample.java


## 지역 검색 API 다음 단계
- [ ] 애플리케이션 등록 ...
- [ ] API 키 발급
- [ ] API 사용 설정

## 네이버 지도 API
- Maps API 활용
    - 가이드문서 : https://guide.ncloud-docs.com/docs/ko/maps-overview

## 내 생각
- 지도 API는 뭔가 어플리케이션 구조를 잡고 만든 뒤에 붙이는 게 나을 것 같다는 생각...
- 다음에는 검색 API 예제 참고해서 간단한 구현 정도해보긔..
