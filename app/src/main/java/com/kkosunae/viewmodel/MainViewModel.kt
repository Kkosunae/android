package com.kkosunae.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    private val _currentTab = MutableLiveData<Int>()
    private val _isLogin = MutableLiveData<Boolean>()
    private val _homeMainBannerState = MutableLiveData<Int>()

    val currentTab : LiveData<Int>
        get() = _currentTab
    val isLogin : LiveData<Boolean>
        get() = _isLogin
    val homeMainBannerState : LiveData<Int>
        get() = _homeMainBannerState

    init {
        // home 탭으로 초기화
        Log.d("MainViewModel", "init tab")
        _currentTab.postValue(1)
    }
    fun setCurrentTab(index : Int) {
        _currentTab.postValue(index)
    }

    fun setIsLogin(index : Boolean) {
        _isLogin.postValue(index)
    }

    init {
        Log.d("MainViewModel", "init state")
        _homeMainBannerState.postValue(0)
    }
    fun setHomeMainBannerState(index : Int) {
        _homeMainBannerState.postValue(index)
    }
}