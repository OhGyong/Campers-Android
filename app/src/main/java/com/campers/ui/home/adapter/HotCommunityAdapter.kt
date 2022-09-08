package com.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.home.HotCommunityListData
import com.campers.databinding.ItemListHomeHotcommunityBinding

class HotCommunityAdapter() :
    RecyclerView.Adapter<HotCommunityAdapter.HotCommunityViewHolder>() {

    private val list: ArrayList<HotCommunityListData> = arrayListOf()

    interface OnItemClickListener {
        fun setOnItemClick(binding: ItemListHomeHotcommunityBinding, data: HotCommunityListData)
    }

    private var clickListener: OnItemClickListener? = null

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    inner class HotCommunityViewHolder(private val binding: ItemListHomeHotcommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotCommunityList: HotCommunityListData) {
            binding.hotCommunityListItem = hotCommunityList

            if(adapterPosition != RecyclerView.NO_POSITION){
                binding.root.setOnClickListener {
                    clickListener?.setOnItemClick(binding, hotCommunityList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCommunityViewHolder {
        val binding = ItemListHomeHotcommunityBinding.inflate(
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

    fun setList(pList: ArrayList<HotCommunityListData>) {
        list.addAll(pList)
        notifyDataSetChanged()
    }
}