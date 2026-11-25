package com.example.checkinpro

data class User(
    val id: Int? = null,
    val fullName: String,
    val email: String,
    val password: String,
    val role: String = "TRAVAILLEUR"
)