package com.campers.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.campers.data.home.RankingListData
import com.campers.databinding.ItemListHomeRankingBinding

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {
    private var list:ArrayList<RankingListData> = arrayListOf()

    inner class RankingViewHolder(private val binding: ItemListHomeRankingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ranking: RankingListData, position: Int){
            binding.rankingItem = ranking

            if(list.lastIndex == position){
                binding.vItemHomeRanking.visibility = View.INVISIBLE
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

    fun setData(pList: ArrayList<RankingListData>) {
        list = pList
        notifyDataSetChanged()
    }
}