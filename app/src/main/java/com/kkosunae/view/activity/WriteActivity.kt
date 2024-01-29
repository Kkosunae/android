package com.kkosunae.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.adapter.HomeNotiListAdapter
import com.kkosunae.databinding.ActivityWriteBinding
import com.kkosunae.model.HomeNotiItem

class WriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityWriteBinding
    private val TAG = "ActivityWriteBinding"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mapWriteToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_write_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.map_write_menu_confirm-> {
                Log.d(TAG, "click confirm")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}