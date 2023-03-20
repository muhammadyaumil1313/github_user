package org.daylab.githubuser.helper

import android.database.Cursor
import org.daylab.githubuser.db.DatabaseContract
import org.daylab.githubuser.models.Love

object MappingHelper {
    fun mapCursorToArrayList(loveCursor: Cursor?): ArrayList<Love> {
        val loveList = ArrayList<Love>()
        loveCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.LoveColumn._ID))
                val avatarUrl = getString(getColumnIndexOrThrow(DatabaseContract.LoveColumn.avatar_url))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.LoveColumn.name))
                val login = getString(getColumnIndexOrThrow(DatabaseContract.LoveColumn.login))
                loveList.add(
                    Love(
                        id = id.toString(),
                        name = name,
                        login = login,
                        avatarUrl = avatarUrl,
                    )
                )
            }
        }
        return loveList
    }
}