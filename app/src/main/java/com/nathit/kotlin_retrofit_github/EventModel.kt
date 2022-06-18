package com.nathit.kotlin_retrofit_github

data class EventModel(
    val type: String?,
    val actor: ArrayList<Actor>,
    val repo: ArrayList<Repo>,
    val created_at: String?
)

data class Actor(
    val id: Int?,
    val login: String?,
    val url: String?,
    val avatar_url: String?
    )

data class Repo(
    val id: Int?,
    val name: String?,
    val url: String?
)
