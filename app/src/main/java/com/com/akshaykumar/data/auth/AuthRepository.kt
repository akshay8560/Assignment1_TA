package com.com.akshaykumar.data.auth

import com.com.akshaykumar.data.firebase.FirebaseAuthSource

class AuthRepository(
    private val firebase: FirebaseAuthSource
) {
    fun login(email: String, password: String) = firebase.login(email, password)

    fun register(email: String, password: String) = firebase.register(email, password)

    fun currentUser() = firebase.currentUser()

    fun logout() = firebase.logout()
}