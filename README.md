# WHAT TO EAT
매일 무엇을 먹을지 고민하는 사람들은 위한 음식점 선택 시간 단축 어플리케이션  

## 기능
* 지도를 통해 사용자에게 주변 음식점&카페 목록 제공 
* 요일마다 방문했던 음식점 데이터 통계량 시각화
* 음식점 램덤 추천 


### FragmentMap.java
##### :heavy_check_mark: 구글 맵을 호출하고 현재 내 위치 마커로 표시, 내 위치를 기준으로 500미터 이내의 음식점 & 카페 위치 검색
    @Override
    public void onPlacesSuccess(final List<Place> places) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String mapstore = "";
                for (noman.googleplaces.Place place : places) {
                    LatLng latLng
                            = new LatLng(place.getLatitude()
                            , place.getLongitude());
                    String markerSnippet = getCurrentAddress(latLng);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    //Mapstore 변수에 검색된 카페(음식점)이름 저장
                    mapstore += place.getName() + "\n";
                    markerOptions.title(place.getName());
                    markerOptions.snippet(markerSnippet);
                    Marker item = map.addMarker(markerOptions);
                    previous_marker.add(item);
                }
                //Activity간에 데이터를 주고 받을 때 Bundle 클래스를 사용하여 데이터를 전송(Mapstore 변수 전송)
                Bundle bundle = new Bundle();
                bundle.putString("sendData", mapstore);
                activity.fragBtnClick(bundle);
                //중복 마커 제거
                HashSet<Marker> hashSet = new HashSet<Marker>();
                hashSet.addAll(previous_marker);
                previous_marker.clear();
                previous_marker.addAll(hashSet);
            }
        });
    }
   
### FragmentStore.java
##### :heavy_check_mark: 주변 음식적 화면에 출력하고 사용자가 방문한 음식점 db에 저장
    //방문했던 음식점을 기록하기 위해 db생성
    private void createDatabase() {
        database = getActivity().openOrCreateDatabase("restaurant_store.db",android.content.Context.MODE_PRIVATE ,null);
        createTable("storeList");
    }
    // 매개변수로 받는 name(storeList) 이라는 테이블이 존재하지 않으면 테이블을 생성하고, store_name 이라는 컬럼 생성
    private void createTable(String name) {
        if (database == null) {
            return;
        }
        database.execSQL("create table if not exists " + name + "("
                + " store_name text)");
    }
    //storeList 테이블에 매개변수로 받은 음식점 이름 insert
    private void insertRecord(String _name) {
        if (database == null) {
            return;
        }
        if (tableName == null) {
            return;
        }
        database.execSQL("INSERT INTO storeList VALUES ('" + _name + "');");
        System.out.println("insert");
    }
    
### FragmentStatic.java
##### :heavy_check_mark: db에 저장되어 있는 방문한 음식점들의 방문 횟수를 Bar Chart로 화면에 출력, 사용자에게 음식점 램덤 추천  
    //db에 저장되어 있는 방문했던 가게들의 이름에 ","를 더하여 store 변수에 저장 후 return
    public String executeQuery() {
        String sql = "select * from " + tableName;
        Cursor cursor = database.rawQuery(sql, null);
        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String store_name = cursor.getString(0);
            System.out.println("레코드" + i + " : " + store_name);
            if (i < recordCount - 1) {
                store += store_name + ",";
            } else if (i == recordCount - 1) {
                store += store_name;
            }
        }
        return store;
    }
    
    //음식점 램덤 추천
    ArrayList storename = new ArrayList();
    for ( String key : Hmap.keySet() ) {
        System.out.println(key+ "key");
        storename.add(key);
     }
    System.out.println(storename+ "storename");
    int n = Hmap.keySet().size();
    double _num = Math.random()*n;
    int num = (int) _num;
    randomView.append("오늘의 추천 음식점: " + storename.get(num).toString());


### 참고한 사이트
- 안드로이드 공식문서 (https://developer.android.com/guide?hl=ko)
- 구글 클라우드 (https://developers.google.com/maps/documentation/android-sdk/overview?hl=ko)
- 멈춤보단 천천히라도 (https://webnautes.tistory.com/1315?category=618190)

