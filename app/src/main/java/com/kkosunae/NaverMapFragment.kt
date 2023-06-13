package com.kkosunae

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kkosunae.databinding.KakaologinfragmentBinding
import com.kkosunae.databinding.NavermapfragmemtBinding

class NaverMapFragment : Fragment(){
    lateinit var binding: NavermapfragmemtBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NavermapfragmemtBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}