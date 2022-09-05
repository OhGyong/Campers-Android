package com.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.community.CommunityCommentData
import com.campers.databinding.ItemListCommunityCommentBinding

class CommunityCommentAdapter(private val list: ArrayList<CommunityCommentData>) :
    RecyclerView.Adapter<CommunityCommentAdapter.CommunityCommentViewHolder>() {

    inner class CommunityCommentViewHolder(private val binding: ItemListCommunityCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(communityCommentList: CommunityCommentData) {
            binding.communityCommentItem = communityCommentList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityCommentViewHolder {
        val binding = ItemListCommunityCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommunityCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityCommentViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}