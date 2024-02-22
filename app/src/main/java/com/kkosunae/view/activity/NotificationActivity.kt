package com.kkosunae.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.adapter.HomeNotiListAdapter
import com.kkosunae.databinding.ActivityNotificationBinding
import com.kkosunae.model.HomeNotiItem

class NotificationActivity : AppCompatActivity() {
    lateinit var binding: ActivityNotificationBinding
    private val TAG = "NotificationActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.homeAlamToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initRecyclerView()
    }
    private fun initRecyclerView() {
        var itemList = ArrayList<HomeNotiItem>()
        itemList.add(HomeNotiItem("내용1","12월 4일" ))
        itemList.add(HomeNotiItem("내용2","12월 4일" ))
        itemList.add(HomeNotiItem("내용3","12월 4일" ))
        itemList.add(HomeNotiItem("내용4","12월 4일" ))
        itemList.add(HomeNotiItem("내용5","12월 4일" ))

        val recyclerView = binding.homeAlamRv
        val homeNotiListAdapter = HomeNotiListAdapter(itemList)
        recyclerView.adapter = homeNotiListAdapter
        recyclerView.layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_noti_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
        R.id.home_noti_menu_delete-> {
                Log.d(TAG, "click delete")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}