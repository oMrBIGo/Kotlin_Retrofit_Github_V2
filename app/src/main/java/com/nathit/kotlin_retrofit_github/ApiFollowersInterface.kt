package com.nathit.kotlin_retrofit_github

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiFollowersInterface {
    @GET("users/{login}/followers")
    fun loadFollowers(
        @Path("login") login: String?
    ): Call<List<FollowersModel>>

    companion object {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}