package com.kkosunae.viewmodel

import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkosunae.model.TokenItem

class MainViewModel : ViewModel(){
    private val _currentTab = MutableLiveData<Int>()
    private val _isLogin = MutableLiveData<Boolean>()
    private val _homeMainBannerState = MutableLiveData<Int>()
    private val _footCount = MutableLiveData<Int>()
    private val _currentToken = MutableLiveData<TokenItem>()

    val currentTab : LiveData<Int>
        get() = _currentTab
    val isLogin : LiveData<Boolean>
        get() = _isLogin
    val homeMainBannerState : LiveData<Int>
        get() = _homeMainBannerState
    val footCount : LiveData<Int>
        get() = _footCount
    val currentToken : LiveData<TokenItem>
        get() = _currentToken
    init {
        // home 탭으로 초기화
        Log.d("MainViewModel", "init tab")
        _currentTab.postValue(1)
        _homeMainBannerState.postValue(0)
        _footCount.value = 0
    }
    fun setCurrentTab(index : Int) {
        _currentTab.postValue(index)
    }
    fun setIsLogin(index : Boolean) {
        _isLogin.postValue(index)
    }
    fun setHomeMainBannerState(index : Int) {
        _homeMainBannerState.postValue(index)
    }
    fun upFootCount() {
        Log.d("MainViewModel", "upFootCount : " + _footCount.value)
        if(_footCount.value == 15) {
            _footCount.value = 0
        } else {
            _footCount.value = _footCount.value?.plus(1)
        }
    }
    fun downFootCount() {
        Log.d("MainViewModel", "downFootCount : " + _footCount.value)
        if(_footCount.value == 0) {

        } else {
            _footCount.value = _footCount.value?.minus(1)
        }
    }
    fun setCurrentToken(token:TokenItem) {
        _currentToken.postValue(token)
    }
}