# FoodYo
# ⚾ KBO GO - 야구장 러쉬 예측 토이 프로젝트

## 🎯 프로젝트 소개

야구팬을 위한 실시간 교통 예측 서비스!  
경기장까지 가는 길의 **교통 혼잡도**를 예측하고,  
**현재 위치 기준 최적 출발 시간**과 **경로**를 추천해주는 서비스입니다.

---

## 🛠️ 핵심 기능

| 기능 | 설명 |
|------|------|
| ✅ 경기장 선택 | 고척, 잠실, 문학 등 선택 가능 |
| ✅ 경기 시간 입력 | 예: 18:30 경기 시작 |
| ✅ 현재 위치 기반 경로 탐색 | 내 위치에서 야구장까지 예상 소요 시간 계산 |
| ✅ 지하철 혼잡도 예측 | 실시간/과거 데이터를 활용한 시간대별 예측 |
| ✅ 출발 시간 추천 | 도착 목표 시간 기준 최적 출발 시점 제안 |
| ✅ 자차 vs 대중교통 비교 | 주차 가능성, 소요 시간, 혼잡도 비교 안내 |

---

## 🧩 사용 API

| 카테고리 | API | 설명 | 비용 |
|----------|-----|------|------|
| 교통정보 | [서울시 TOPIS](https://topis.seoul.go.kr/refRoom/openRefRoom_4.do) | 실시간 교통/버스/지하철 혼잡도 | ✅ 무료 |
| 지하철 | [서울교통공사 소요시간 API](https://www.data.go.kr/data/15057802/openapi.do) | 역간 거리/시간 | ✅ 무료 |
| 위치 기반 | `navigator.geolocation` (JS 내장) | 유저 위치 받아오기 | ✅ 무료 |
| 장소 검색 | Tmap POI / Kakao Local | 야구장 및 주변 시설 검색 | 일부 유료 (Tmap), 무료 (Kakao 범위 내) |

---

## 📍 내 위치 받기 (JS 예시 코드)

```javascript
navigator.geolocation.getCurrentPosition(
  (pos) => {
    const lat = pos.coords.latitude;
    const lon = pos.coords.longitude;
    console.log("위도:", lat, "경도:", lon);
  },
  (err) => console.error("위치 가져오기 실패", err)
);
