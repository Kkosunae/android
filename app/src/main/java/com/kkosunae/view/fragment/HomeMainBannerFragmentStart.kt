package com.kkosunae.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.kkosunae.R
import com.kkosunae.databinding.FragmentHomeMainBannerStartBinding
import com.kkosunae.model.WalkEndData
import com.kkosunae.network.WalkApiRepository
import com.kkosunae.viewmodel.MainViewModel

class HomeMainBannerFragmentStart : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentHomeMainBannerStartBinding
    private val mainViewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainBannerStartBinding.inflate(inflater)
        initObserver()
        binding.homeMainStateStopButton.setOnClickListener(this)
        binding.homeMainStatePauseButton.setOnClickListener(this)
        binding.homeMainStateResumeButton.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.home_main_state_stop_button -> {
                var location = mainViewModel.getCurrentLocation()
                if (location != null) {
                    WalkApiRepository.postWalkEnd(WalkEndData(mainViewModel.getWalkId(),location.latitude, location.longitude))
                }
                mainViewModel.setHomeMainBannerState(0)
            }
            R.id.home_main_state_pause_button -> {
                mainViewModel.setHomeMainBannerState(2)

            }
            R.id.home_main_state_resume_button -> {
                mainViewModel.setHomeMainBannerState(1)

            }
        }
    }
    private fun initObserver() {
        mainViewModel.homeMainBannerState.observe(viewLifecycleOwner, Observer { it ->
            Log.d("state", "it : $it" )
            when (it) {
                0 -> {
                }
                1 -> {
                    binding.homeMainStatePauseButton.visibility = View.VISIBLE
                    binding.homeMainStateResumeButton.visibility = View.GONE
                    binding.homeMainStateStopButton.visibility = View.GONE
                }
                2 -> {
                    binding.homeMainStatePauseButton.visibility = View.GONE
                    binding.homeMainStateResumeButton.visibility = View.VISIBLE
                    binding.homeMainStateStopButton.visibility = View.VISIBLE
                }
            }
        })
    }
}