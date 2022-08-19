package com.ssafy.heritage.view.group

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mikhaellopez.rxanimation.RxAnimation
import com.mikhaellopez.rxanimation.alpha
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.GroupDestinationMap
import com.ssafy.heritage.databinding.FragmentGroupMapBinding
import com.ssafy.heritage.databinding.ItemHeritageBinding
import com.ssafy.heritage.view.heritage.HeritageDetailFragment
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

private const val TAG = "GroupMapFragment___"

class GroupMapFragment : BaseFragment<FragmentGroupMapBinding>(R.layout.fragment_group_map),
    MapView.POIItemEventListener {

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val heritageViewModel by activityViewModels<HeritageViewModel>()

    private lateinit var mapView: MapView

    override fun init() {
        CoroutineScope(Main).launch {
            delay(500)

            initMap()

            initObserver()

            initView()

            initClickListener()
        }
    }

    private fun initClickListener()= with(binding) {
        tvAlert.setOnClickListener {
            findNavController().navigate(R.id.action_groupInfoFragment_to_heritageListFragment)
        }
    }

    private fun initView() = with(binding) {
//        CoroutineScope(Main).launch {
//            delay(5000)
//            RxAnimation.from(tvAlert)
//                .alpha(0f, duration = 3000L, reverse = false)
//                .subscribe()
//        }
    }

    override fun onStart() {
        super.onStart()
        groupViewModel.getGroupDestination()
    }

    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())

        // 마커 클릭 리스너 설정
        mapView.setPOIItemEventListener(
            this@GroupMapFragment
        )

        map.addView(mapView)

        mapView.setZoomLevel(11, true)
    }

    private fun initObserver() {
        groupViewModel.groupDestination.observe(viewLifecycleOwner) {
            makeGroupMarker(it as MutableList<GroupDestinationMap>)
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
                val marker: Bitmap = Bitmap.createScaledBitmap(b, 100, 100, false)

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

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onPOIItemSelected(p0: MapView?, poiItem: MapPOIItem?) {

        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                poiItem?.mapPoint?.mapPointGeoCoord?.latitude!!,
                poiItem?.mapPoint?.mapPointGeoCoord?.longitude!!
            ), 1, true
        )

        val data = poiItem?.tag?.let { groupViewModel.groupDestination.value?.get(it) }

        binding.frame.removeAllViews()
        val inflater = LayoutInflater.from(context)
        val cardBinding: ItemHeritageBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_heritage, binding.frame, true)
        cardBinding.root.background =
            resources.getDrawable(R.drawable.background_heritage_map_item, null)
        cardBinding.heritage = data?.heritage

        ViewCompat.setTransitionName(cardBinding.ivHeritage, "heritage${data?.heritageSeq}")

        cardBinding.root.setOnClickListener {

            heritageViewModel.setHeritage(data?.heritage!!)

            binding.map.removeAllViews()

            requireActivity().supportFragmentManager
                .beginTransaction()
                .addSharedElement(cardBinding.ivHeritage, "heritage")
                .addToBackStack(null)
                .replace(
                    R.id.fragment_container_main,
                    HeritageDetailFragment.newInstance(data?.heritage!!)
                )
                .commit()
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