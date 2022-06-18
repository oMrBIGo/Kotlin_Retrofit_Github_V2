package com.nathit.kotlin_retrofit_github

data class EventModel(
    val type: String?,
    val created_at: String?,
    val actor: Actor,
    val repo: Repo
)

data class Actor(
    val login: String?,
    val url: String?,
    val avatar_url: String?
    )

data class Repo(
    val name: String?
)
