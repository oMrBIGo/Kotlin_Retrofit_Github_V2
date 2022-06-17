package com.nathit.kotlin_retrofit_github

data class ReposModel (
    val name: String,
    val description: String,
    val html_url: String,
    val created_at: String,
    val updated_at: String,
    val pushed_at: String,
    val language: String,
    val stargazers_count: String,
    val forks_count: String,
    val visibility: String
        )
