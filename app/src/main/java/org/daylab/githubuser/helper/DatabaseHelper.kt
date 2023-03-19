package org.daylab.githubuser.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.daylab.githubuser.db.DatabaseContract.LoveColumn

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "GitUsersDb"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_Love = """
            CREATE TABLE ${LoveColumn.TABLE_NAME} 
            (
                ${LoveColumn._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${LoveColumn.name} TEXT NOT NULL,
                ${LoveColumn.avatar_url} TEXT NOT NULL,
                ${LoveColumn.login} TEXT NOT NULL,
                ${LoveColumn.followersUrl} TEXT NOT NULL,
                ${LoveColumn.followingUrl} TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_Love)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${LoveColumn.TABLE_NAME}")
        onCreate(db)
    }
}