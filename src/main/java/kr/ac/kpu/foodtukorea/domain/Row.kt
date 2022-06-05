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

class Row {

    @SerializedName("SIGUN_CD")
    @Expose
    var sigunCd: String? = null

    @SerializedName("SIGUN_NM")
    @Expose
    var sigunNm: String? = null

    @SerializedName("ENTRPS_NM")
    @Expose
    var entrpsNm: String? = null

    @SerializedName("APPONT_GRAD")
    @Expose
    var appontGrad: String? = null

    @SerializedName("APPONT_DE")
    @Expose
    var appontDe: String? = null

    @SerializedName("APPONT_INST_DIV_NM")
    @Expose
    var appontInstDivNm: String? = null

    @SerializedName("RM")
    @Expose
    var rm: Any? = null

    @SerializedName("REFINE_ROADNM_ADDR")
    @Expose
    var refineRoadnmAddr: String? = null

    @SerializedName("REFINE_LOTNO_ADDR")
    @Expose
    var refineLotnoAddr: String? = null

    @SerializedName("REFINE_WGS84_LAT")
    @Expose
    var refineWgs84Lat: String? = null

    @SerializedName("REFINE_WGS84_LOGT")
    @Expose
    var refineWgs84Logt: String? = null
}