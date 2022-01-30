package com.example.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.data.home.Ranking
import com.example.campers.databinding.RecyclerHomeRankingBinding

class RankingAdapter(private val list: ArrayList<Ranking>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(private val binding: RecyclerHomeRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: Ranking){
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