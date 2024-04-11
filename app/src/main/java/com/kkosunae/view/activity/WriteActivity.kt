package com.kkosunae.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.adapter.HomeNotiListAdapter
import com.kkosunae.databinding.ActivityWriteBinding
import com.kkosunae.model.FootData
import com.kkosunae.model.HomeNotiItem
import com.kkosunae.network.RetrofitManager
import com.kkosunae.view.fragment.MyMapFragment
import com.kkosunae.viewmodel.MainViewModel
import com.naver.maps.geometry.LatLng

class WriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityWriteBinding
    private val TAG = "ActivityWriteBinding"
    private var mLatitude = 0.0
    private var mLongitude = 0.0
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
                Log.d(TAG, "position_lat : $mLatitude position_long : $mLongitude")
                intent.putExtra("resultKey", content)
                intent.putExtra("position_lat", mLatitude)
                intent.putExtra("position_long", mLongitude)
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