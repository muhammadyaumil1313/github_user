package org.daylab.githubuser.models

import com.google.gson.annotations.SerializedName

data class Love(
    @SerializedName("name")
    val name : String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?, // https://avatars.githubusercontent.com/u/54166418?v=4
    @SerializedName("login")
    val login: String?, // MuhammadYaumil1212
    @SerializedName("id")
    var id: Int?, // 54166418
)
