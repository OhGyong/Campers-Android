package com.example.campers.ui.campingzone

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.campers.R
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class CampingzoneFragment : Fragment(), OnMapReadyCallback {

    private lateinit var naverMap: NaverMap // naverMap API를 호출하기 위한 인터페이스 역할의 NaverMap 객체 선언
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource // 위치 권한 변수

    private lateinit var response: JSONObject

    private lateinit var searchText: String

    private val markerList = mutableListOf<Marker>()


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

        val mapOptionBtn = view.findViewById<ImageView>(R.id.map_option)
        mapOptionBtn.setOnClickListener {
            findNavController().navigate(R.id.campingzoneOptionFragment)
        }

        val searchBtn = view.findViewById<EditText>(R.id.searchText)
        val searchView = view.findViewById<ImageView>(R.id.searchView)

        /**
         * 검색 이미지 클릭 했을 때
         * 마커리스트에 값이 있으면 for문으로 돌면서 마커를 삭제(null)하고
         * for문을 나와서 마커리스트를 초기화시켜준다.
         */
        searchView.setOnClickListener {
            if (markerList.isNotEmpty()) {
                for (i in markerList){
                    i.map = null
                }
                markerList.clear()
            }
            searchText = searchBtn.text.toString()
            goCampingMark()

            // 키보드 내리기
            var imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        // MapView의 getMapAsync() 메서드로 OnMapReadyCallboack을 등록하여 비동기로 NaverMap 객체를 얻게한다.
        mapView = view.findViewById(R.id.naver_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // UI 관련 설정
        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true // 현재위치로 이동하는 컨트롤러 설정
        uiSettings.isLogoClickEnabled = false // naver 로고 버튼 이벤트 제거 설정

        naverMap.locationTrackingMode =
            LocationTrackingMode.Follow // 위치 추적 모드를 Follow로 설정(캠핑존 진입시 현재 위치로 이동)
    }


    /**
     * 마커 설정
     */
    private fun markerOption(i: Int?, responseItemArray: JSONArray?, responseItemObject: JSONObject?) {
        val marker = Marker()
        val infoWindow = InfoWindow()
        markerList.add(marker)

        if (responseItemObject == null) {
            marker.position = LatLng(
                responseItemArray!!.getJSONObject(i!!).getDouble("mapY"),
                responseItemArray.getJSONObject(i).getDouble("mapX")
            )
            marker.captionText = responseItemArray.getJSONObject(i).getString("facltNm")
            marker.tag = responseItemArray.getJSONObject(i).getString("induty")

        } else {
            val infoWindow = InfoWindow()
            marker.position =
                LatLng(responseItemObject.getDouble("mapY"), responseItemObject.getDouble("mapX"))
            marker.captionText = responseItemObject.getString("facltNm")
            marker.tag = responseItemObject.getString("induty")
        }
        marker.setOnClickListener {
            if (marker.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close()
            }
            true
        }
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?: ""
            }
        }
        marker.icon = OverlayImage.fromResource(R.drawable.campingzone_location)
        marker.width = 70
        marker.height = 110
        marker.map = naverMap
        infoWindow.open(marker)
    }

    /**
     * 위치 권한 설정
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
    }


    /**
     * 고캠핑 api 받아오면서 마커에 마킹하기
     */
    private fun goCampingMark() {

        val urlBuilder =
            StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/GoCamping/searchList")
        urlBuilder.append("?serviceKey=" + "zeE3dESt31%2FMFmTX6I4OcbrZz2ChbnBsiYt332Dh%2B7hYTDnljPKRh01AHJlrf3RFPG2vQwGPkAYi1rEsfBjU6Q%3D%3D")
        urlBuilder.append("&pageNo=" + "1")
        urlBuilder.append("&numOfRows=" + "10")
        urlBuilder.append("&MobileOS=" + "AND")
        urlBuilder.append("&MobileApp=" + "AppTest")
        urlBuilder.append("&keyword=$searchText")
        urlBuilder.append("&_type=" + "json")


        val url = urlBuilder.toString()
        val requestHeaders = HashMap<String, String>()

        runBlocking {
            val k = GlobalScope.launch {
                response = get(url, requestHeaders).getJSONObject("response")
            }
            k.join()

            val responseBody = response.getJSONObject("body")
            val responseItems = responseBody.getJSONObject("items")
            println(responseItems)

            // 고캠핑 api에서 받아온 데이터가 단일항목(JSONObject)일 경우와 JSONArray일 경우 나눠서 작업
            if (responseItems.get("item") is JSONArray) {
                val responseItem = responseItems.getJSONArray("item")
                for (i in 0 until responseItem.length()) {
                    println("고캠핑 api 데이터 확인 " + responseItem.getJSONObject(i).getString("mapY") + "  " + responseItem.getJSONObject(i).getString("mapX"))
                    markerOption(i, responseItem, null)
                }

                // 카메라 이동
                val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(responseItem.getJSONObject(0).getDouble("mapY"), responseItem.getJSONObject(0).getDouble("mapX")), 10.0)
                naverMap.moveCamera(cameraUpdate)
            } else {
                val responseItem = responseItems.getJSONObject("item")
                println("고캠핑 api 데이터 확인 ${responseItem.get("mapY")}  ${responseItem.get("mapX")}")
                println(responseItem.get("lineIntro"))
                markerOption(null, null, responseItem)

                // 카메라 이동
                val cameraUpdate = CameraUpdate.scrollAndZoomTo(LatLng(responseItem.getDouble("mapY"), responseItem.getDouble("mapX")), 10.0)
                naverMap.moveCamera(cameraUpdate)
            }
        }

    }

    private fun get(apiUrl: String, requestHeaders: Map<String, String>): JSONObject {
        val con = connect(apiUrl)

        try {
            con.requestMethod = "GET"

            for ((key, value) in requestHeaders) {
                con.setRequestProperty(key, value)
            }

            val responseCode = con.responseCode
            return if (responseCode == HttpURLConnection.HTTP_OK) {
                readBody(con.inputStream)
            } else {
                readBody(con.errorStream)
            }
        } catch (error: IOException) {
            throw RuntimeException("API 요청과 응답 실패 $error")
        } finally {
            con.disconnect()
        }
    }

    private fun connect(apiUrl: String): HttpURLConnection {
        try {
            val url = URL(apiUrl)
            return url.openConnection() as HttpURLConnection
        } catch (error: MalformedURLException) {
            throw RuntimeException("API URL이 잘못되었습니다. : $apiUrl, $error")
        } catch (error: IOException) {
            throw RuntimeException("연결이 실패했습니다. : $apiUrl, $error")
        }
    }

    private fun readBody(body: InputStream): JSONObject {
        val streamReader = InputStreamReader(body)

        try {
            BufferedReader(streamReader).use { lineReader ->
                val responseBody = StringBuilder()
                var line: String?
                while (lineReader.readLine().also { line = it } != null) {
                    responseBody.append(line)
                }
                println(responseBody.toString())
                return JSONObject(responseBody.toString())
            }
        } catch (e: IOException) {
            throw java.lang.RuntimeException("API 응답을 읽는데 실패했습니다.", e)
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}