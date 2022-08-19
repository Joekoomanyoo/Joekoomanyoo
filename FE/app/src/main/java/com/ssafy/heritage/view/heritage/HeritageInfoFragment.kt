package com.ssafy.heritage.view.heritage

import android.Manifest
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
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
import java.lang.Exception

private const val TAG = "HeritageInfoFragment___"

private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION

class HeritageInfoFragment :
    BaseFragment<FragmentHeritageInfoBinding>(R.layout.fragment_heritage_info),
    MapView.POIItemEventListener {

    private val viewModel: HeritageViewModel by activityViewModels()
    private var heritage: Heritage? = null

    private lateinit var btnPlayAudio: Button
    var mediaPlayer: MediaPlayer? = null
    var audioCheck = false
    var audioManager: AudioManager? = null
    var focusRequest: AudioFocusRequest? = null

    private lateinit var mapView: MapView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        initObserver()
        initClickListener()
    }

    override fun onStart() {
        super.onStart()
        initMap()
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initClickListener() {
        binding.apply {

            // 오디오 포커스
//            audioManager = context?.applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
//            // Android 8 이상: AudioFocusRequest 빌드
//            focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
//                // 오디오 용도 정보 세팅
//                setAudioAttributes(AudioAttributes.Builder().run {
//                    setUsage(AudioAttributes.USAGE_MEDIA)
//                    setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
//                    build()
//                })
//                setAcceptsDelayedFocusGain(true)
//                setOnAudioFocusChangeListener(afChangeListener, handler)
//                build()
//            }
//            val focusLock = Any()
//            var playbackDelayed = false
//            var playbackNowAuthorized = false
//
//            val res = audioManager!!.requestAudioFocus(focusRequest)
//            synchronized(focusLock) {
//                playbackNowAuthorized = when (res) {
//                    AudioManager.AUDIOFOCUS_REQUEST_FAILED -> false
//                    AudioManager.AUDIOFOCUS_REQUEST_GRANTED -> {
//                        playbackNow()
//                        true
//                    }
//                    AudioManager.AUDIOFOCUS_REQUEST_DELAYED -> {
//                        playbackDelayed = true
//                        false
//                    }
//                    else -> false
//                }
//            }
//
//            override fun onAudioFocusChange(focusChange: Int) {
//                when (focusChange) {
//                    AudioManager.AUDIOFOCUS_GAIN ->
//                        if (playbackDelayed || resumeOnFocusGain) {
//                            synchronized(focusLock) {
//                                playbackDelayed = false
//                                resumeOnFocusGain = false
//                            }
//                            playbackNow()
//                        }
//                    AudioManager.AUDIOFOCUS_LOSS -> {
//                        synchronized(focusLock) {
//                            resumeOnFocusGain = false
//                            playbackDelayed = false
//                        }
//                        pausePlayback()
//                    }
//                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
//                        synchronized(focusLock) {
//                            resumeOnFocusGain = true
//                            playbackDelayed = false
//                        }
//                        pausePlayback()
//                    }
//                    AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
//                        // ... pausing or ducking depends on your app
//                    }
//                }
//            }

            val audioURL = "http://116.67.83.213/media/voice/krVoice/11/kr-11-00030000-11.mp3"
            Log.d(TAG, audioURL)
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)


            imagebtnHeritageDetailVoicePlay.setOnClickListener {
                if (mediaPlayer!!.isPlaying) {
                    try {
                        // 재생을 일시정지
                        mediaPlayer!!.pause()
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                } else {
                    try {
                        mediaPlayer!!.setDataSource(audioURL)
                        mediaPlayer!!.prepare()
                        mediaPlayer!!.setOnPreparedListener {
                            mediaPlayer!!.start()
                        }
                    } catch (e: IllegalStateException) {
                        e.printStackTrace()
                    }
                }
            }

            imagebtnHeritageDetailVoicePause.setOnClickListener {
                if (mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.pause()
                    audioCheck = true
                    Log.d(TAG, "Audio pause")
                }
            }
        }
    }

    // 앱을 일시적으로 숨겼을 때 일시정지
    override fun onPause() {
        super.onPause()
        try {
            mediaPlayer?.pause()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    // Destroys the MediaPlayer instance when the app is closed
    override fun onStop() {
        super.onStop()
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    // 오디오 포커스 나머지
//    private val handler = Handler()
//    // 오디오 포커스 뺏길 때
//    private val afChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
//        when (focusChange) {
//            // 일시적인 포커스 손실 (일시정지)
//            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
//            }
//
////            /* 새로운 포커스 소유자가 다른 사람에게 침묵을 요구하지 않기 때문에
////            오디오 포커스를 잃은 사람이 계속 재생하려는 경우("더킹"이라고도 함)
////            출력 볼륨을 낮출 수 있는 오디오 포커스의 일시적인 손실을 나타내는 데 사용됩니다.*/
//////            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
////            }
////            // 영구적인 포커스 손실
////            AudioManager.AUDIOFOCUS_LOSS -> {
////                // 즉시 재생 일시 중지
////                mediaController.transportControls.pause()
////                // 다시 재생하기까지 30초 기다림
////                handler.postDelayed(delayedStopRunnable, TimeUnit.SECONDS.toMillis(30))
////            }
//
//            // 지속 시간을 알 수 없는 오디오 포커스의 증가 또는 오디오 포커스의 요청을 나타내는 데 사용됩니다.
//            AudioManager.AUDIOFOCUS_GAIN -> {
//                // 앱에 오디오 포커스가 다시 부여되었습니다
//                // 볼륨을 정상으로 높이고, 필요한 경우 재생을 다시 시작합니다.
//            }
//        }
//    }
//
//    private var delayedStopRunnable = Runnable {
//        mediaController.transportControls.stop()
//    }

    // 지도
    private fun initMap() = with(binding) {
        mapView = MapView(requireContext())
        mapView.setPOIItemEventListener(
            this@HeritageInfoFragment
        )
        map.addView(mapView)
        Log.d(TAG, "initMap: ${viewModel.heritage.value?.heritageLat}")
        Log.d(TAG, "initMap: ${viewModel.heritage.value?.heritageLng}")

//        setLocation()
//        makeMarker()
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