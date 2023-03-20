package org.daylab.githubuser.models


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("name")
    val name : String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?, // https://avatars.githubusercontent.com/u/54166418?v=4
    @SerializedName("login")
    val login: String?, // MuhammadYaumil1212
    @SerializedName("followers_url")
    val followersUrl: String?, // https://api.github.com/users/MuhammadYaumil1212/followers
    @SerializedName("following_url")
    val followingUrl: String?, // https://api.github.com/users/MuhammadYaumil1212/following{/other_user}
    @SerializedName("id")
    val id: String?, // 54166418
)