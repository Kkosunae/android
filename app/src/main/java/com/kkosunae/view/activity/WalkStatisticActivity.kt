package com.kkosunae.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.kkosunae.R
import com.kkosunae.databinding.ActivityWalkStatisticBinding
import com.kkosunae.network.WalkApiRepository
import com.kkosunae.view.fragment.MonthlyFragment
import com.kkosunae.view.fragment.TotalStatisticFragment
import com.kkosunae.view.fragment.WeekFragment
import com.kkosunae.viewmodel.MainViewModel
import com.kkosunae.viewmodel.WalkStatisticViewModel

class WalkStatisticActivity : AppCompatActivity() {
    lateinit var binding: ActivityWalkStatisticBinding
    private val TAG = "WalkStatisticActivity"
    private val viewModel : WalkStatisticViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        binding = ActivityWalkStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        setSupportActionBar(binding.statisticToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WalkApiRepository.getWalkStatistics(viewModel)
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("전체"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("주"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("월"))
        supportFragmentManager.beginTransaction().replace(R.id.container, TotalStatisticFragment()).commit()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var position = tab?.position
                WalkApiRepository.getWalkStatistics(viewModel)
                if (position != null) {
                    if (position == 0) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, TotalStatisticFragment()).commit()
                    } else if (position == 1) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, WeekFragment()).commit()
                    } else if (position == 2) {
                        supportFragmentManager.beginTransaction().replace(R.id.container, MonthlyFragment()).commit()
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
    private fun initObserver() {
        viewModel.statisticData.observe(this) {

        }
    }
}