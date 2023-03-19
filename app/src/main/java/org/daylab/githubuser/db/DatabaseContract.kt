package org.daylab.githubuser.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class LoveColumn : BaseColumns{
        companion object{
            const val TABLE_NAME = "loves"
            const val _ID = "_id"
            const val avatar_url = "avatar_url"
            const val login = "login"
            const val name = "name"
            const val followingUrl = "following_url"
            const val followersUrl = "followers_url"
        }
    }
}