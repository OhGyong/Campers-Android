package com.example.campers.ui.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.campers.R
import com.example.campers.data.community.CommunityData

class CommunityAdapter(private val list: MutableList<CommunityData>): RecyclerView.Adapter<CommunityAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val userName: TextView = itemView.findViewById(R.id.community_user_name)
        private val communityTitle: TextView = itemView.findViewById(R.id.community_title)
        private val bornfireNum: TextView = itemView.findViewById(R.id.bornfireNum)
        private val commentNum: TextView = itemView.findViewById(R.id.commentNum)
        private val viewNum: TextView = itemView.findViewById(R.id.viewNum)


        fun bind(data: CommunityData, position: Int){
            println("확인 $data")
            userName.text = data.userName
            communityTitle.text = data.communityTitle
            bornfireNum.text = data.bornfireNum.toString()
            commentNum.text = data.commentNum.toString()
            viewNum.text = data.viewNum.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        println("zxcvxcz")
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.recycler_community, parent ,false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        println("bnvcxnxc")
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        println("확인 ")
        return list.count()
    }
}