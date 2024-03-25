package com.kkosunae.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.kkosunae.R
import com.kkosunae.databinding.FragmentMonthlyBinding
import com.kkosunae.databinding.FragmentWeekBinding
import com.kkosunae.view.fragment.placeholder.PlaceholderContent
import com.kkosunae.viewmodel.WalkStatisticViewModel

/**
 * A fragment representing a list of Items.
 */
class MonthlyFragment : Fragment() {
    lateinit var binding: FragmentMonthlyBinding
    private val viewModel : WalkStatisticViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMonthlyBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }
    private fun initObserver() {
        viewModel.statisticData.observe(viewLifecycleOwner) {
            binding.tvWalkCount.text = viewModel.getStatisticData().value?.monthly?.walkCount
            binding.tvAverageDuration.text = viewModel.getStatisticData().value?.monthly?.averageDuration.toString()
            binding.tvTotalDuration.text = viewModel.getStatisticData().value?.monthly?.totalDuration.toString()
            binding.tvMaxDuration.text = viewModel.getStatisticData().value?.monthly?.maxDuration.toString()
            binding.tvAverageDistance.text = viewModel.getStatisticData().value?.monthly?.averageDistance.toString()
            binding.tvTotalDistance.text = viewModel.getStatisticData().value?.monthly?.totalDistance.toString()
            binding.tvMaxDistance.text = viewModel.getStatisticData().value?.monthly?.maxDistance.toString()
        }
    }
}