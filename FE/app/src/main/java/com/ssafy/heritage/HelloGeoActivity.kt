package com.ssafy.heritage

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.core.exceptions.*
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.helpers.ARCoreSessionLifecycleHelper
import com.ssafy.heritage.helpers.FullScreenHelper
import com.ssafy.heritage.helpers.GeoPermissionsHelper
import com.ssafy.heritage.samplerender.SampleRender
import com.ssafy.heritage.view.dialog.ARCheckDialog
import com.ssafy.heritage.view.dialog.ARCheckDialogInterface
import com.ssafy.heritage.viewmodel.ARViewModel
import kotlinx.coroutines.*

private const val TAG = "HelloGeoActivity"

class HelloGeoActivity : AppCompatActivity(), ARCheckDialogInterface {

    lateinit var arCoreSessionHelper: ARCoreSessionLifecycleHelper
    lateinit var view: HelloGeoView
    lateinit var renderer: HelloGeoRenderer
    private val arViewModel by viewModels<ARViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()
    val stampInfo: Stamp = ApplicationClass.sharedPreferencesUtil.getStamp()  // 주변 스탬프 정보 받아오기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup ARCore session lifecycle helper and configuration.
        arCoreSessionHelper = ARCoreSessionLifecycleHelper(this)
        // If Session creation or Session.resume() fails, display a message and log detailed
        // information.
        arCoreSessionHelper.exceptionCallback =
            { exception ->
                val message =
                    when (exception) {
                        is UnavailableUserDeclinedInstallationException ->
                            "Please install Google Play Services for AR"
                        is UnavailableApkTooOldException -> "Please update ARCore"
                        is UnavailableSdkTooOldException -> "Please update this app"
                        is UnavailableDeviceNotCompatibleException -> "This device does not support AR"
                        is CameraNotAvailableException -> "Camera not available. Try restarting the app."
                        else -> "Failed to create AR session: $exception"
                    }
                Log.e(TAG, "ARCore threw an exception", exception)
                view.snackbarHelper.showError(this, message)
            }

        // 세션기능 구성
        arCoreSessionHelper.beforeSessionResume = ::configureSession
        lifecycle.addObserver(arCoreSessionHelper)

        // renderer 설정
        renderer = HelloGeoRenderer(this)
        lifecycle.addObserver(renderer)

        // Set up Hello AR UI.
        view = HelloGeoView(this)
        lifecycle.addObserver(view)
        setContentView(view.root)

        // Sets up an example renderer using our HelloGeoRenderer.
        SampleRender(view.surfaceView, renderer, assets)

        CoroutineScope(Dispatchers.Main).launch {
            set()
        }
        Log.d(TAG, "onCreate: ${stampInfo.heritageLat}")

        view.btnCheck.setOnClickListener {
            Log.d(TAG,"btnCheck")
            // 획득한 스탬프 전송
            arViewModel.addStamp(userSeq, stampInfo.stampSeq)
            ApplicationClass.sharedPreferencesUtil.deleteStamp()
            val dialog = ARCheckDialog(it.context, this)
            dialog.show()
        }
    }

    // Configure the session, setting the desired options according to your usecase.
    fun configureSession(session: Session) {
        session.configure(
            session.config.apply {
                // Enable Geospatial Mode.
                geospatialMode = Config.GeospatialMode.ENABLED
            }
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        results: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, results)
        if (!GeoPermissionsHelper.hasGeoPermissions(this)) {
            // Use toast instead of snackbar here since the activity will exit.
            Toast.makeText(
                this,
                "Camera and location permissions are needed to run this application",
                Toast.LENGTH_LONG
            )
                .show()
            if (!GeoPermissionsHelper.shouldShowRequestPermissionRationale(this)) {
                // Permission denied with checking "Do not ask again".
                GeoPermissionsHelper.launchPermissionSettings(this)
            }
            finish()
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        FullScreenHelper.setFullScreenOnWindowFocusChanged(this, hasFocus)
    }

    override fun onHomeBtnClicked() {
        finish()
    }

//    override fun onARListBtnClicked() {
//        finish()
//    }

    suspend fun set() = coroutineScope {
        delay(7000)
        renderer.onMapInit()
    }
}
