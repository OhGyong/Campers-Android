package com.example.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.data.home.HotCommunityList
import com.example.campers.databinding.RecyclerHomeHotcommunityBinding

class HotCommunityAdapter(private val list: ArrayList<HotCommunityList>): RecyclerView.Adapter<HotCommunityAdapter.HotCommunityViewHolder>() {

    inner class HotCommunityViewHolder(private val binding: RecyclerHomeHotcommunityBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(hotCommunityList: HotCommunityList){
            binding.hotCommunityListItem = hotCommunityList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCommunityViewHolder {
        val binding = RecyclerHomeHotcommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotCommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotCommunityViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }

}