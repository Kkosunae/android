package com.kkosunae.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.adapter.HomeNotiListAdapter
import com.kkosunae.databinding.ActivityWriteBinding
import com.kkosunae.model.FootData
import com.kkosunae.model.HomeNotiItem
import com.kkosunae.network.RetrofitManager
import com.kkosunae.view.fragment.MyMapFragment

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
                val content : String = binding.editText.text.toString()
                val intent = Intent()
                intent.putExtra("resultKey", content)
                setResult(Activity.RESULT_OK, intent)
                Log.d(TAG, "setResult$intent")
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}