package com.ssafy.heritage.view.heritage

import android.Manifest
import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageInfoBinding
import com.ssafy.heritage.viewmodel.HeritageViewModel
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.io.IOException

private const val TAG = "HeritageInfoFragment___"

private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION

class HeritageInfoFragment :
    BaseFragment<FragmentHeritageInfoBinding>(R.layout.fragment_heritage_info),
    MapView.POIItemEventListener {

    private val viewModel: HeritageViewModel by activityViewModels()
    private var heritage: Heritage? = null

    private lateinit var btnPlayAudio: Button
    var mediaPlayer: MediaPlayer? = null


    private lateinit var mapView: MapView

    override fun init() {
        initObserver()
        initClickListener()

//        initMap()
    }

    // 소리 관련인데
    override fun onStop() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onStop()
    }

    // 뷰모델
    private fun initObserver() {
        viewModel.heritage.observe(viewLifecycleOwner) {
            Log.d("blah blah", it.toString())
            binding.heritage = it
            heritage = it
        }
    }

    // 내레이션 버튼
    private fun initClickListener() {
        binding.apply {
            imagebtnHeritageDetailVoice.setOnClickListener {
                playAudio()
//                if (mediaPlayer!!.isPlaying) {
//                    pauseAudio()
//                }else {
//                    playAudio()
//                }
            }
        }
    }

    private fun playAudio() {
        // 이게 맞나....
        // mediaPlayer는 not null인데 heritage가 nullable이라서 안되나?? >> 예!
//        val audioURL = "${heritage?.heritageVoice}"
//        Log.d(TAG, heritage?.heritageVoice.toString())
        val audioURL = "http://116.67.83.213/media/voice/krVoice/11/kr-11-00030000-11.mp3"
        Log.d(TAG, audioURL)
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaPlayer!!.setDataSource(audioURL)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
            Log.d(TAG, "play!!")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun pauseAudio() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            Log.d(TAG, "stop")
        }
    }

    // 지도
    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())
        mapView.setPOIItemEventListener(
            this@HeritageInfoFragment
        )
        map.addView(mapView)
        setLocation()
        makeMarker()
    }

    private fun setLocation() {
        mapView.setMapCenterPointAndZoomLevel(
            MapPoint.mapPointWithGeoCoord(
                binding.heritage!!.heritageLat.toDouble(),
                binding.heritage!!.heritageLng.toDouble()
            ), 1, true
        )
    }

    private fun makeMarker() {
        // 문화유산 마커 찍기
        MapPOIItem().apply {
            itemName = heritage!!.heritageName
            mapPoint =
                MapPoint.mapPointWithGeoCoord(
                    binding.heritage!!.heritageLat.toDouble(),
                    binding.heritage!!.heritageLng.toDouble()
                )
            markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
            selectedMarkerType =
                MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround

            mapView.addPOIItem(this)

        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
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