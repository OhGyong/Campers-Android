package com.example.campers.ui.campingzone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.campers.R
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class CampingzoneFragment: Fragment(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap // naverMap API를 호출하기 위한 인터페이스 역할의 NaverMap 객체 선언
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationSource =
                FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_campingzone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // MapView의 getMapAsync() 메서드로 OnMapReadyCallboack을 등록하여 비동기로 NaverMap 객체를 얻게한다.
        mapView = view.findViewById(R.id.naver_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isLogoClickEnabled = false

    }

    /**
     * 권한 설정
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                        grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}