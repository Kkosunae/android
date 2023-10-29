package com.kkosunae.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel(){
    private var currentTab = MutableLiveData<Int>()
    private var isLogin = MutableLiveData<Boolean>()

    fun getCurrentTab() : MutableLiveData<Int> {
        if(currentTab == null) {
            currentTab = MutableLiveData<Int>()
        }
        return currentTab
    }
    init {
        // home 탭으로 초기화
        getCurrentTab().postValue(1)
    }
    fun setCurrentTab(index : Int) {
        getCurrentTab().postValue(index)
    }

    fun getIsLogin() :MutableLiveData<Boolean> {
        if(isLogin == null) {
            isLogin = MutableLiveData<Boolean>(false)
        }
        return isLogin
    }
    fun setIsLogin(index : Boolean) {
        getIsLogin().postValue(index)
    }
}