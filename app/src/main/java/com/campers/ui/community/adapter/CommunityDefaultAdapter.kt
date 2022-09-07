package com.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.community.CommunityDefaultData
import com.campers.databinding.ItemListCommunityDefaultBinding

class CommunityDefaultAdapter : RecyclerView.Adapter<CommunityDefaultAdapter.CommunityDefaultViewHolder>() {
    private var list: ArrayList<CommunityDefaultData> = arrayListOf()

    inner class CommunityDefaultViewHolder(private val binding: ItemListCommunityDefaultBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityDefaultData, position: Int) {
            binding.glampingBoard.text = data.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityDefaultAdapter.CommunityDefaultViewHolder {
        val binding = ItemListCommunityDefaultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityDefaultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CommunityDefaultAdapter.CommunityDefaultViewHolder,
        position: Int
    ) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(pList: ArrayList<CommunityDefaultData>) {
        list = pList
        notifyDataSetChanged()
    }
}