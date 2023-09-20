package com.kkosunae.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kkosunae.R
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.databinding.ActivityMainBinding
import com.kkosunae.view.fragment.*
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


        initNaverMap();
        initBottomNavigation()

    }
    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_main, HomeFragment()).commit()
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navi_menu_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
                R.id.navi_menu_map -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MapFragment() ).commit()
                R.id.navi_menu_point -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, PointFragment() ).commit()
                R.id.navi_menu_community -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, CommunityFragment() ).commit()
                R.id.navi_menu_mypage -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MypageFragment() ).commit()


            }
            true
        }
    }
    private fun initKakaoLogin() {
        // 로그인 정보 확인 후 로그인 필요 시 login activity로 이동.
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
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
        val mapFragment = fm.findFragmentById(R.id.fragment_map) as MapFragment?
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