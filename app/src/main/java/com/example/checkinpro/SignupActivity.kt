
package com.example.checkinpro

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var bdService: BDLocalService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        bdService = BDLocalService(this)

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
                password.isEmpty() || confirmPassword.isEmpty()
            ) {

                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()

            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                Toast.makeText(this, "IAdresse e-mail invalide", Toast.LENGTH_SHORT).show()

            } else if (password.length < 6) {

                Toast.makeText(
                    this,
                    "Le mot de passe doit contenir au moins 6 caractères",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (password != confirmPassword) {

                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()

            } else {
                val result = bdService.addUser(
                    User(
                        fullName = name,
                        email = email,
                        password = password,
                        role = "TRAVAILLEUR"
                    )
                )

                if (result != -1L) {
                    Toast.makeText(this, "Compte créé avec succès !!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Cette adresse e-mail existe déjà", Toast.LENGTH_SHORT).show()
                }
            }
        }

        txtLogin.setOnClickListener {
            finish()
        }
    }}