package org.daylab.githubuser.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Love(
    @SerializedName("name")
    val name : String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?, // https://avatars.githubusercontent.com/u/54166418?v=4
    @SerializedName("login")
    val login: String?, // MuhammadYaumil1212
    @SerializedName("id")
    var id: Int?, // 54166418
) : Parcelable
