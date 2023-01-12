package com.com.akshaykumar.ui.fragment.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import com.com.akshaykumar.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences("SESSION", Context.MODE_PRIVATE)
        val isUser = sharedPreferences?.getBoolean("USER", false) == true

        if (!isUser) {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_without_login_dashboardFragment_to_availableWorkshopFragment)
        }

        //initialise sqlite
        val dbHelper = SqliteHelper(requireContext())
        val repository = DbRepository(dbHelper)

        binding.btnAvailableWorkshop.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_dashboardFragment_to_availableWorkshopFragment)
        }
        binding.btnRefresh.setOnClickListener {
            setUpRecyclerView(repository)
        }

        setUpRecyclerView(repository)
    }

    private fun setUpRecyclerView(repository: DbRepository) {
        //check list is empty or not 
        if (repository.appliedWorkshops().size > 0) {
            binding.recyclerView.visibility  = View.VISIBLE
            binding.txtNote.visibility = View.GONE
        
        // create  layoutManager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        // pass it to recyclerview layoutManager
        binding.recyclerView.layoutManager = layoutManager

        // initialize the adapter,
        // and pass the required argument
        val adapter = WorkShopAdapter(repository.appliedWorkshops(), object : ViewListeners {
            override fun onApply(btn: Button, id: Int) {
            }
        })

        // attach adapter to the recycler view
        binding.recyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}