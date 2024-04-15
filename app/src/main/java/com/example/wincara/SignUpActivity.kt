package com.example.wincara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    private fun showCustomToast(message: String, iconResId: Int) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_layout, null)

        // Set the text and icon
        val toastText = layout.findViewById<TextView>(R.id.toastText)
        toastText.text = message

        val toastIcon = layout.findViewById<ImageView>(R.id.toastIcon)
        toastIcon.setImageResource(iconResId)

        // Create and show the toast
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

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

            inputFirstName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && inputFirstName.text.isNotEmpty() && inputLastName.text.isNotEmpty()) {
                    val text = inputFirstName.text
                    if (!text.endsWith(" ")) {
                        inputFirstName.text = Editable.Factory.getInstance().newEditable("$text ")
                    }
                }
            }

            inputLastName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && inputFirstName.text.isNotEmpty() && inputLastName.text.isNotEmpty()) {
                    val text = inputLastName.text
                    if (!text.startsWith(" ")) {
                        inputLastName.text = Editable.Factory.getInstance().newEditable(" $text")
                    }
                }
            }

            val firstName = inputFirstName.text.trim().toString()
            val lastName = inputLastName.text.trim().toString()
            val password = inputPassword.text.toString()
            val gender = if (male.isChecked) "Male" else "Female"
            val department = inputDepartment.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || department.isEmpty()) {
                // Show error toast if any field is empty
                showCustomToast("Please fill in all fields", R.drawable.no)
                return@setOnClickListener
            }

            val result = dbHelper.addUser(firstName, lastName, password, gender, department)
            if (result != -1L) {
                showCustomToast("User added successfully", R.drawable.ok)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                showCustomToast("Failed to add user", R.drawable.no)
            }

        }
    }
}

