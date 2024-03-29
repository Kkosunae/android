package com.kkosunae.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kkosunae.R
import com.kkosunae.databinding.FragmentHomeMainBannerDefaultBinding
import com.kkosunae.model.WalkStartData
import com.kkosunae.network.WalkApiRepository
import com.kkosunae.viewmodel.MainViewModel

class HomeMainBannerFragmentDefault : Fragment(), View.OnClickListener {
    private val TAG = "HomeMainBannerDefault"
    lateinit var binding: FragmentHomeMainBannerDefaultBinding
    private val mainViewModel : MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainBannerDefaultBinding.inflate(inflater)
        binding.tvHomeMainStart.setOnClickListener(this)
        binding.ivHomeMainStart.setOnClickListener(this)
        return binding.root
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_home_main_start,
            R.id.tv_home_main_start -> {
                Log.d(TAG, "onClick")

                val locationItem = mainViewModel.getCurrentLocation()
                if (locationItem != null) {
                    WalkApiRepository.postWalkStart(WalkStartData(locationItem.latitude, locationItem.longitude),mainViewModel)
                    mainViewModel.setHomeMainBannerState(1)
                }

            }
        }
    }
}