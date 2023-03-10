package org.daylab.githubuser.utils

import androidx.lifecycle.LiveData
import org.daylab.githubuser.models.Item
import org.daylab.githubuser.models.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getListUsers() : Call<List<Item>>

    @GET("/users/{username}")
    fun getDetailUser(@Path("username") username : String) : Call<Item>

    @GET("/search/users")
    fun searchData( @Query("q") username: String) : Call<ResponseUser>

    @GET("/users/{username}/followers")
    fun getFollowers(@Path("username") username: String) : Call<List<Item>>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String) : Call<List<Item>>
}