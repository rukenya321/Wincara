package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge

class LoginActivity : AppCompatActivity() {
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


        val btnSignupinstead = findViewById<Button>(R.id.btnSignupinstead)
        btnSignupinstead.setOnClickListener {
            // Start the Sign Up Activity when the button is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}