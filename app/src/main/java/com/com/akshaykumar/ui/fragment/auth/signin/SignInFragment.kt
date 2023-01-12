package com.com.akshaykumar.ui.fragment.auth.signin

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.com.akshaykumar.R


import com.com.akshaykumar.data.auth.AuthRepository
import com.com.akshaykumar.data.firebase.FirebaseAuthSource
import com.com.akshaykumar.databinding.FragmentSignInBinding

import com.com.akshaykumar.ui.fragment.auth.listener.AuthListener
import com.com.akshaykumar.ui.fragment.auth.signup.msg
import com.com.akshaykumar.util.Utils

class SignInFragment : Fragment(), AuthListener {

    private val TAG: String = "SignInFragment"

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)

        val repository = AuthRepository(FirebaseAuthSource())
        val viewModelProviderFactory = SignInViewModelFactory(repository)
        val viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[SignInViewModel::class.java]
        binding.signInViewModel = viewModel

        //set listeners to track
        viewModel.authListener = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { progressDialog = Utils.dialog(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStarted() {
        progressDialog.show()
    }

    override fun onSuccess() {
        context.msg("Successfully login")
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.putBoolean("USER", true)?.commit()
        progressDialog.dismiss()
        Navigation.findNavController(binding.root).navigate(R.id.action_signInFragment_to_dashboardFragment)
    }

    override fun onFailure(message: String) {
        progressDialog.dismiss()
        context.msg(message)
    }
}