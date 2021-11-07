# Campers-Android

## 일과

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
- data : Model, 데이터 작업을 수행하는 곳.
- ui : 사용자에게 데이터를 표시하는데 도움이 되는 Activity, Fragment, ViewModel.
- util : 도우미 클래스 및 함수.