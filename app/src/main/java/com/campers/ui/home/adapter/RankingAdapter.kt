package com.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.home.RankingList
import com.campers.databinding.RecyclerHomeRankingBinding

class RankingAdapter(private val list: ArrayList<RankingList>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(private val binding: RecyclerHomeRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: RankingList){
            binding.rankingItem = ranking
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = RecyclerHomeRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}