package com.com.akshaykumar.ui.fragment.home

import android.app.Dialog
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.com.akshaykumar.R

import com.com.akshaykumar.data.db.SqliteHelper
import com.com.akshaykumar.data.home.DbRepository
import com.com.akshaykumar.databinding.FragmentAvailableWorkshopBinding

import com.com.akshaykumar.util.Utils


class AvailableWorkshopFragment : Fragment() {
    private var _binding: FragmentAvailableWorkshopBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressDialog: Dialog

    private var isUser: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailableWorkshopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialise sqlite
        val dbHelper = SqliteHelper(requireContext())
        val repository = DbRepository(dbHelper)

        //to list add in sqlite only once
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", MODE_PRIVATE)
        isUser = sharedPreferences?.getBoolean("USER", false) == true

        if (!isUser) binding.btnDashboard.visibility = View.GONE

        val notFirstTime = sharedPreferences?.getBoolean("FIRST_TIME", false)
        Log.e("AVAILABLEWORKSHOP", notFirstTime.toString())
        if (!notFirstTime!!) {
            repository.addWorkShops(Utils.workShop())
            sharedPreferences.edit().putBoolean("FIRST_TIME", true).commit()
        }

        initView(repository)
    }

    private fun initView(repository: DbRepository) {
        context?.let { progressDialog = Utils.dialog(it) }
        setUpRecyclerView(repository)

        binding.btnDashboard.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_availableWorkshopFragment_to_dashboardFragment)
        }
    }

    private fun setUpRecyclerView(repository: DbRepository) {
        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        // pass it to recyclerview layoutManager
        binding.recyclerView.layoutManager = layoutManager

        // initialize the adapter,
        // and pass the required argument
        val adapter = WorkShopAdapter(repository.getWorkShops(), object : ViewListeners {
            override fun onApply(btn: Button, id: Int) {
                if (isUser) {
                    btn.isEnabled = false
                    btn.text = "Applied"
                    repository.apply(id)
                } else {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_availableWorkshopFragment_to_signInFragment)
                }
            }
        })

        // attach adapter to the recycler view
        binding.recyclerView.adapter = adapter
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}