package org.daylab.githubuser.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import org.daylab.githubuser.db.DatabaseContract.LoveColumn
import java.sql.SQLException

class LoveHelper(context: Context) {
    private val DatabaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = LoveColumn.TABLE_NAME
        private val INSTANCE : LoveHelper? = null
        fun getInstance(context: Context):LoveHelper=
            INSTANCE ?: synchronized(this){
                INSTANCE?:LoveHelper(context)
            }
    }
    fun checkIfExist(username : String?) : Boolean {
        val sql = "SELECT COUNT(*) FROM ${LoveColumn.TABLE_NAME} WHERE ${LoveColumn.login} = ?"
        val cursor = database.rawQuery(sql, arrayOf(username))
        var exist = false
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            exist = count > 0
        }
        cursor.close()
        return exist
    }
    @Throws(SQLException::class)
    fun open() {
        database = DatabaseHelper.writableDatabase
    }

    fun queryAll() : Cursor{
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "${LoveColumn._ID} ASC"

        )
    }
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "${LoveColumn._ID} = ?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }
    fun insert(value: ContentValues?) : Long{
        return database.insert(DATABASE_TABLE,null,value)
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "${LoveColumn._ID} = '$id'", null)
    }
    fun close() {
        DatabaseHelper.close()
        if (database.isOpen)
            database.close()
    }
}