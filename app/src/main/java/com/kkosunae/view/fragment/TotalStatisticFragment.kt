package com.kkosunae.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.kkosunae.R
import com.kkosunae.databinding.FragmentTotalBinding
import com.kkosunae.viewmodel.WalkStatisticViewModel

class TotalStatisticFragment : Fragment() {
    lateinit var binding: FragmentTotalBinding
    private val viewModel : WalkStatisticViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTotalBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }
    private fun initObserver() {
        viewModel.statisticData.observe(viewLifecycleOwner) {
            binding.tvWalkCount.text = viewModel.getStatisticData().value?.total?.walkCount
            binding.tvAverageDuration.text = viewModel.getStatisticData().value?.total?.averageDuration.toString()
            binding.tvTotalDuration.text = viewModel.getStatisticData().value?.total?.totalDuration.toString()
            binding.tvMaxDuration.text = viewModel.getStatisticData().value?.total?.maxDuration.toString()
            binding.tvAverageDistance.text = viewModel.getStatisticData().value?.total?.averageDistance.toString()
            binding.tvTotalDistance.text = viewModel.getStatisticData().value?.total?.totalDistance.toString()
            binding.tvMaxDistance.text = viewModel.getStatisticData().value?.total?.maxDistance.toString()
        }
    }
}