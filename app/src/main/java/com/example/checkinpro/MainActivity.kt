
package com.example.checkinpro

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Masquer la barre d'action
        supportActionBar?.hide()

        // Personnaliser les barres système
        window.statusBarColor = Color.rgb(36, 16, 100)
        window.navigationBarColor = Color.rgb(16, 26, 60)

        // Récupérer les composants
        val email = findViewById<EditText>(R.id.edtEmail)
        val password = findViewById<EditText>(R.id.edtPassword)
        val remember = findViewById<CheckBox>(R.id.chkRemember)

        val login = findViewById<Button>(R.id.btnLogin)
        val forgot = findViewById<TextView>(R.id.txtForgot)
        val signup = findViewById<TextView>(R.id.txtSignup)

        // Bouton Login
        login.setOnClickListener {

            val e = email.text.toString().trim()
            val p = password.text.toString()

            if (e.isEmpty()) {
                email.error = "Email is required"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                email.error = "Invalid email address"
            }
            else if (p.isEmpty()) {
                password.error = "Password is required"
            }
            else if (p.length < 6) {
                password.error = "Minimum 6 characters"
            }
            else {
                Toast.makeText(
                    this,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                if (remember.isChecked) {
                    Toast.makeText(
                        this,
                        "Remember me activated",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Forgot Password
        forgot.setOnClickListener {
            Toast.makeText(
                this,
                "Forgot Password clicked",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Sign Up
        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
