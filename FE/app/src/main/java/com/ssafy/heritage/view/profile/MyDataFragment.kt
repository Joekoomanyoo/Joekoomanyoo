package com.ssafy.heritage.view.profile

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.GroupDestinationMap
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.databinding.FragmentMyDataBinding
import com.ssafy.heritage.databinding.ItemHeritageBinding
import com.ssafy.heritage.view.ar.ARFoundFragment
import com.ssafy.heritage.viewmodel.UserViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

private const val TAG = "MyDataFragment___"

class MyDataFragment : BaseFragment<FragmentMyDataBinding>(R.layout.fragment_my_data),
    MapView.POIItemEventListener {

    private val userViewModel by activityViewModels<UserViewModel>()

    private lateinit var mapView: MapView

    override fun init() {

        initMap()

        initView()

        initObserver()

        initClickListener()

    }

    private fun initClickListener() = with(binding) {
        btnOpenMap.setOnClickListener {
            frame.removeAllViews()

            it.isSelected = !it.isSelected
            btnOpenStamp.isSelected = false
            btnOpenGraph.isSelected = false

            if (it.isSelected) {
                map.visibility = View.VISIBLE
                frameStamp.visibility = View.GONE
                frameGraph.visibility = View.GONE
            } else {
                map.visibility = View.GONE
                frameStamp.visibility = View.GONE
                frameGraph.visibility = View.GONE
            }
        }

        btnOpenStamp.setOnClickListener {
            frame.removeAllViews()

            it.isSelected = !it.isSelected
            btnOpenMap.isSelected = false
            btnOpenGraph.isSelected = false

            if (it.isSelected) {
                map.visibility = View.GONE
                frameStamp.visibility = View.VISIBLE
                frameGraph.visibility = View.GONE
            } else {
                map.visibility = View.GONE
                frameStamp.visibility = View.GONE
                frameGraph.visibility = View.GONE
            }
        }

        btnOpenGraph.setOnClickListener {
            frame.removeAllViews()

            it.isSelected = !it.isSelected
            btnOpenMap.isSelected = false
            btnOpenStamp.isSelected = false

            if (it.isSelected) {
                map.visibility = View.GONE
                frameStamp.visibility = View.GONE
                frameGraph.visibility = View.VISIBLE
            } else {
                map.visibility = View.GONE
                frameStamp.visibility = View.GONE
                frameGraph.visibility = View.GONE
            }
        }
    }

    private fun initView() = with(binding) {
        btnOpenMap.isSelected = true

        val stampFragment = ARFoundFragment()
        childFragmentManager.beginTransaction().replace(R.id.frame_stamp, stampFragment).commit()

        val statisticFragment = StatisticFragment()
        childFragmentManager.beginTransaction().replace(R.id.frame_graph, statisticFragment)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getMydestination()
        userViewModel.getMyStamp()
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())

        // 마커 클릭 리스너 설정
        mapView.setPOIItemEventListener(
            this@MyDataFragment
        )

        map.addView(mapView)

        mapView.setZoomLevel(11, true)
    }

    private fun initObserver() {
        userViewModel.myDestinationList.observe(viewLifecycleOwner) {
            makeGroupMarker(it)
        }
        userViewModel.myStampList.observe(viewLifecycleOwner) {
            makeStampMarker(it)
        }
    }

    // 모임에서 설정한 마커
    private fun makeGroupMarker(list: MutableList<GroupDestinationMap>) {

        list.forEachIndexed { index, destination ->
            MapPOIItem().apply {
                itemName = destination.heritage.heritageName
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(
                        destination.heritage.heritageLat.toDouble(),
                        destination.heritage.heritageLng.toDouble()
                    )

                val bitmapdraw: BitmapDrawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_group_location,
                    null
                ) as BitmapDrawable
                val b: Bitmap = bitmapdraw.bitmap
                val marker: Bitmap = Bitmap.createScaledBitmap(b, 50, 50, false)

                markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
                customImageBitmap = marker
                customSelectedImageBitmap = marker

                showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround
                tag = index + 100

                mapView.addPOIItem(this)
            }
        }
    }

    // 스탬프 획득한 곳 마커
    private fun makeStampMarker(list: MutableList<Stamp>) {
        list.forEachIndexed { index, destination ->
            MapPOIItem().apply {
                itemName = destination.stampTitle
                mapPoint =
                    MapPoint.mapPointWithGeoCoord(
                        destination.heritageLat.toDouble(),
                        destination.heritageLng.toDouble()
                    )

                val bitmapdraw: BitmapDrawable = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_stamp_location,
                    null
                ) as BitmapDrawable
                val b: Bitmap = bitmapdraw.bitmap
                val marker: Bitmap = Bitmap.createScaledBitmap(b, 50, 50, false)

                markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
                selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
                customImageBitmap = marker
                customSelectedImageBitmap = marker

                showAnimationType = MapPOIItem.ShowAnimationType.DropFromHeaven
                tag = index

                mapView.addPOIItem(this)
            }
        }
    }

    override fun onPOIItemSelected(p0: MapView?, poiItem: MapPOIItem?) {

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                poiItem?.mapPoint?.mapPointGeoCoord?.latitude!!,
                poiItem?.mapPoint?.mapPointGeoCoord?.longitude!!
            ), 1, true
        )

        binding.frame.removeAllViews()

        if (poiItem?.tag > 99) {
            val data = poiItem?.tag?.let { userViewModel.myDestinationList.value?.get(it - 100) }

            val inflater = LayoutInflater.from(context)
            val cardBinding: ItemHeritageBinding =
                DataBindingUtil.inflate(inflater, R.layout.item_heritage, binding.frame, true)
            cardBinding.root.background =
                resources.getDrawable(R.drawable.background_heritage_map_item, null)
            cardBinding.heritage = data?.heritage

            ViewCompat.setTransitionName(cardBinding.ivHeritage, "heritage${data?.heritageSeq}")
        }
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }
}