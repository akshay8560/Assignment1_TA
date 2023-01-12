package com.com.akshaykumar.ui.fragment.auth.signin

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.com.akshaykumar.R

import com.com.akshaykumar.data.auth.AuthRepository
import com.com.akshaykumar.ui.fragment.auth.listener.AuthListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    //email and password for the input
    var email: String? = null
    var password: String? = null

    //auth listener
    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    //function to perform login
    fun login() {

        //validating email and password
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }

        if (password?.length!! < 6) {
            authListener?.onFailure("Password length should not less than 6")
            return
        }

        //authentication started
        authListener?.onStarted()

        //calling login from repository to perform the actual authentication
        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //sending a success callback
                authListener?.onSuccess()
            }, {
                //sending a failure callback
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }


    fun goToSignUp(view: View) =
        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment)

    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}