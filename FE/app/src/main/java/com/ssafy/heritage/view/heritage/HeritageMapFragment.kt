package com.ssafy.heritage.view.heritage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageMapBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.viewmodel.HeritageViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


private const val TAG = "HeritageMapFragment___"

private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HeritageMapFragment :
    BaseFragment<FragmentHeritageMapBinding>(R.layout.fragment_heritage_map) {

    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    private val locationManager by lazy {
        requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private lateinit var mapView: MapView

    private var lat = 0.0    // 위도
    private var lng = 0.0    // 경도

    override fun init() {

        initMap()

        initAdapter()

        initObserver()
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())
        map.addView(mapView)
        requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
    }

    private fun initAdapter() = with(binding) {
    }

    private fun initObserver() {

    }

    private val requestPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED

                // 위치정보 얻어옴
                getLastLocation()

                // 현재 위치로 중심점 변경
                setLocation()

                // 마커 찍기
                makeMarker()
            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun getLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
            return
        }
        locationManager
            .getLastKnownLocation(LocationManager.GPS_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }
    }

    private fun setLocation() {
        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                lat,
                lng
            ), 1, true
        )
    }

    private fun makeMarker() {

        heritageViewModel.heritageList.value?.forEach {
            val marker = MapPOIItem()
            marker.itemName = "Default Marker"
            marker.mapPoint =
                MapPoint.mapPointWithGeoCoord(it.heritageLat.toDouble(), it.heritageLng.toDouble())
            marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.

            marker.selectedMarkerType =
                MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.


            mapView.addPOIItem(marker)
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}