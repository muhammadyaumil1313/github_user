package org.daylab.githubuser.models

import com.google.gson.annotations.SerializedName

data class Followers(
    @SerializedName("avatar_url")
    val avatarUrl: String, // https://avatars.githubusercontent.com/u/54166418?v=4
    @SerializedName("login")
    val login: String, // MuhammadYaumil1212
)
