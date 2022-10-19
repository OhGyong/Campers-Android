package com.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.community.CommunityListData
import com.campers.databinding.ItemListCommunityBinding

class CommunityListAdapter: RecyclerView.Adapter<CommunityListAdapter.CommunityListViewHolder>() {
    private var list: ArrayList<CommunityListData> = arrayListOf()
    private var clickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun setOnItemClick(binding: ItemListCommunityBinding, data: CommunityListData)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    inner class CommunityListViewHolder(private val binding: ItemListCommunityBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: CommunityListData, position: Int){
            binding.communityTitle.text = data.title
            binding.communityUserName.text = data.nickName
            binding.viewNum.text = data.viewCount.toString()
            binding.bornfireNum.text = data.fireCount.toString()
            binding.tvCommunityListDate.text = data.date.trim('"')
            binding.tvCommentNum.text = "0"

            if(adapterPosition != RecyclerView.NO_POSITION){
                binding.root.setOnClickListener {
                    clickListener?.setOnItemClick(binding, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityListViewHolder {
        val binding = ItemListCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommunityListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityListViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    fun setData(pList: ArrayList<CommunityListData>) {
        list = pList
        notifyDataSetChanged()
    }
}