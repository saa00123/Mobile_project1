package kr.ac.kpu.foodtukorea.domain

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import androidx.appcompat.app.AppCompatActivity
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.overlay.InfoWindow
import android.os.Bundle
import kr.ac.kpu.foodtukorea.R
import kr.ac.kpu.foodtukorea.MainActivity
import com.naver.maps.map.UiSettings
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.InfoWindow.DefaultTextAdapter
import com.naver.maps.map.CameraPosition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kr.ac.kpu.foodtukorea.FoodStoreApi
import kr.ac.kpu.foodtukorea.domain.FoodStoreApiResult
import kr.ac.kpu.foodtukorea.domain.RestrtSanittnGradStu
import com.naver.maps.map.overlay.OverlayImage
import android.graphics.PointF

class Head {
    @SerializedName("list_total_count")
    @Expose
    var listTotalCount: Int? = null

    @SerializedName("RESULT")
    @Expose
    var result: Result? = null

    @SerializedName("api_version")
    @Expose
    var apiVersion: String? = null
}