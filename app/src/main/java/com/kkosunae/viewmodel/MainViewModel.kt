package com.kkosunae.viewmodel

import android.media.session.MediaSession.Token
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kkosunae.model.LocationItem
import com.kkosunae.model.TokenItem
import com.kkosunae.model.WalkStartData
import com.kkosunae.network.WalkApiRepository
import java.time.LocalDateTime

class MainViewModel : ViewModel(){
    private val _currentTab = MutableLiveData<Int>()
    private val _isLogin = MutableLiveData<Boolean>()
    private val _homeMainBannerState = MutableLiveData<Int>()
    private val _footCount = MutableLiveData<Int>()
    private val _currentToken = MutableLiveData<TokenItem>()
    private val _currentLocation = MutableLiveData<LocationItem>()
    private var mWalkId = 0
    @RequiresApi(Build.VERSION_CODES.O)
    private var mStartTime : LocalDateTime = LocalDateTime.now()
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
    val currentLocation : LiveData<LocationItem>
        get() = _currentLocation

    init {
        // home 탭으로 초기화
        Log.d("MainViewModel", "init tab")
        _currentTab.postValue(1)
//        _homeMainBannerState.postValue(0)

        _footCount.value = 0
    }
    fun setCurrentTab(index : Int) {
        _currentTab.postValue(index)
    }
    fun setIsLogin(index : Boolean) {
        _isLogin.postValue(index)
    }
    fun setHomeMainBannerState(index : Int) {
        Log.d("MainViewModel", "setHomeMainBannerState $index: ")
        _homeMainBannerState.postValue(index)
    }
    fun setFootCount(value : Int) {
        _footCount.value = value
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
    fun setCurrentLocation(location : LocationItem) {
        _currentLocation.postValue(location)
    }
    fun getCurrentLocation() : LocationItem? {
        Log.d("MainViewModel", "${_currentLocation.value}")
        return _currentLocation.value
    }
    fun setWalkId(value :Int?) {
        Log.d("MainViewModel", "setWalkId $value: ")
        if (value != null)
            mWalkId = value
    }
    fun getWalkId() : Int {
        return mWalkId
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setStartTime(value :LocalDateTime?) {
        Log.d("MainViewModel", "setStartTime $value: ")
        if (value != null)
            mStartTime = value
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartTime() : LocalDateTime {
        return mStartTime
    }
}