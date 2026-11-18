
package com.example.checkinpro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL = "http://10.0.2.2:8080/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtEmail = findViewById<TextInputEditText>(R.id.edtEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        btnLogin.setOnClickListener {

            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {Toast.makeText(this,"Veuillez remplir tous les champs",Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val user = LoginRequest(email, password)

            api.connecter(user).enqueue(object : Callback<Map<String, Any>> {

                override fun onResponse(call: Call<Map<String, Any>>,response: Response<Map<String, Any>>) {

                    if (response.isSuccessful && response.body() != null) {

                        val resultat = response.body()!!

                        if (resultat["success"] == true) {

                            val utilisateur =
                                resultat["utilisateur"] as? Map<*, *>

                            if (utilisateur != null) {

                                val id = (utilisateur["id"] as? Number)?.toLong() ?: 0L
                                val nom = utilisateur["nom"]?.toString() ?: ""
                                val prenom = utilisateur["prenom"]?.toString() ?: ""
                                val emailUtilisateur = utilisateur["email"]?.toString() ?: ""

                                val intent = Intent(
                                    this@MainActivity,
                                    InfoActivity::class.java
                                )

                                intent.putExtra("id", id)
                                intent.putExtra("nom", nom)
                                intent.putExtra("prenom", prenom)
                                intent.putExtra("email", emailUtilisateur)

                                startActivity(intent)

                            } else {
                                Toast.makeText(this@MainActivity,"Utilisateur introuvable",Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Toast.makeText(this@MainActivity,resultat["message"]?.toString() ?: "Échec de connexion",Toast.LENGTH_LONG).show()
                        }

                    } else {Toast.makeText(this@MainActivity,"Erreur serveur : ${response.code()}",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>,t: Throwable) {
                    Toast.makeText(this@MainActivity,"Erreur de connexion : ${t.message}",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}
