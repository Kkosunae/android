package com.kkosunae

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kkosunae.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.databinding.ActivityMainBinding
import com.kkosunae.databinding.KakaologinfragmentBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    var TAG:String = "MainActivity"
    lateinit var binding: ActivityMainBinding
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.d("MainActivity", "keyHash : " +keyHash)
        binding.loginButtonMain.setOnClickListener { item ->
            initKakaoLogin()
        }

        initNaverMap();
        initBottomNavigation()

    }
    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_main, KakaoLoginFragment()).commit()
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, KakaoLoginFragment()).commit()
                R.id.second -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, NaverMapFragment() ).commit()


            }
            true
        }
    }
    private fun initKakaoLogin() {
        // 로그인 정보 확인 후 로그인 필요 시 login activity로 이동.
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, KakaoLoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun initNaverMap() {
        val fm = supportFragmentManager
        //MapFragment 객체 얻기
        val mapFragment = fm.findFragmentById(R.id.naver_map) as MapFragment?
            ?: MapFragment.newInstance()
        //NaverMap 객체가 준비되면 OnMapReady() 콜백 메서드 호출
        mapFragment.getMapAsync(this)

        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onMapReady(naverMap: NaverMap) {
        TODO("Not yet implemented")
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.uiSettings.isLocationButtonEnabled = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
       if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
           if (!locationSource.isActivated) { // 권한 거부됨
               Log.d(TAG, "onRequestPermissionsResult 권한 거부됨")
               naverMap.locationTrackingMode = LocationTrackingMode.None
           } else {
               Log.d(TAG, "onRequestPermissionsResult 권한 승인됨")
               naverMap.locationTrackingMode = LocationTrackingMode.Follow
           }
           return
       }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}