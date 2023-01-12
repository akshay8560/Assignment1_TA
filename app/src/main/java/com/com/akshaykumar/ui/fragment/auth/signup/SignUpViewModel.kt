package com.com.akshaykumar.ui.fragment.auth.signup

import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import com.com.akshaykumar.R
import com.com.akshaykumar.data.auth.AuthRepository
import com.com.akshaykumar.ui.fragment.auth.listener.AuthListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    //email, password, and other for the input
    var fullName: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null

    //auth listener
    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    //function to perform signup
    fun signup() {
        if (fullName.isNullOrEmpty()
            || email.isNullOrEmpty()
            || password.isNullOrEmpty()
            || confirmPassword.isNullOrEmpty()
        ) {
            authListener?.onFailure("Please input all values")
            return
        }
        if (!email.isValidEmail()) {
            authListener?.onFailure("Please Enter valid email")
            return
        }
        if (!password.equals(confirmPassword)) {
            authListener?.onFailure("Password not match")
            return
        }
        if (password?.length!! < 6 || confirmPassword?.length!! < 6) {
            authListener?.onFailure("Password length should not less than 6")
            return
        }

        authListener?.onStarted()
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToLogin(view: View) =
        findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)


    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}

fun String?.isValidEmail(): Boolean =
    !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
