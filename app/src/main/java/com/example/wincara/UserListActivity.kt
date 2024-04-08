package com.example.wincara

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        dbHelper = DatabaseHelper(this)
        displayUserList()
    }

    @SuppressLint("Range")
    private fun displayUserList() {
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM ${DatabaseHelper.TABLE_USERS}", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID))
                val firstName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LAST_NAME))
                val gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GENDER))
                val department = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DEPARTMENT))

                val row = TableRow(this)

                val idTextView = TextView(this)
                idTextView.text = id.toString()
                row.addView(idTextView)

                val firstNameTextView = TextView(this)
                firstNameTextView.text = firstName
                row.addView(firstNameTextView)

                val lastNameTextView = TextView(this)
                lastNameTextView.text = lastName
                row.addView(lastNameTextView)

                val genderTextView = TextView(this)
                genderTextView.text = gender
                row.addView(genderTextView)

                val departmentTextView = TextView(this)
                departmentTextView.text = department
                row.addView(departmentTextView)

                tableLayout.addView(row)
            } while (cursor.moveToNext())
        }
        cursor.close()
    }
}
