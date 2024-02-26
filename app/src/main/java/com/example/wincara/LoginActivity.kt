package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignupinstead = findViewById<Button>(R.id.btnSignupinstead)
        btnSignupinstead.setOnClickListener {
            // Start the Sign Up Activity when the button is clicked
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}