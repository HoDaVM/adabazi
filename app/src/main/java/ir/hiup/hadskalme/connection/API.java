package ir.hiup.hadskalme.connection;

import ir.hiup.hadskalme.connection.callbacks.CallBackAppVersion;
import ir.hiup.hadskalme.connection.callbacks.CallBackVideoAds;
import ir.hiup.hadskalme.connection.callbacks.CallBackWord;
import ir.hiup.hadskalme.data.Constant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface API {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "User-Agent: Game";
    String SECURITY = "Security: " + Constant.SECURITY_CODE;

    @Headers({CACHE, AGENT, SECURITY})
    @GET("getFullscreenADS")
    Call<CallBackVideoAds> getFullscreenADS(
            @Query("location") int location
    );

    @Headers({CACHE, AGENT, SECURITY})
    @GET("GetWord")
    Call<CallBackWord> GetWord(
            @Query("id") int id,
            @Query("pic") String pic,
            @Query("points") int point
    );

    @Headers({CACHE, AGENT, SECURITY})
    @GET("GettAppVersionCafeBazaar")
    Call<CallBackAppVersion> GettAppVersion(
            @Query("location") int location
    );
}
