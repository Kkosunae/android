package com.kkosunae.view.activity

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.kkosunae.R
import com.kakao.sdk.user.UserApiClient
import com.kkosunae.databinding.ActivityMainBinding
import com.kkosunae.view.fragment.*
import com.kkosunae.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val TAG : String = "MainActivity"
    lateinit var binding : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFullScreen()
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding.mainLayout.setPadding(0, getStatusBarHeight(this), 0, 0)
        setSupportActionBar(binding.mainToolbar)
        initObserver()
        initBottomNavigation()
    }
    fun getStatusBarHeight(context : Context) : Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")

        return if (resourceId > 0) {
            context.resources.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }
    private fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(
//                WindowInsets.Type.statusBars() or
                        WindowInsets.Type.navigationBars()
            )
            window.insetsController?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }
    private fun initObserver() {
        mainViewModel.currentTab.observe(this, Observer {it ->
            Log.d("MainActivity", "observe it : $it")
            binding.mainLayout.setPadding(0, getStatusBarHeight(this), 0, 0)
            when (it) {
                1 -> {
                    binding.mainToolbar.title = ""
                    supportActionBar?.show()
                    visibleToolbarIcon(true)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, HomeFragment()).commit()
                }
                2 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_map)
                    supportActionBar?.hide()
                    visibleToolbarIcon(false)
                    binding.mainLayout.setPadding(0, 0, 0, 0)
                    Log.d("MainActivity", "clear()")
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MyMapFragment() ).commit()

                }
                3 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_point)
                    supportActionBar?.show()
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, PointFragment() ).commit()
                }
                4 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_community)
                    supportActionBar?.show()
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, CommunityFragment() ).commit()
                }
                5 -> {
                    binding.mainToolbar.setTitle(R.string.toolbar_menu_mypage)
                    supportActionBar?.show()
                    visibleToolbarIcon(false)
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_main, MypageFragment() ).commit()
                }
            }
        })
    }
    private fun initBottomNavigation() {
        Log.d("MainActivity", "initBottomNavigation")
        binding.bnvMain.itemIconTintList = null
        binding.bnvMain.backgroundTintList =resources.getColorStateList(R.color.colorWhite,theme)
//            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorWhite))
        binding.bnvMain.setOnItemSelectedListener { item ->
            Log.d("MainActivity", "initBottomNavigation item : $item")
            when (item.itemId) {
                R.id.navi_menu_home -> mainViewModel.setCurrentTab(1)
                R.id.navi_menu_map -> mainViewModel.setCurrentTab(2)
                R.id.navi_menu_point -> mainViewModel.setCurrentTab(3)
                R.id.navi_menu_community -> mainViewModel.setCurrentTab(4)
                R.id.navi_menu_mypage -> mainViewModel.setCurrentTab(5)
            }
            true
        }
    }
    private fun visibleToolbarIcon(boolean: Boolean) {
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