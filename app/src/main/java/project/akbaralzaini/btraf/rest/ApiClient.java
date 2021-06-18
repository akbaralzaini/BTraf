package project.akbaralzaini.btraf.rest;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://13.213.140.15:8080/finalproject-0.0.1/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String token) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer "+token) // <-- this is the important line
                    .header("Content-Type", "application/json");

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();


        }
        return retrofit;
    }
}
