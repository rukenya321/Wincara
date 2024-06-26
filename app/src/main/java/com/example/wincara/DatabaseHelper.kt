package com.example.wincara

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "wincara.db"

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_FIRST_NAME = "firstName"
        const val COLUMN_LAST_NAME = "lastName"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_DEPARTMENT = "department"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = ("CREATE TABLE $TABLE_USERS " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_FIRST_NAME TEXT, " +
                "$COLUMN_LAST_NAME TEXT, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_GENDER TEXT, " +
                "$COLUMN_DEPARTMENT TEXT)")
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(
        firstName: String,
        lastName: String,
        password: String,
        gender: String,
        department: String
    ): Long {
        val db = this.writableDatabase

        if (isUserExists(firstName, lastName)) {
            return -1
        }

        val values = ContentValues().apply {
            put(COLUMN_FIRST_NAME, firstName)
            put(COLUMN_LAST_NAME, lastName)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_GENDER, gender)
            put(COLUMN_DEPARTMENT, department)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    fun isUserExists(firstName: String, lastName: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_FIRST_NAME = ? AND $COLUMN_LAST_NAME = ?"
        val cursor = db.rawQuery(query, arrayOf(firstName, lastName))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun deleteUser(firstName: String, lastName: String): Boolean {
        val db = writableDatabase
        val selection = "$COLUMN_FIRST_NAME = ? AND $COLUMN_LAST_NAME = ?"
        val selectionArgs = arrayOf(firstName, lastName)
        val result = db.delete(TABLE_USERS, selection, selectionArgs)
        db.close()
        return result != 0
    }
}