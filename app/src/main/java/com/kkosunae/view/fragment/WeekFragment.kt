package com.kkosunae.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kkosunae.databinding.FragmentWeekBinding
import com.kkosunae.viewmodel.WalkStatisticViewModel

class WeekFragment : Fragment() {
    lateinit var binding: FragmentWeekBinding
    private val viewModel : WalkStatisticViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeekBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }
    private fun initObserver() {
        viewModel.statisticData.observe(viewLifecycleOwner) {
            binding.tvWalkCount.text = viewModel.getStatisticData().value?.weekly?.walkCount
            binding.tvAverageDuration.text = viewModel.getStatisticData().value?.weekly?.averageDuration.toString()
            binding.tvTotalDuration.text = viewModel.getStatisticData().value?.weekly?.totalDuration.toString()
            binding.tvMaxDuration.text = viewModel.getStatisticData().value?.weekly?.maxDuration.toString()
            binding.tvAverageDistance.text = viewModel.getStatisticData().value?.weekly?.averageDistance.toString()
            binding.tvTotalDistance.text = viewModel.getStatisticData().value?.weekly?.totalDistance.toString()
            binding.tvMaxDistance.text = viewModel.getStatisticData().value?.weekly?.maxDistance.toString()
        }
    }
}