package com.example.wincara

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

                val btnDelete = findViewById<Button>(R.id.btnDelete)
                btnDelete.setOnClickListener {
                    val fullName = "$firstName $lastName" // Combine first name and last name
                    showDeleteConfirmationDialog(fullName)
                }
                tableLayout.addView(row)
            } while (cursor.moveToNext())
        }
        cursor.close()

    }

    private fun showDeleteConfirmationDialog(fullName: String) {
        val dialogView = layoutInflater.inflate(R.layout.delete_popup, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Delete User")

        val dialog = dialogBuilder.create()
        dialog.show()

        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val editTextFullName = dialogView.findViewById<EditText>(R.id.editTextFullName)

        btnConfirm.setOnClickListener {
            val fullNameParts = editTextFullName.text.toString().split(" ")
            if (fullNameParts.size != 2) {
                Toast.makeText(this, "Please enter both first name and last name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val firstName = fullNameParts[0]
            val lastName = fullNameParts[1]
            if (dbHelper.isUserExists(firstName, lastName)) {
                if (dbHelper.deleteUser(firstName, lastName)) {
                    showCustomToast("User deleted successfully", R.drawable.ok)
                } else {
                    showCustomToast("Failed to delete user", R.drawable.no)
                }
            } else {
                showCustomToast("Oops! This user doesn't exist", R.drawable.no)
            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showCustomToast(message: String, iconResId: Int) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_layout, null)
        val toastText = layout.findViewById<TextView>(R.id.toastText)
        toastText.text = message
        val toastIcon = layout.findViewById<ImageView>(R.id.toastIcon)
        toastIcon.setImageResource(iconResId)
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
    }

}