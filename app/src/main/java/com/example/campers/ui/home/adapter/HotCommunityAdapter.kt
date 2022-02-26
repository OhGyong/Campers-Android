package com.example.campers.ui.home.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.home.HotCommunityList
import com.example.campers.databinding.RecyclerHomeHotcommunityBinding
import com.example.campers.repository.home.HomeRepository
import com.google.gson.JsonArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HotCommunityAdapter(private val list: ArrayList<HotCommunityList>) :
    RecyclerView.Adapter<HotCommunityAdapter.HotCommunityViewHolder>() {

    inner class HotCommunityViewHolder(private val binding: RecyclerHomeHotcommunityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotCommunityList: HotCommunityList) {
            binding.hotCommunityListItem = hotCommunityList
            binding.root.setOnClickListener {
                runBlocking {
                    var data: JsonArray? = null
                    GlobalScope.launch {
                        try {
                            data = HomeRepository().getHotCommunityDetailData(hotCommunityList).payload
                        } catch (err: Error) {
                            println("getHotCommunityDetailData 호출 실패")
                        }
                    }.join()
                    binding.root.findNavController().navigate(R.id.communityDetailFragment, Bundle().apply {
                        putString("content", data?.get(0).toString())
                        putString("comment", data?.get(1).toString())
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotCommunityViewHolder {
        val binding = RecyclerHomeHotcommunityBinding.inflate(
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
}