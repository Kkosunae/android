package com.kkosunae.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.kkosunae.R
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.databinding.ActivityMainBinding
import com.kkosunae.view.fragment.*

class MainActivity : AppCompatActivity() {
    val TAG : String = "MainActivity"
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.d("MainActivity", "keyHash : " +keyHash)


        initBottomNavigation()

    }
    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_main, HomeFragment()).commit()
        binding.bnvMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navi_menu_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
                R.id.navi_menu_map -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MyMapFragment() ).commit()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> Log.d(TAG, "menu_search click!")
            R.id.menu_setting -> Log.d(TAG, "menu_setting click!")
            else -> null
        }
        return super.onOptionsItemSelected(item)
    }

}