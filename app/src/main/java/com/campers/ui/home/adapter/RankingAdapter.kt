package com.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.home.RankingList
import com.campers.databinding.ItemListHomeRankingBinding

class RankingAdapter(private val list: ArrayList<RankingList>) : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    inner class RankingViewHolder(private val binding: ItemListHomeRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: RankingList, position: Int){
            binding.rankingItem = ranking

            if(list.lastIndex == position){
                binding.vItemHomeRanking.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val binding = ItemListHomeRankingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.count()
    }
}