package com.kkosunae.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkosunae.R
import com.kkosunae.model.HomeNotiItem

class HomeNotiListAdapter (val list : ArrayList<HomeNotiItem>) : RecyclerView.Adapter<HomeNotiListAdapter.HomeNotiViewHolder>() {
    private val itemList:ArrayList<HomeNotiItem> = list

    inner class HomeNotiViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val tvDate = itemView.findViewById<TextView>(R.id.item_date)
        val tvContent = itemView.findViewById<TextView>(R.id.item_contents)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeNotiListAdapter.HomeNotiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_noti, parent, false)
        return HomeNotiViewHolder(view)
    }



    override fun onBindViewHolder(holder: HomeNotiListAdapter.HomeNotiViewHolder, position: Int) {
        holder.tvDate.text = itemList[position].date
        holder.tvContent.text = itemList[position].content
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}