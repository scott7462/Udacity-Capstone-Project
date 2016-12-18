package scott.com.workhard.data.sourse.rest.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import scott.com.workhard.data.Injection;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/3/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class RestClient {

    private static String BASE_URL;
    private final ApiClient apiPublicService;
    private final ApiPrivateClient apiPrivateService;
    private final PublicService publicService;
    private final PrivateService privateService;

    public RestClient(String baseUrl) {
        BASE_URL = baseUrl;
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getClient())
                .build();


        Retrofit retrofitPrivate = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getPrivateClient())
                .build();


        this.apiPublicService = retrofit.create(ApiClient.class);
        this.apiPrivateService = retrofitPrivate.create(ApiPrivateClient.class);

        this.publicService = new PublicService(getApiPublicService());
        this.privateService = new PrivateService(getApiPrivateService());
    }

    private ApiClient getApiPublicService() {
        return this.apiPublicService;
    }

    public PublicService getPublicService() {
        return this.publicService;
    }

    public ApiPrivateClient getApiPrivateService() {
        return apiPrivateService;
    }

    public PrivateService getPrivateService() {
        return privateService;
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    public OkHttpClient getPrivateClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new StethoInterceptor());
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + Injection.provideSessionRepository().getToken())
                        .build();

                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }
}
