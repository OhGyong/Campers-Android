package com.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.community.CommunityCommentList
import com.campers.databinding.RecyclerCommunityCommentBinding

class CommunityCommentAdapter(private val list: ArrayList<CommunityCommentList>) :
    RecyclerView.Adapter<CommunityCommentAdapter.CommunityCommentViewHolder>() {

    inner class CommunityCommentViewHolder(private val binding: RecyclerCommunityCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(communityCommentList: CommunityCommentList) {
            binding.communityCommentListItem = communityCommentList
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityCommentViewHolder {
        val binding = RecyclerCommunityCommentBinding.inflate(
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