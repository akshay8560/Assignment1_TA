package com.com.akshaykumar.data.model

data class Workshop(
    val id: Int = 0,
    val name: String,
    val company: String,
    val description: String,
    val date: String,
    val applied: Int = 0
)