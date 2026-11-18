
package com.example.checkinpro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Masquer la barre d'action
        supportActionBar?.hide()

        val edtFullName = findViewById<EditText>(R.id.edtFullName)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val edtConfirmPassword = findViewById<EditText>(R.id.edtConfirmPassword)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        btnSignUp.setOnClickListener {

            val name = edtFullName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {

                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length < 6) {

                Toast.makeText(this, "Password must contain at least 6 characters", Toast.LENGTH_SHORT).show()

            } else if (password != confirmPassword) {

                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        txtLogin.setOnClickListener {
            finish()
        }
    }
}
