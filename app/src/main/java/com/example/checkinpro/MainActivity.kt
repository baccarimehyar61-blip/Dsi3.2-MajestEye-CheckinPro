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

    private lateinit var bdService: BDLocalService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bdService = BDLocalService(this)

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
                email.error = "L'adresse e-mail est obligatoire"
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                email.error = "Adresse e-mail invalide"
            }
            else if (p.isEmpty()) {
                password.error = "Le mot de passe est obligatoire"
            }
            else if (p.length < 6) {
                password.error = "Minimum 6 caractères"
            }
            else {
                val connectedUser = bdService.checkLogin(e, p)

                if (connectedUser != null) {
                    Toast.makeText(
                        this,
                        "Connexion réussie",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (remember.isChecked) {
                        Toast.makeText(
                            this,
                            "Se souvenir de moi activé",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (connectedUser.role == "ADMIN") {
                        startActivity(Intent(this, AdminActivity::class.java))
                    } else {
                        val intent = Intent(this, UserHomeActivity::class.java)
                        intent.putExtra("user_name", connectedUser.fullName)
                        intent.putExtra("user_role", connectedUser.role)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Adresse e-mail ou mot de passe incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Forgot Password
        forgot.setOnClickListener {
            Toast.makeText(
                this,
                "Mot de passe oublié cliqué",
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