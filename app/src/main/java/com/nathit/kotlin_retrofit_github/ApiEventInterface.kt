package com.nathit.kotlin_retrofit_github

import retrofit2.Call
import retrofit2.http.GET

interface ApiEventInterface {

    @GET("events")
    fun loadEvent(): Call<List<EventModel>>

}
