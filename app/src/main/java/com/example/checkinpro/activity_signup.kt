
package com.example.checkinpro

import android.os.Bundle
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignupActivity : AppCompatActivity() {

    private val BASE_URL = "http://10.0.2.2:8080/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val edtNom = findViewById<TextInputEditText>(R.id.edtNom)
        val edtPrenom = findViewById<TextInputEditText>(R.id.edtPrenom)
        val edtEmail = findViewById<TextInputEditText>(R.id.edtEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.edtPassword)

        val btnSignUp = findViewById<MaterialButton>(R.id.btnSignUp)
        val txtLogin = findViewById<TextView>(R.id.txtLogin)

        val instance: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        btnSignUp.setOnClickListener {

            val nom = edtNom.text.toString().trim()
            val prenom = edtPrenom.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (nom.isEmpty() || prenom.isEmpty() ||
                email.isEmpty() || password.isEmpty()) {

                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Le mot de passe doit contenir au moins 6 caractères", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                nom = nom,
                prenom = prenom,
                email = email,
                password = password
            )

            instance.inscrire(user).enqueue(object : Callback<Map<String, String>> {

                override fun onResponse(
                    call: Call<Map<String, String>>,
                    response: Response<Map<String, String>>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()?.get("message") ?: "Réponse reçue"

                        Toast.makeText(this@SignupActivity, message, Toast.LENGTH_SHORT).show()

                        if (message.contains("réussie", ignoreCase = true)) {
                            finish()
                        }
                    } else {
                        Toast.makeText(this@SignupActivity,"Erreur serveur : ${response.code()}",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                    Toast.makeText(this@SignupActivity,"Erreur de connexion : ${t.message}",Toast.LENGTH_LONG).show()
                }
            })
        }

        txtLogin.setOnClickListener {
            finish()
        }
    }
}
