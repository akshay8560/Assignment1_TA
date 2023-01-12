package com.com.akshaykumar.ui.fragment.auth.listener

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}