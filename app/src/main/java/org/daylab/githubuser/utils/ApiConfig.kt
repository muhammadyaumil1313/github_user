package org.daylab.githubuser.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.daylab.githubuser.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService() : ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor()
                    .setLevel(
                        HttpLoggingInterceptor.Level.BODY
                    )
            }else{
                HttpLoggingInterceptor()
                    .setLevel(
                        HttpLoggingInterceptor.Level.NONE
                    )
            }
            val clientOk = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit
                .Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(clientOk)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}