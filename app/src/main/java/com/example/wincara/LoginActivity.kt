package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

/*class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val inputUsername: EditText = findViewById(R.id.inputUsername)
        val inputPass: EditText = findViewById(R.id.inputPass)

        // Example in LoginActivity
        val dbHelper = DatabaseHelper(this)
        val username = inputUsername.text.toString()
        val password = inputPass.text.toString()

        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM users WHERE username = '$username' AND password = '$password'", null)

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


        val btnSignupinstead = findViewById<Button>(R.id.btnSignup)
        btnSignupinstead.setOnClickListener {
            // Start the Sign Up Activity when the button is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}*/

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
            val username = inputUsername.text.toString()
            val password = inputPass.text.toString()

            // Validate input fields (optional)
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if user exists in the database
            if (authenticateUser(username, password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // Navigate to the next screen
                // val intent = Intent(this, NextActivity::class.java)
                // startActivity(intent)
                // finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

        btnSignup.setOnClickListener {
            // Start the SignUpActivity when the button is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        // Perform authentication logic here, such as querying the database
        // Return true if the user is authenticated, false otherwise
        // For demo purposes, always return true
        return true
    }
}