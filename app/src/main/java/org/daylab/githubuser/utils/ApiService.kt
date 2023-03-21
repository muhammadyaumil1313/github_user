package org.daylab.githubuser.utils

import androidx.lifecycle.LiveData
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.models.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    @Headers("Authorization: token <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>")
    @GET("users")
    fun getListUsers() : Call<List<Item>>

//    @Headers("Authorization: token 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")
    @GET("/users/{username}")
    fun getDetailUser(@Path("username") username : String) : Call<Item>

//    @Headers("Authorization: token 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")
    @GET("/search/users")
    fun searchData( @Query("q") username: String) : Call<ResponseUser>

//    @Headers("Authorization: token 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")
    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String) : Call<List<Item>>

    @Headers("Authorization: token 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")
    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String) : Call<List<Item>>
}