package com.ahmedelouha.telfaza.data;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by raaja on 17-12-2017.
 */

public interface MatchRefreshInterface {

    @POST("data-match.php")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ResponseBody> refreshMatches(@Body RequestBody requestBody);

    @POST("data-match.php")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<ResponseBody> refreshSelectedMatch(@Body RequestBody requestBody);

    @GET("data-stream.php")
    Call<List<Channel>> refreshChannels(@Query("get_stream_api") int getAll);

}
