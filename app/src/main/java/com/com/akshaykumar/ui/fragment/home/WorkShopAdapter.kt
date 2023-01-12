package com.com.akshaykumar.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

import com.com.akshaykumar.data.model.Workshop
import com.com.akshaykumar.databinding.LayoutWorkshopBinding


class WorkShopAdapter(
    private val workshops: List<Workshop>,
    private val listeners: ViewListeners
) : RecyclerView.Adapter<WorkShopAdapter.WorkShopViewHolder>() {

    inner class WorkShopViewHolder(val binding: LayoutWorkshopBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkShopViewHolder {
        val binding = LayoutWorkshopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkShopViewHolder, position: Int) {
        val workshop = workshops[position]
        holder.binding.apply {
            txtWorkshopName.text = workshop.name
            txtCompanyName.text = "by ${workshop.company}"
            txtDescription.text = workshop.description
            txtDate.text = workshop.date
            if (workshop.applied == 1){
                btnApply.isEnabled = false
                btnApply.text = "Applied"
            }
            btnApply.setOnClickListener {
                listeners.onApply(btnApply,workshop.id)
            }
        }
    }

    override fun getItemCount(): Int = workshops.size
}

interface ViewListeners {
    fun onApply(btn: Button, id: Int)
}