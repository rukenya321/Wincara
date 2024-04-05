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

// Trim leading and trailing spaces before authentication
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

        // Define the query to check for username and password
        val query = "SELECT * FROM ${DatabaseHelper.TABLE_USERS} WHERE LOWER(${DatabaseHelper.COLUMN_FIRST_NAME}) = LOWER(?) AND ${DatabaseHelper.COLUMN_PASSWORD} = ?"

        // Execute the query with the provided username and password
        val cursor = db.rawQuery(query, arrayOf(username, password))
        var exists = false

        try {
            // Check if the cursor has any rows (i.e., if the user exists)
            if (cursor.moveToFirst()) {
                exists = true
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the query execution
            Log.e("AuthenticationError", "Error authenticating user: ${e.message}")
        } finally {
            // Close the cursor and the database connection
            cursor.close()
            db.close()
        }

        return exists
    }

}