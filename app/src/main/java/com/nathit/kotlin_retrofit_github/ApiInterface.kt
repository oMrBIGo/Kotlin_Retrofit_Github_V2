package com.nathit.kotlin_retrofit_github

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {

    @GET("users")
    fun getUserData(): Call<List<UserModel>>

}