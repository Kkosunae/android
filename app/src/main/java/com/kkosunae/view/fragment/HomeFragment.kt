package com.kkosunae.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.adapter.HomeHotPlaceListAdapter
import com.kkosunae.adapter.HomeListAdapter
import com.kkosunae.adapter.HomeTipsListAdapter
import com.kkosunae.databinding.FragmentHomeBinding
import com.kkosunae.viewmodel.MainViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.kkosunae.model.*
import com.kkosunae.network.WalkApiRepository
import com.kkosunae.network.WalkApiRepository.getWalkStatistics
import com.kkosunae.view.activity.NotificationActivity
import com.kkosunae.view.activity.WalkStatisticActivity

class HomeFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentHomeBinding
    lateinit var mContext : Context
    private val TAG = "HomeFragment"
    private val mainViewModel : MainViewModel by activityViewModels()
    private var mFootCount : Int = 0
    private var isRunning = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"onCreateView")
        binding = FragmentHomeBinding.inflate(inflater)
        initObserver()
        // menu item 설정
        initMenuItem()
        // pirchart
        initPieChart()
        WalkApiRepository.getWalkStatus(mainViewModel)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        getWalkStatistics()
        binding.homeWalkNextIv.setOnClickListener(this)
        binding.ivHomeMainStart.setOnClickListener(this)
        binding.tvHomeMainStart.setOnClickListener(this)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    private fun initMenuItem() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    //Todo 클릭 시 알람 내역 API 호출
                    // 확인한 알람, 미확인 알람 분리, 확인하지 않은 알람이 있다면 뱃지 표시
                    // 지금까지 받은 알람 표시하는 화면으로 이동 (R->L)
                    R.id.home_menu_alam -> {
                        Log.d(TAG, "home_menu_alam click!")
                        var intent = Intent(context, NotificationActivity::class.java)
                        startActivity(intent)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
    private fun initRecyclerView() {
        var itemList = ArrayList<HomeItem>()
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))

        val recyclerView = binding.homeRv
        val homeListAdapter = HomeListAdapter(itemList)
        recyclerView.adapter = homeListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // HomeHotPlaceItem
        var itemHotplace = ArrayList<HomeHotPlaceItem>()
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title1", 300))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title2", 200))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title3", 1000))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title4", 10))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title5", 0))

        val rvHotPlace = binding.rvHomeHotplace
        val hotPlaceAdapter = HomeHotPlaceListAdapter(itemHotplace)
        rvHotPlace.adapter = hotPlaceAdapter
        rvHotPlace.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // HomeTipsItem
        var itemTips = ArrayList<HomeTipsItem>()
        itemTips.add(HomeTipsItem(R.drawable.sample_img_dog, "category", "title1", "111"))
        itemTips.add(HomeTipsItem(R.drawable.sample_img_dog, "category", "title2", "222"))
        itemTips.add(HomeTipsItem(R.drawable.sample_img_dog, "category", "title3", "333"))
        itemTips.add(HomeTipsItem(R.drawable.sample_img_dog, "category", "title4", "444"))
        itemTips.add(HomeTipsItem(R.drawable.sample_img_dog, "category", "title5", "555"))

        val rvTips = binding.rvHomeTips
        val homeTipsAdapter = HomeTipsListAdapter(itemTips)
        rvTips.adapter = homeTipsAdapter
        rvTips.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }
    private fun initPieChart() {
        val pieChart = binding.homeWalkPiechart
        var value : Float = if (mFootCount < 15) {
            (mFootCount * 7).toFloat()
        } else {
            100f
        }
        var value2 :Float = 100f - value
        val entries = arrayListOf(
            PieEntry(value, 0),
            PieEntry(value2, 1)
        )
        val dataSet = PieDataSet(entries,"")
        val colors = arrayListOf(
            ContextCompat.getColor(mContext, R.color.colorVisVis),
            ContextCompat.getColor(mContext, R.color.colorWhiteSmoke))
        dataSet.colors = colors
        dataSet.setDrawValues(false)
        val data = PieData(dataSet)
        pieChart.data = data
        //하단 설명 비활성화
        pieChart.legend.isEnabled = false
        pieChart.holeRadius= 80f
        pieChart.isDrawHoleEnabled = true
        pieChart.description = null
        pieChart.setTouchEnabled(false)
        pieChart.invalidate()
        binding.homeWalkDistanceTv.setOnClickListener(this)
        binding.homeWalkNum.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.home_walk_num -> {
                mainViewModel.upFootCount()
            }
            R.id.home_walk_distance_tv -> {
                mainViewModel.downFootCount()
            }
            R.id.home_walk_next_iv -> {
                var intent = Intent(context, WalkStatisticActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_home_main_start,
            R.id.tv_home_main_start -> {
                Log.d(TAG, "onClick")
                var locationItem = mainViewModel.getCurrentLocation()
                if (!isRunning) {
                    Log.d(TAG, "!isRunning $locationItem")
                    if (locationItem != null) {
                        WalkApiRepository.postWalkStart(WalkStartData(locationItem.latitude, locationItem.longitude),mainViewModel)
                        mainViewModel.setHomeMainBannerState(0)
                    }
                } else {
                    Log.d(TAG, "isRunning $locationItem")
                    if (locationItem != null) {
                        WalkApiRepository.postWalkEnd(WalkEndData(mainViewModel.getWalkId(),locationItem.latitude, locationItem.longitude, 1000))
                        mainViewModel.setHomeMainBannerState(1)
                    }
                }
            }
        }
    }
    private fun initObserver() {
        mainViewModel.homeMainBannerState.observe(viewLifecycleOwner, Observer { it ->
            Log.d("HomeFragment", "homeMainBannerState observer it : $it")
            when (it) {
                0 -> {
                    // 산책 중
                    binding.ivHomeMainStart.text = "정지"
                    isRunning = true
                    binding.tvHomeMainCo.visibility = View.GONE
                    binding.tvHomeMainStart.visibility = View.GONE
                    binding.tvHomeMainDistance.visibility = View.VISIBLE
                    binding.tvHomeMainMinute.visibility = View.VISIBLE
                    binding.tvHomeMainHour.visibility = View.VISIBLE
                    binding.tvHomeMainSec.visibility = View.VISIBLE
                //                    childFragmentManager.beginTransaction().replace(R.id.home_main_container, HomeMainBannerFragmentDefault()).commit()
                }
                1 -> {
                    // 산책 시작 전
                    binding.ivHomeMainStart.text = "시작"
                    isRunning = false
                    binding.tvHomeMainCo.visibility = View.VISIBLE
                    binding.tvHomeMainStart.visibility = View.VISIBLE
                    binding.tvHomeMainDistance.visibility = View.GONE
                    binding.tvHomeMainMinute.visibility = View.GONE
                    binding.tvHomeMainHour.visibility = View.GONE
                    binding.tvHomeMainSec.visibility = View.GONE
//                    childFragmentManager.beginTransaction().replace(R.id.home_main_container, HomeMainBannerFragmentStart()).commit()
                }
            }
        })
        mainViewModel.footCount.observe(viewLifecycleOwner, Observer { it ->
            Log.d("HomeFragment", "footCount observer it : $it")
            mFootCount = it
            Log.d("HomeFragment", "footCount observer mFootCount : $mFootCount")
            binding.homeWalkNum.text = it.toString()
            initPieChart()
        })
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop()")
        var locationItem = mainViewModel.getCurrentLocation()
        if (locationItem != null)
            WalkApiRepository.postWalkEnd(WalkEndData(mainViewModel.getWalkId(),locationItem.latitude, locationItem.longitude, 1000))
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy()")
        super.onDestroy()

    }
}