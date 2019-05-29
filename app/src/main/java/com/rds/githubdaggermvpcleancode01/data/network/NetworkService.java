package com.rds.githubdaggermvpcleancode01.data.network;

import android.content.Context;

import com.rds.githubdaggermvpcleancode01.BuildConfig;
import com.rds.githubdaggermvpcleancode01.data.network.model.GithubUser;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {
    @GET("/users")
    Observable<List<GithubUser>> getUsers(@Query("per_page") int perPage, @Query("page") int page);

    @GET("/users/{user_name}")
    Observable<GithubUser> getUserDetail(@Path("user_name") String userName);

    class Factory {
        public static NetworkService makeNetworkService(Context context) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
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
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(NetworkService.class);
        }

    }
}