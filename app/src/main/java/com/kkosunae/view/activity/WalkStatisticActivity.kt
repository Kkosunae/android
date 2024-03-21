package com.kkosunae.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.kkosunae.R
import com.kkosunae.databinding.ActivityWalkStatisticBinding
import com.kkosunae.view.fragment.DayFragment
import com.kkosunae.view.fragment.HomeFragment
import com.kkosunae.view.fragment.WeekFragment

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

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("일"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("주"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("월"))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                if (position != null) {
                    if (position == 0) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, DayFragment()).commit()
                    } else if (position == 1) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, WeekFragment()).commit()
                    } else if (position == 2) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, DayFragment()).commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}