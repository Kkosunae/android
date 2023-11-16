package com.kkosunae.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.kkosunae.R
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.databinding.ActivityMainBinding
import com.kkosunae.view.fragment.*
import com.kkosunae.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    val TAG : String = "MainActivity"
    lateinit var binding : ActivityMainBinding
    val mainViewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
        initObserver()
        initBottomNavigation()

    }
    private fun initObserver() {
        mainViewModel.getCurrentTab().observe(this, Observer {it ->
            Log.d("MainActivity", "observe it : " + it)
            when (it) {
                1 -> {
                    binding.mainToolbar.setTitle("")
                    visibleToolbarIcon(true)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
                }
                2 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_map)
                    visibleToolbarIcon(false)
                    Log.d("MainActivity", "clear()")
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MyMapFragment() ).commit()

                }
                3 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_point)
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, PointFragment() ).commit()
                }
                4 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_community)
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, CommunityFragment() ).commit()
                }
                5 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_mypage)
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MypageFragment() ).commit()
                }
            }
        })
    }
    private fun initBottomNavigation() {
        Log.d("MainActivity", "initBottomNavigation")
        binding.bnvMain.itemIconTintList = null
//        supportFragmentManager.beginTransaction().add(R.id.fragment_container_main, HomeFragment()).commit()
        binding.bnvMain.setOnItemSelectedListener { item ->
            Log.d("MainActivity", "initBottomNavigation item : " + item)
            when (item.itemId) {
//                R.id.navi_menu_home -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
//                R.id.navi_menu_map -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MyMapFragment() ).commit()
//                R.id.navi_menu_point -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, PointFragment() ).commit()
//                R.id.navi_menu_community -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, CommunityFragment() ).commit()
//                R.id.navi_menu_mypage -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MypageFragment() ).commit()
                R.id.navi_menu_home -> mainViewModel.setCurrentTab(1)
                R.id.navi_menu_map -> mainViewModel.setCurrentTab(2)
                R.id.navi_menu_point -> mainViewModel.setCurrentTab(3)
                R.id.navi_menu_community -> mainViewModel.setCurrentTab(4)
                R.id.navi_menu_mypage -> mainViewModel.setCurrentTab(5)
            }
            true
        }
    }
    fun visibleToolbarIcon(boolean: Boolean) {
        if (boolean) {
            binding.mainToolbarLogo1.visibility = View.VISIBLE
            binding.mainToolbarLogo2.visibility = View.VISIBLE
        } else {
            binding.mainToolbarLogo1.visibility = View.GONE
            binding.mainToolbarLogo2.visibility = View.GONE
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
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        Log.d("MainActivity", "inflate()")
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_search -> Log.d(TAG, "menu_search click!")
//            R.id.menu_setting -> Log.d(TAG, "menu_setting click!")
//            else -> null
//        }
//        return super.onOptionsItemSelected(item)
//    }
}