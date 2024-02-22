package com.kkosunae.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kkosunae.R
import com.kkosunae.databinding.ActivityWalkStatisticBinding

class WalkStatisticActivity : AppCompatActivity() {
    lateinit var binding: ActivityWalkStatisticBinding
    private val TAG = "WalkStatisticActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityWalkStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.statisticToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}