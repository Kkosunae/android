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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.d("MainActivity", "keyHash : " +keyHash)
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
}