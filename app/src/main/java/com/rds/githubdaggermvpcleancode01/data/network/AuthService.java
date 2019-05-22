package com.rds.githubdaggermvpcleancode01.data.network;

import android.content.Context;

import com.rds.githubdaggermvpcleancode01.BuildConfig;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginCredentials;
import com.rds.githubdaggermvpcleancode01.data.network.model.LoginResponse;

import java.io.IOException;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
//    @POST("/posts")
//    @FormUrlEncoded
//    Observable<LoginResponse> goLogin (@Field("title") String title, @Field("body") String body, @Field("userId") long userId);
@POST("/user/login")
    Observable<LoginResponse> goLogin(@Body LoginCredentials credentials);

//    @POST("/posts")
//    Observable<LoginResponse> goLogin(@Body LoginCredentials credentials);

    class Factory {
        public static AuthService makeLoginService(Context context) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request request = original.newBuilder()
                                    .header("Content-Type", "application/json")
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                    .build();

                            Response response = chain.proceed(request);
                            response.cacheResponse();
                            // Customize or return the response
                            return response;

                        }
                    })

                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.LOGIN_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(AuthService.class);
        }
    }

}
