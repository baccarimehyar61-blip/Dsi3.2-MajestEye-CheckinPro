package com.example.checkinpro

data class User(


    val nom: String,
    val prenom: String,
    val email: String,
    val password: String
)
data class LoginRequest(
    val email: String,
    val password: String
)