# Campers-Android

## 일과

- 2021.11.11</br>
    Json 사용을 위해서 Gson Convertor, kotlinx serialization 공부 

- 2021.11.10</br>
    MVVM 공부(api, data, repository 패키지 생성) → 계층구조 업데이트
    로그인 api, data 작성 (@SerializedName의 반응 확인)

- 2021.11.08</br>
    SharedPreferences 클래스 생성 → SharedPreference를 사용하여 accessToken을 앱에 저장</br>
    View-Tool Windows-Device File Explorer에서 data/data/com.example.campers/shared_prefs에 accessToken 저장</br>
    마이페이지에 로그아웃 버튼 추가하여 accessToken 삭제 구현</br>
    스플래시에서 shared_prefs에 저장된 accessToken 유무에 따라 로그인화면/메인화면으로 이동 다르게 처리

- 2021.11.07</br>
    naver 로그인 테스트, 로그인 정보 불러오는 것까지(정보 불러올 때 스레드 사용 안하면 에러)
    
- 2021.11.05</br>
    campingzone 현재위치 이동 컨트롤러 추가</br>
    naver 로고 클릭 이벤트 비활성화</br>
    앱 다크모드 비활성화 설정

- 2021.11.04</br>
    campingzone naverMap 객체 등록 마커 생성까지 확인

- 2021.11.03</br>
    campingzone 프래그먼트에 네이버 지도 추가
    
- 2021.11.02</br>
     로그인 액티비티, xml 파일 추가</br>
     네이버 지도 api 조사
     
- 2021.11.01</br>
    BottomNavigation 구현, `app:labelVisibilityMode="labeled"`은 item의 라벨을 무조건 보이게한다.

- 2021.10.31</br>
    프로젝트 생성</br>
    홈, 캠핑존, 모닥불, 알람, 마이페이지 패키지 생성

## MVVM 클래스 계층 구조
- api : retrofit을 사용한 서버와의 통신
- data : data 클래스
- repository : data의 추상화
- ui : 사용자에게 데이터를 표시하는데 도움이 되는 Activity, Fragment, ViewModel.
- util : 도우미 클래스 및 함수.

<pre>
  .
  ├── api
  ├── data
  ├── repository
  ├── ui
  │   ├── campingzone
  │   ├── community
  │   ├── home
  │   ├── login
  │   ├── mypage
  │   ├── notification
  │   ├── splash
  ├── util
</pre>