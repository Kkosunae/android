package com.kkosunae.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkosunae.R
import com.kkosunae.model.HomeItem

class HomeListAdapter (val itemList : ArrayList<HomeItem>) : RecyclerView.Adapter<HomeListAdapter.HomeViewHolder>(){

    inner class HomeViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle = itemView.findViewById<TextView>(R.id.item_tv_title)
        var tvContent = itemView.findViewById<TextView>(R.id.item_tv_content)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return HomeViewHolder(view);
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.tvTitle.text = itemList[position].title
        holder.tvContent.text = itemList[position].content
    }
}