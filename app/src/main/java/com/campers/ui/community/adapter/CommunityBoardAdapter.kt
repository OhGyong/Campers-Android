package com.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.community.CommunityBoardData
import com.campers.databinding.ItemListCommunityBoardBinding

class CommunityBoardAdapter : RecyclerView.Adapter<CommunityBoardAdapter.CommunityDefaultViewHolder>() {
    private var list: ArrayList<CommunityBoardData> = arrayListOf()
    private var clickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun setOnItemClick(binding: ItemListCommunityBoardBinding, data: CommunityBoardData)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    inner class CommunityDefaultViewHolder(private val binding: ItemListCommunityBoardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityBoardData, position: Int) {
            binding.boardItem = data

            if(adapterPosition != RecyclerView.NO_POSITION){
                binding.root.setOnClickListener {
                    clickListener?.setOnItemClick(binding, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityBoardAdapter.CommunityDefaultViewHolder {
        val binding = ItemListCommunityBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityDefaultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CommunityBoardAdapter.CommunityDefaultViewHolder,
        position: Int
    ) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(pList: ArrayList<CommunityBoardData>) {
        list = pList
        notifyDataSetChanged()
    }
}