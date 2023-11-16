package com.kkosunae.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kkosunae.R
import com.kkosunae.Utils
import com.kkosunae.adapter.HomeHotPlaceListAdapter
import com.kkosunae.adapter.HomeListAdapter
import com.kkosunae.adapter.HomeTipsListAdapter
import com.kkosunae.databinding.FragmentHomeBinding
import com.kkosunae.model.HomeHotPlaceItem
import com.kkosunae.model.HomeItem
import com.kkosunae.model.HomeTipsItem

class HomeFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentHomeBinding
    private val TAG = "HomeFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        // menu item 설정
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.tvHomeMainStart.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title1", "5.5", "111"))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title2", "8.5", "222"))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title3", "10.0", "333"))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title4", "0.0", "444"))
        itemHotplace.add(HomeHotPlaceItem(R.drawable.sample_img_dog, "category", "title5", "1.4", "555"))

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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_home_main_start -> {
                Log.d(TAG, "onClick - home_start")
                startWalk()
            }
        }
    }
    private fun startWalk() {
        // main banner background 수정
        var drawable = Utils.replaceBackgroundInLayerList(requireContext(), null, R.drawable.home_mainbanner_background, R.id.home_mainbanner_back_top)
        binding.homeMainLayout.background = drawable

        // start walk api 호출
        // 일시정지 / 계속, 정지 버튼, 시간, 거리 표시
    }

}