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
import com.kkosunae.model.HomeHotPlaceItem
import com.kkosunae.model.HomeItem

class HomeHotPlaceListAdapter (private val list : ArrayList<HomeHotPlaceItem>) : RecyclerView.Adapter<HomeHotPlaceListAdapter.HomeViewHolder>(){
    private val itemList: ArrayList<HomeHotPlaceItem> = list

    inner class HomeViewHolder(private val binding: ItemHomeHotplaceBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeHotPlaceItem) {
            //sampleImage 고정
            binding.itemHomeHotplaceIv.setImageResource(R.drawable.sample_img_dog)
            binding.itemHomeTvCategory.text = data.category
            binding.itemHomeTvTitle.text = data.title
            binding.itemHomeHotplaceLayoutStarTvValue.text = data.star
            binding.itemHomeHotplaceLayoutStarTvComment.text = data.comment

            binding.itemHomeHotplaceLayout.setOnClickListener {
                Log.d("HomeHotPlaceListAdapter", "click layout")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding =  ItemHomeHotplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding);
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun addData(image: Int, category: String, title : String, star : String, comment : String) {
        itemList.add(HomeHotPlaceItem(image,category,title,star,comment))
        notifyItemInserted(itemList.size)
    }
}