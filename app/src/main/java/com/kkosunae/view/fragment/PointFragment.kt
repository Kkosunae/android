package com.kkosunae.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkosunae.databinding.FragmentPointBinding

class PointFragment : Fragment() {
    lateinit var binding: FragmentPointBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointBinding.inflate(inflater)
        return binding.root
    }
}