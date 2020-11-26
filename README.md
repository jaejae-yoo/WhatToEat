# WHAT TO EAT
매일 무엇을 먹을지 고민하는 사람들은 위한 음식점 선택 시간 단축 어플리케이션  

## 기능
* 지도를 통해 사용자에게 주변 음식점&카페 목록 제공 
* 요일마다 방문했던 음식점 데이터 통계량 시각화
* 음식점 램덤 추천 

## 참고한 사이트
- 안드로이드 공식문서 (https://developer.android.com/guide?hl=ko)
- 구글 클라우드 (https://developers.google.com/maps/documentation/android-sdk/overview?hl=ko)
- 멈춤보단 천천히라도 (https://webnautes.tistory.com/1315?category=618190)

## MainActivity.java
    하단 액션바를 틀릭하면 Fragment 변경
    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch(menuItem.getItemId())
            {
                case R.id.iconMap:
                    transaction.replace(R.id.frameLayout, fragmentmap).commitAllowingStateLoss();
                    break;
                case R.id.iconFood:
                    transaction.replace(R.id.frameLayout, fragmentstore).commitAllowingStateLoss();
                    break;
                case R.id.iconStatic:
                    transaction.replace(R.id.frameLayout, fragmentstatic).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }



