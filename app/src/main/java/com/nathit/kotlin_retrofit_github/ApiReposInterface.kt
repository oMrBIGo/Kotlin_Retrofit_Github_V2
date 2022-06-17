package com.nathit.kotlin_retrofit_github

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiReposInterface {
    @GET("users/{login}/repos")
    fun loadRepos(
        @Path("login") login: String?
    ): Call<List<ReposModel>>

    companion object {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}