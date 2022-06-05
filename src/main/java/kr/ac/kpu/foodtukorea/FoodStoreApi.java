package kr.ac.kpu.foodtukorea;

import kr.ac.kpu.foodtukorea.domain.FoodStoreApiResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodStoreApi {
//    @GET("RestrtSanittnGradStus?KEY=e9d760bbea604ab0900bcd74d4f95be6&pSize=250&Type=json")
//    Call<FoodStoreApiResult> getStoreByGeo(@Query("SIGUN_CD") int sigun);
    @GET("RestrtSanittnGradStus?KEY=e9d760bbea604ab0900bcd74d4f95be6&pSize=250&Type=json&SIGUN_CD=41390")
    Call<FoodStoreApiResult> getStoreByGeo();

    //https://openapi.gg.go.kr/RestrtSanittnGradStus?KEY=e9d760bbea604ab0900bcd74d4f95be6&pSize=250&Type=json&SIGUN_CD=41390

}
