package com.example.campers.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.home.HotCommunityList
import com.example.campers.databinding.RecyclerHomeHotcommunityBinding

class HotCommunityAdapter(private val list: ArrayList<HotCommunityList>) :
    RecyclerView.Adapter<HotCommunityAdapter.HotCommunityViewHolder>() {

    inner class HotCommunityViewHolder(private val binding: RecyclerHomeHotcommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotCommunityList: HotCommunityList) {
            binding.hotCommunityListItem = hotCommunityList
            binding.root.setOnClickListener {
                // 상세 화면으로 이동할때 type값과 id 값을 넘겨줌
                binding.root.findNavController().navigate(R.id.communityDetailFragment, Bundle().apply {
                    putInt("type", hotCommunityList.type)
                    putInt("id", hotCommunityList.id)
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCommunityViewHolder {
        val binding = RecyclerHomeHotcommunityBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HotCommunityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotCommunityViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}