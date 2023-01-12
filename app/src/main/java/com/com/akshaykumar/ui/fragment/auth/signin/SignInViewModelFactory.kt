package com.com.akshaykumar.ui.fragment.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.com.akshaykumar.data.auth.AuthRepository

class SignInViewModelFactory(
    private val repository: AuthRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(repository) as T
        }
        throw IllegalArgumentException("Something is wrong with viewmodel!!")
    }
}