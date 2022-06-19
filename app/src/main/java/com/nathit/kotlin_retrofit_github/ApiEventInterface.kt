package com.nathit.kotlin_retrofit_github

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiEventInterface {

    @GET("events")
    fun loadEvent(): Call<List<EventModel>>

    companion object {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
