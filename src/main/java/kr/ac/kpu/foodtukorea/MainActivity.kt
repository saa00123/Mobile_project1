package kr.ac.kpu.foodtukorea

import android.graphics.PointF
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.InfoWindow.DefaultTextAdapter
import com.naver.maps.map.overlay.InfoWindow.DefaultViewAdapter
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import kr.ac.kpu.foodtukorea.domain.FoodStoreApiResult
import kr.ac.kpu.foodtukorea.domain.Row
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private var mapView: MapView? = null
    private var naverMap: NaverMap? = null
    private val markerList: MutableList<Marker>? = ArrayList()
    private var locationSource: FusedLocationSource? = null
    private var infoWindow: InfoWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 스플래시
        supportActionBar?.setIcon(R.drawable.restaurant2)
        supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.show()

        //네이버 지도
        mapView = findViewById<View>(R.id.map) as MapView
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu!!.add(0,1,0,"위생등급")
        menu!!.add(0,2,0,"위생법위반 및 행정처분")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> {

                return true
            }
            2 -> {

                return true
            }
        }
        return false
    }

    //최초 실행 시 설정값
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        locationSource = FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        val mapCenter = naverMap.cameraPosition.target
        fetchFoodStore("e9d760bbea604ab0900bcd74d4f95be6", 250, "json", 41390)
        infoWindow = InfoWindow()
        //마커 클릭 시 표시되는 내용
        //마커 클릭 시 표시되는 내용
        infoWindow!!.adapter = object : DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                val marker = infoWindow.marker
                val row = marker!!.tag as Row?
                // 정보창에 음식점이름, 주소(도로명x)표시
                return row!!.entrpsNm.toString() + "\n" + row!!.refineLotnoAddr.toString()
            }
        }

        val cameraPosition = CameraPosition(
            LatLng(37.377, 126.805),  // 위치 지정
            11.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition
    }

    private fun fetchFoodStore(key: String, pSize: Int, type: String, sigun: Int) {
        val retrofit = Retrofit.Builder().baseUrl("https://openapi.gg.go.kr/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val foodStoreApi = retrofit.create(FoodStoreApi::class.java)
        foodStoreApi.storeByGeo.enqueue(object : Callback<FoodStoreApiResult?> {
            override fun onResponse(
                call: Call<FoodStoreApiResult?>,
                response: Response<FoodStoreApiResult?>
            ) {
                if (response.code() == 200) {
                    val result = response.body()
                    updateMarkers(result)
                }
            }

            override fun onFailure(call: Call<FoodStoreApiResult?>, t: Throwable) {
                println("실패")
            }
        })
    }

    private fun updateMarkers(result: FoodStoreApiResult?) {
        val stus = result?.restrtSanittnGradStus
        val rows: MutableList<Row?> = ArrayList()
        for (restrtSanittnGradStu in stus!!) {
            if (restrtSanittnGradStu.row == null) {
                continue
            }
            val row = restrtSanittnGradStu.row
            for (temp in row!!) {
                rows.add(temp)
            }
        }
        if (result.restrtSanittnGradStus != null && rows.size > 0) {
            for (rowStore in rows) {
                val marker = Marker()
                marker.tag = rowStore
                if (rowStore != null) {
                    marker.position = rowStore.refineWgs84Lat?.let {
                        rowStore.refineWgs84Logt?.let { it1 ->
                            LatLng(
                                it.toDouble(),
                                it1.toDouble()
                            )
                        }
                    }!!
                }
                marker.icon = OverlayImage.fromResource(R.drawable.marker_green)
                if (rowStore != null) {
                    if ("매우우수" == rowStore.appontGrad) {
                        marker.icon = OverlayImage.fromResource(R.drawable.marker_green)//초록색
                    } else if ("우수" == rowStore.appontGrad) {
                        marker.icon = OverlayImage.fromResource(R.drawable.marker_yellow) //노란색
                    } else if ("좋음" == rowStore.appontGrad) {
                        marker.icon = OverlayImage.fromResource(R.drawable.marker_red) //빨간색
                    } else {
                        marker.icon = OverlayImage.fromResource(R.drawable.marker_blue)//파란색
                    }
                }
                marker.anchor = PointF(0.5f, 0f)
                marker.map = naverMap
                marker.onClickListener = this
                markerList!!.add(marker)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_LOCATION_PERMISSION_REQUEST_CODE -> {
                locationSource!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
                return
            }
        }
    }

    private fun resetMarkerList() {
        if (markerList != null && markerList.size > 0) {
            for (marker in markerList) {
                marker.map = null
            }
            markerList.clear()
        }
    }

    override fun onClick(overlay: Overlay): Boolean {
        // 지도를 클릭하면 정보창 close
        naverMap!!.setOnMapClickListener { pointF: PointF, latLng: LatLng ->
            infoWindow!!.close()
        }
        // 마커를 클릭하면 정보창 open
        // 마커를 다시 클릭하면 정보창 close
        val marker = overlay as Marker
        if (marker.infoWindow == null){
            infoWindow!!.open(marker)
        }
        else {
            infoWindow!!.close()
        }
        return true
    }

    companion object {
        private const val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}