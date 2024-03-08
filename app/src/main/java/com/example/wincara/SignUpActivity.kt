package com.example.wincara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        dbHelper = DatabaseHelper(this)

        val inputFirstName: EditText = findViewById(R.id.inputFirstName)
        val inputLastName: EditText = findViewById(R.id.inputLastName)
        val inputPassword: EditText = findViewById(R.id.inputPassword)
        val inputDepartment: EditText = findViewById(R.id.inputDepartment)
        val male: CheckBox = findViewById(R.id.male)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            val firstName = inputFirstName.text.toString()
            val lastName = inputLastName.text.toString()
            val password = inputPassword.text.toString()
            val gender = if (male.isChecked) "Male" else "Female"
            val department = inputDepartment.text.toString()

            val result = dbHelper.addUser(firstName, lastName, password, gender, department)
            if (result != -1L) {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
        }
    }
}