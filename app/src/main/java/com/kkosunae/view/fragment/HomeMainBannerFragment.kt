package com.kkosunae.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkosunae.databinding.FragmentPointBinding
import com.kkosunae.databinding.ViewHomeMainStartState1Binding

class HomeMainBannerFragment : Fragment() {
    lateinit var binding: ViewHomeMainStartState1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewHomeMainStartState1Binding.inflate(inflater)
        return binding.root
    }
}