package com.example.checkinpro

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        val txtWelcome = findViewById<TextView>(R.id.txtWelcomeUser)
        val txtRole = findViewById<TextView>(R.id.txtUserRole)
        val btnLogout = findViewById<Button>(R.id.btnLogoutUser)

        val fullName = intent.getStringExtra("user_name") ?: "Utilisateur"
        val role = intent.getStringExtra("user_role") ?: "TRAVAILLEUR"

        txtWelcome.text = "Bienvenue, $fullName"
        txtRole.text = "Rôle : $role"

        btnLogout.setOnClickListener {
            finish()
        }
    }
}