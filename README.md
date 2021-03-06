# WhatToEat
매일 무엇을 먹을지 고민하는 사람들은 위한 음식점 선택 시간 단축 앱

## Function
* 지도를 통해 사용자에게 주변 음식점 & 카페 목록 제공 ➜ <a href="https://developers.google.com/maps/documentation?hl=ko"> Google Map API 사용 </a>
* 방문했던 음식점 데이터 통계량 그래프
* 사용자 요청에 기반한 음식점 추천

### Database(Mysql)
사용자가 방문한 음식점 이름과 리뷰 저장

## Android Studio ↔ PHP ↔ Mysql ↔ Pycharm
* Android Studio는 보안상의 이유로 Mysql 직접 접근이 불가능 함 ➜ PHP 서버를 통해 접근

## 추천 시스템 (Recommendation System)
* TF-IDF 활용
* 기록된 리뷰를 활용히여 Cosine Similarity 비교 후 유사도가 가장 높은 식당 추천

## Cosine Similarity
![image](https://user-images.githubusercontent.com/61091307/118861024-025d7e00-b917-11eb-8a44-3e68b7f3ba79.png)

### 참고한 사이트
 - <a href="https://developer.android.com/guide?hl=ko"> 안드로이드 공식문서 </a>
 - <a href="https://developers.google.com/maps/documentation/android-sdk/overview?hl=ko"> 구글 클라우드 문서 </a>
 - <a href="https://webnautes.tistory.com/1315?category=618190"> 멈춤보단 천천히라도 </a>


