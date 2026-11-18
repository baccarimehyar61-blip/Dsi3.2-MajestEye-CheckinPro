
package com.example.checkinpro

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerInfo)

        // Récupération des informations
        val id = intent.getLongExtra("id", 0L)
        val nom = intent.getStringExtra("nom") ?: ""
        val prenom = intent.getStringExtra("prenom") ?: ""
        val email = intent.getStringExtra("email") ?: ""

        // Création de l'utilisateur
        val utilisateur = User(

            nom = nom,
            prenom = prenom,
            email = email,
            password = ""
        )

        // Création de la liste
        val utilisateurs = listOf(utilisateur)

        // Configuration de la RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = UtilisateurAdapter(utilisateurs) { user ->

            Toast.makeText(
                this,
                "Utilisateur sélectionné : ${user.nom}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
