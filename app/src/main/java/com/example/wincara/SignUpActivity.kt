package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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

            val inputFirstName: EditText = findViewById(R.id.inputFirstName)
            val inputLastName: EditText = findViewById(R.id.inputLastName)

// Automatically append a single space when moving from first name to last name field
            inputFirstName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && inputFirstName.text.isNotEmpty() && inputLastName.text.isNotEmpty()) {
                    val text = inputFirstName.text
                    if (!text.endsWith(" ")) {
                        inputFirstName.text = Editable.Factory.getInstance().newEditable("$text ")
                    }
                }
            }

// Automatically append a single space when moving from last name to first name field
            inputLastName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && inputFirstName.text.isNotEmpty() && inputLastName.text.isNotEmpty()) {
                    val text = inputLastName.text
                    if (!text.startsWith(" ")) {
                        inputLastName.text = Editable.Factory.getInstance().newEditable(" $text")
                    }
                }
            }

// Trim leading and trailing spaces before saving to the database
            val firstName = inputFirstName.text.trim().toString()
            val lastName = inputLastName.text.trim().toString()

            val password = inputPassword.text.toString()
            val gender = if (male.isChecked) "Male" else "Female"
            val department = inputDepartment.text.toString()

            val result = dbHelper.addUser(firstName, lastName, password, gender, department)
            if (result != -1L) {
                Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
            }
            
            
        }
    }
}