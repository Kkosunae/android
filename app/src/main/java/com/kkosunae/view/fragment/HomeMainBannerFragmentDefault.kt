package com.kkosunae.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkosunae.R
import com.kkosunae.databinding.FragmentPointBinding
import com.kkosunae.databinding.ViewHomeMainStartBinding
import com.kkosunae.databinding.ViewHomeMainStartState1Binding

class HomeMainBannerFragmentDefault : Fragment(), View.OnClickListener {
    lateinit var binding: ViewHomeMainStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewHomeMainStartBinding.inflate(inflater)
        binding.tvHomeMainStart.setOnClickListener(this)

        return binding.root
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_home_main_start -> {
                initMainBanner(1)
            }
        }
    }
    private fun initMainBanner(state : Int) {
        if (state == 0) {
            parentFragmentManager.beginTransaction().replace(R.id.home_main_container, HomeMainBannerFragmentDefault()).commit()
        } else {
            parentFragmentManager.beginTransaction().replace(R.id.home_main_container, HomeMainBannerFragment()).commit()
        }
    }

}