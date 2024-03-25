package com.kkosunae.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkosunae.model.WalkStatistic

class WalkStatisticViewModel : ViewModel(){
    private val _statisticData = MutableLiveData<WalkStatistic>()
    val statisticData: LiveData<WalkStatistic> = _statisticData

    fun getStatisticData(): MutableLiveData<WalkStatistic> {
        return _statisticData
    }
    fun setStatisticData(value : WalkStatistic) {
        _statisticData.value = value
        Log.d("WalkStatisticViewModel","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@${statisticData.value?.total}")
    }
}