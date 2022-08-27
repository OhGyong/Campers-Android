package com.campers.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.campers.R
import com.campers.data.home.HotCommunityList
import com.campers.databinding.RecyclerHomeHotcommunityBinding

class HotCommunityAdapter() :
    RecyclerView.Adapter<HotCommunityAdapter.HotCommunityViewHolder>() {

    private val list: ArrayList<HotCommunityList> = arrayListOf()

    interface OnItemClickListener {
        fun setOnItemClick(binding: RecyclerHomeHotcommunityBinding, data: HotCommunityList)
    }

    private var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    inner class HotCommunityViewHolder(private val binding: RecyclerHomeHotcommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotCommunityList: HotCommunityList) {
            binding.hotCommunityListItem = hotCommunityList

            if(adapterPosition != RecyclerView.NO_POSITION){
                binding.root.setOnClickListener {
                    clickListener?.setOnItemClick(binding, hotCommunityList)
                }
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

    fun setList(pList: ArrayList<HotCommunityList>) {
        list.addAll(pList)
    }
}