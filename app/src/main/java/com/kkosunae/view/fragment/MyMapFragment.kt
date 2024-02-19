package com.kkosunae.view.fragment

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kkosunae.R
import com.kkosunae.adapter.HomeListAdapter
import com.kkosunae.databinding.FragmentMapBinding
import com.kkosunae.model.FootData
import com.kkosunae.model.HomeItem
import com.kkosunae.network.RetrofitManager
import com.kkosunae.view.activity.MainActivity
import com.kkosunae.view.activity.NotificationActivity
import com.kkosunae.view.activity.WriteActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileInputStream

class MyMapFragment : Fragment(), OnMapReadyCallback, View.OnClickListener, NaverMap.OnLocationChangeListener{
    override fun onLocationChange(p0: Location) {
        p0.latitude
        p0.longitude
        //발자국 조회 api 호출.
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val resultData = data?.getStringExtra("resultKey")
            // 받아온 데이터 처리
            Log.d(TAG, "$resultData")
            val file = File("/Users/dd/development/Kkosunae/app/src/main/res/drawable/sample_img_dog.png")
            // 발자국 생성 api호출.
            runBlocking {
                val result = async {
                    RetrofitManager.instance.postFootPrint(FootData(resultData, 11.22, 12.22, file))
                }
                result.await()
            }

        }
    }
    private fun startWriteActivity() {
        val intent = Intent(activity, WriteActivity::class.java)
        launcher.launch(intent)
    }
    val TAG : String = "MyMapFragment"
    lateinit var binding: FragmentMapBinding
    private lateinit var mNaverMap: NaverMap
    private lateinit var mLocationSource: FusedLocationSource
    private var isFabOpen = false
    private lateinit var bottomSheetLayout : LinearLayout
    private lateinit var bottomSheetBehavior :BottomSheetBehavior<LinearLayout>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG,"onCreateView()")
        binding = FragmentMapBinding.inflate(inflater)
        initBottomSheet()
        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.map_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e(TAG,"onViewCreated()")
        val options = NaverMapOptions()
            .camera(CameraPosition(LatLng(37.496486063, 127.028361548),  10.0))  // 카메라 위치 (위도,경도,줌)
            .mapType(NaverMap.MapType.Basic)    //지도 유형
            .enabledLayerGroups(NaverMap.LAYER_GROUP_BUILDING)  //빌딩 표시

        val fm = childFragmentManager
        val mapFragment = fm.findFragmentById(R.id.fragment_container_map) as MapFragment?
            ?:MapFragment.newInstance(options).also {
                fm.beginTransaction().add(R.id.fragment_container_map, it).commit()
            }
        mapFragment.getMapAsync(this)

        binding.mapButtonFoot.setOnClickListener {
            Log.d(TAG, "map_foot button click!")
            startWriteActivity()
//            var intent = Intent(context, WriteActivity::class.java)
//            startActivity(intent)
        }
//         toggleFab 기능 삭제
//        binding.mapFabFilter.setOnClickListener{
//            toggleFab()
//        }
        initRecyclerView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (mLocationSource.onRequestPermissionsResult(requestCode,permissions, grantResults)) {
            if (!mLocationSource.isActivated) {
                mNaverMap.locationTrackingMode = LocationTrackingMode.Follow
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onMapReady(naverMap: NaverMap) {
        Log.e(TAG,"onMapReady()")
//        MapFragment.newInstance(options)
        mNaverMap = naverMap
        naverMap.setOnMapClickListener { point, coord ->
            if (binding.mapSearch.visibility == View.VISIBLE) {
                binding.mapSearch.visibility = View.INVISIBLE
                binding.btnFilter.visibility = View.INVISIBLE
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
            else {
                binding.mapSearch.visibility = View.VISIBLE
                binding.btnFilter.visibility = View.VISIBLE
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

        }

        val uiSettings = mNaverMap.uiSettings
        val locationOverlay = mNaverMap.locationOverlay
        mNaverMap.locationSource = mLocationSource

        uiSettings.isLocationButtonEnabled = false
        uiSettings.isCompassEnabled = false
        uiSettings.isScaleBarEnabled = false
        uiSettings.isZoomControlEnabled = false
        uiSettings.isLocationButtonEnabled = false
        val logo = binding.navermapLogo
        logo.setMap(naverMap)
        logo.isClickable = false
        val location = binding.navermapLocationButton
        location.map = naverMap

        locationOverlay.isVisible = true
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.4964860636, 127.028361548))
            .animate(CameraAnimation.Fly, 1000)
        mNaverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = LatLng(37.4964860636, 127.028361548)
        marker.map = mNaverMap
    }

    private fun initRecyclerView() {
        var itemList = ArrayList<HomeItem>()
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        itemList.add(HomeItem("chlghduf", "sodyd"))
        val recyclerView = binding.bottomSheet.mapBottomSheetRv
        val recyclerAdapter = HomeListAdapter(itemList)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
//      기능 삭제
//    private fun toggleFab() {
//        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
//        if (isFabOpen) {
//            ObjectAnimator.ofFloat(binding.mapFabFilter3, "translationY", 0f).apply { start() }
//            ObjectAnimator.ofFloat(binding.mapFabFilter2, "translationY", 0f).apply { start() }
//            ObjectAnimator.ofFloat(binding.mapFabFilter, View.ROTATION, 45f, 0f).apply { start() }
//        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
//            ObjectAnimator.ofFloat(binding.mapFabFilter3, "translationY", 440f).apply { start() }
//            ObjectAnimator.ofFloat(binding.mapFabFilter2, "translationY", 220f).apply { start() }
//            ObjectAnimator.ofFloat(binding.mapFabFilter, View.ROTATION, 0f, 45f).apply { start() }
//        }
//
//        isFabOpen = !isFabOpen
//
//    }


    private fun initBottomSheet() {
        bottomSheetLayout = binding.bottomSheet.root
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior.isGestureInsetBottomIgnored = true
        bottomSheetBehavior.isDraggable = true
        bottomSheetBehavior.maxHeight = resources.getDimensionPixelSize(R.dimen.expanded_height)
        bottomSheetBehavior.peekHeight = resources.getDimensionPixelSize(R.dimen.collapsed_height)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {

                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {

                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {

                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("onSlide","slideOffset : "+ slideOffset)
//                if (slideOffset <= 0.5 && slideOffset > 0.1 ) {
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//                } else if (slideOffset < 0.1) {
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
//                } else
//                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        })
        Log.d("peekHeight","peekHeight : "+ bottomSheetBehavior.peekHeight.toString())
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}