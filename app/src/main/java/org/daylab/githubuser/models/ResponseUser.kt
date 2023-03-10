package org.daylab.githubuser.models


import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int // 1
)