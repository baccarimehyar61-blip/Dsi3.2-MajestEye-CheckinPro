package com.example.checkinpro


import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("api/utilisateurs/inscrire")
    fun inscrire(@Body user: User): Call<Map<String, String>>
    @POST("api/utilisateurs/login")
    fun connecter(@Body user: LoginRequest): Call<Map<String, Any>>

}