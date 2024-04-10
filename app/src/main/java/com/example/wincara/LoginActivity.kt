package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DatabaseHelper(this)

        val inputUsername: EditText = findViewById(R.id.inputUsername)
        val inputPass: EditText = findViewById(R.id.inputPass)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnSignup: Button = findViewById(R.id.btnSignup)

        btnLogin.setOnClickListener {
            val inputUsername: EditText = findViewById(R.id.inputUsername)
            val inputPass: EditText = findViewById(R.id.inputPass)

            val username = inputUsername.text.trim().toString()
            val password = inputPass.text.toString()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (authenticateUser(username, password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, UserListActivity::class.java)
                startActivity(intent)


            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${DatabaseHelper.TABLE_USERS} WHERE LOWER(${DatabaseHelper.COLUMN_FIRST_NAME}) = LOWER(?) AND ${DatabaseHelper.COLUMN_PASSWORD} = ?"

        val cursor = db.rawQuery(query, arrayOf(username, password))
        var exists = false

        try {
            if (cursor.moveToFirst()) {
                exists = true
            }
        } catch (e: Exception) {
            Log.e("AuthenticationError", "Error authenticating user: ${e.message}")
        } finally {
            cursor.close()
            db.close()
        }

        return exists
    }

}