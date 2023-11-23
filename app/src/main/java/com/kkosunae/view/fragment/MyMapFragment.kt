package com.kkosunae.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kkosunae.R
import com.kkosunae.databinding.FragmentMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker

class MyMapFragment : Fragment(), OnMapReadyCallback, View.OnClickListener{
    val TAG : String = "MyMapFragment"
    lateinit var binding: FragmentMapBinding
    private var isFabOpen = false
    private lateinit var bottomSheetLayout : LinearLayoutCompat
    private lateinit var bottomSheetBehavior :BottomSheetBehavior<LinearLayoutCompat>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG,"onCreateView()")
        binding = FragmentMapBinding.inflate(inflater)
        initBottomSheet()
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

        binding.mapFabFilter.setOnClickListener{
            toggleFab()
        }
    }
    private fun toggleFab() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            ObjectAnimator.ofFloat(binding.mapFabFilter3, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mapFabFilter2, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.mapFabFilter, View.ROTATION, 45f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(binding.mapFabFilter3, "translationY", 440f).apply { start() }
            ObjectAnimator.ofFloat(binding.mapFabFilter2, "translationY", 220f).apply { start() }
            ObjectAnimator.ofFloat(binding.mapFabFilter, View.ROTATION, 0f, 45f).apply { start() }
        }

        isFabOpen = !isFabOpen

    }

    override fun onMapReady(naverMap: NaverMap) {
        Log.e(TAG,"onMapReady()")
//        MapFragment.newInstance(options)
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.4964860636, 127.028361548))
            .animate(CameraAnimation.Fly, 1000)
        naverMap.moveCamera(cameraUpdate)


        val marker = Marker()
        marker.position = LatLng(37.4964860636, 127.028361548)
        marker.map = naverMap
    }
    private fun initBottomSheet() {
        bottomSheetLayout = binding.bottomSheet.root
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        Log.d("peekHeight","peekHeight : "+ bottomSheetBehavior.peekHeight.toString())
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.map_button_modal_bottom_sheet -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            }
            R.id.map_button_persistent_bottom_sheet -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

            }
        }
    }
}