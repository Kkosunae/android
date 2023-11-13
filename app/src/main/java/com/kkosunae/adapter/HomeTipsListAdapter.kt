package com.kkosunae.adapter

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkosunae.R
import com.kkosunae.databinding.ItemHomeHotplaceBinding
import com.kkosunae.databinding.ItemHomeTipsBinding
import com.kkosunae.model.HomeHotPlaceItem
import com.kkosunae.model.HomeItem
import com.kkosunae.model.HomeTipsItem

class HomeTipsListAdapter (private val list : ArrayList<HomeTipsItem>) : RecyclerView.Adapter<HomeTipsListAdapter.HomeViewHolder>(){
    private val itemList: ArrayList<HomeTipsItem> = list

    inner class HomeViewHolder(private val binding: ItemHomeTipsBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeTipsItem) {
            //sampleImage 고정
            binding.itemHomeTipsIv.setImageResource(R.drawable.sample_img_dog)
            binding.itemHomeTipsTvCategory.text = data.category
            binding.itemHomeTipsTvTitle.text = data.title
            binding.itemHomeTipsTvComment.text = data.comment

            binding.itemHomeTipsLayout.setOnClickListener {
                Log.d("HomeHotPlaceListAdapter", "click layout")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =  ItemHomeTipsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun addData(image : Int, category: String, title : String, comment : String) {
        itemList.add(HomeTipsItem(image, category,title,comment))
        notifyItemInserted(itemList.size)
    }
}