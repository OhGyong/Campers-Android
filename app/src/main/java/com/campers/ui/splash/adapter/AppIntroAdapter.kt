package com.campers.ui.splash.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.campers.R
import com.campers.ui.login.LoginActivity

class AppIntroAdapter(val context: Context, imageList: ArrayList<Int>): RecyclerView.Adapter<AppIntroAdapter.PagerViewHolder>() {

    var item = imageList

    inner class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.imageView)
        init {
            itemView.findViewById<View>(R.id.v_skip_intro).setOnClickListener {
                val intent = Intent(itemView.context, LoginActivity::class.java)
                ContextCompat.startActivity(itemView.context, intent, null)
                (context as Activity).finish() // 화면 이동 후 액티비티 종료
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.app_intro_item, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.image.setImageResource(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }
}