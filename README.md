# Campers-Android

## MVVM 클래스 계층 구조
- api : retrofit을 사용한 서버와의 통신
- data : data 클래스
- repository : data의 추상화
- ui : 사용자에게 데이터를 표시하는데 도움이 되는 Activity, Fragment, ViewModel.
- util : 도우미 클래스 및 함수.

## 데이터 클래스 명 정의
Response : 서버 응답 데이터
Request : 서버 요청 데이터
Result : 서버 응답 데이터를 success와 failure로 정리한 데이터 / 서버 응답 데이터에서 사용할 데이터

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

## 일과
-2022.09.12~09.15<br/>
    모닥불 리스트 UI 작업 및 API 연동<br/>
    Mysql 서버 Connection 끊김 이슈 발생

-2022.09.02~09.08<br/>
    홈 화면, 캠피장 검색 UI 추가<br/>
    모닥불 데이터 바인딩 적용<br/>
    모닥불 화면, 사용자 게시판, 기본 게시판 15개 리스트 API 연동 및 UI 작업<br/>
    RecyclerView, No adapter attached; skipping layout 에러 해결 
    
    

- 2022.08.25~09.01<br/>
    HomeFragment, DataBinding 적용<br/>
    홈 화면 로직 전체적으로 수정(코루틴 적용, 상세화면 이동할 때 Fragment에서 Activity 변경)
    전역적으로 사용하기 위한 화면 xml 생성(뒤로가기 버튼 )


- 2022.08.22~08.24<br/>
    SDK, API 버전 업데이트<br/>
    SharedPreferences 수정

- 2022.07.28~08.21<br/>
    로그인 데이터 바인딩 적용<br/>
    네이버 로그인, 구글 로그인 로직 분리<br/>
    Common 하게 사용할 다이얼로그 생성

- 2022.07.24~07.27<br/>
    인트로 수정

- 2022.03.28<br/>
    ViewPager2 추가하고 인트로 화면 추가

- 2022.03.27<br/>
    스플래시 화면 gif로 변경(glide 추가)</br>

- 2022.03.02<br/>
    커뮤니티 상세 화면 작업 마무리

- 2022.02.27<br/>
    커뮤니티 상세 화면 작업

- 2022.02.20~21<br/>
    커뮤니티 모닥불의 댓글 이미지와 텍스크 크기 dimen 지정<br/>
    커뮤니티 상세 화면 작업

- 2022.02.07</br>
    홈 화면 핫 커뮤니티 리스트 적용

- 2022.01.31</br>
    MVVM 공부했던 내용을 적용 → 랭킹 리스트를 홈에 적용

- 2022.01.07~-01.28</br>
    MVVM(DataBinding, LiveData, ViewModel)의 사용법을 Android_Study 리포지토리에 정리하면서 공부.

- 2022.01.06</br>
    뒤로가기 버튼 이벤트 처리, onBackPressed()를 사용(navController.currentDestination를 통해 홈 화면을 구분)
    
- 2021.12.31~2022.01.05</br>
    안드로이드 버전 업데이트 해결 실패.</br>
        - gradle(Project)에서 jcenter() 삭제하면 Naver SDK 인식 불가 오류</br>
        - classpath "com.android.tools.build:gradle:4.1.3" 을 7.~로 업데이트하면 manifests에서 exported 에러 발생(명시해도 에러남) 

- 2021.12.01</br>
    키보드 올라올 때 EditText 가리는 거 해결

- 2021.11.30</br>
    community 리사이클러 뷰 추가

- 2021.11.29</br>
    drawable에 home_round.xml을 추가하여 위젯의 테두리를 둥글게 만드는 작업함 → 홈의 ConstraintLayout에 적용</br>
    캠핑존 검색 시 정보 창 바로 띄어지도록 처리</br>
    themes.xml에 액셕바 삭제 추가</br>
    홈에 베스트 Camper 임시로 추가

- 2021.11.28</br>
    campingzone 검색 searchView를 EditText와 ImageView로 변경</br>
    고캠핑 API로 네이버 지도에 marker 생성</br>
    campingzone 검색 시 카메라 이동 및 키보드 내리기</br>
    현재 마커가 열려있으면 닫고, 닫혀있으면 열어주는 처리</br>
    검색으로 마커를 띄우고 다른 검색어로 입력하면 기존에 있던 마커 삭제

- 2021.11.27</br>
    네이버 검색 API 연습</br>
    고캠핑 API 연습

- 2021.11.24</br>
    캡스톤 결과물 보여주기 위한 작업</br>
    fragment to fragment를 위해서 MainActivity에서 changeFragment 대신 navController를 등록해서 사용.
    
- 2021.11.23</br>
    AlertDialog 클래스 생성</br>
    sign-up api 생성하여 다이얼로그로 닉네임 입력후 넘어가게 처리 → 문제 발생: 로그인일 때는 payload가 JsonPrimitive, 회원가입일 때는 JsonObject → 해결: 서버에서 payload 부분을 다 JsonPrimitive로 변경</br>
    다이얼로그 끝나고 메인화면으로 넘어가는 부분에서 Activity has leaked window 에러 발생</br>
    wysiwyg 갤러리 사진 삽입 테스트

- 2021.11.22</br>
    다이얼로그 공부

- 2021.11.21</br>
    LoginRepository suspend 정지함수 처리.</br>
    LoginActivity에서 Thread 대신 GlobalScope 사용.

- 2021.11.20</br>
    'startActivityForResult(Intent!, Int): Unit' is deprecated. Deprecated in Java 해결 → registerForActivityResult 사용

- 2021.11.19</br>
    ViewModel, LiveData 의존성 추가    

- 2021.11.17~18</br>
    구글 로그인 해결, LoginResponse에서 응답이 안넘어와서 JsonPrimitive로 바꾸고 해결.</br>
    
- 2021.11.15~16</br>
    구글 로그인 오류 해결중

- 2021.11.14</br>
    구글 로그인 버튼 추가 및 Text 변경</br>
    multidex 의존성 추가</br>
    Firebase, 구글 로그인 의존성 추가
    
- 2021.11.13</br>
    MVVM 구조에 맞춰서 Login 구현(서버와 통신해서 발급받은 accessToken을 shared_prefs에 저장)</br>

- 2021.11.12</br>
    네이버 로그인 api 권한에서 name 속성 추가로 읽도록 수정</br>
    retrofit으로 데이터 넘어오는 것 확인(문제는 데이터 형식이였음 Gson의 JsonObject으로 해결)

- 2021.11.11</br>
    Json 사용을 위해서 Gson Convertor, kotlinx serialization 공부 

- 2021.11.10</br>
    MVVM 공부(api, data, repository 패키지 생성) → 계층구조 업데이트</br>
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