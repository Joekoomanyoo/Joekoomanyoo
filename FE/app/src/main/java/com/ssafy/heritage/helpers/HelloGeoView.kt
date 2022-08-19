package com.ssafy.heritage

import android.opengl.GLSurfaceView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.ar.core.Earth
import com.google.ar.core.GeospatialPose
import com.google.ar.core.Pose
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.helpers.MapTouchWrapper
import com.ssafy.heritage.helpers.MapView
import com.ssafy.heritage.helpers.SnackbarHelper

private const val TAG = "HelloGeoView___"

class HelloGeoView(val activity: HelloGeoActivity) : DefaultLifecycleObserver {
    val root = View.inflate(activity, R.layout.activity_hello_geo, null)
    val surfaceView = root.findViewById<GLSurfaceView>(R.id.surfaceview)
    val btnCheck = root.findViewById<Button>(R.id.btn_check)

    val session
        get() = activity.arCoreSessionHelper.session

    val snackbarHelper = SnackbarHelper()

    var mapView: MapView? = null
    val mapTouchWrapper = root.findViewById<MapTouchWrapper>(R.id.map_wrapper)
    val mapFragment =
        (activity.supportFragmentManager.findFragmentById(R.id.map)!! as SupportMapFragment).also {
            it.getMapAsync { googleMap -> mapView = MapView(activity, googleMap) }
        }

    val statusText = root.findViewById<TextView>(R.id.statusText)
    val statusTitle = root.findViewById<TextView>(R.id.statusTitle)
    fun updateStatusText(stamp: Stamp, earth: Earth?, cameraGeospatialPose: GeospatialPose?) {
        activity.runOnUiThread {
            statusTitle.text = "목표 스탬프 : ${stamp.stampTitle}"
//            val poseText = if (cameraGeospatialPose == null) "" else
//                activity.getString(
//                    R.string.geospatial_pose,
//                    cameraGeospatialPose.latitude,
//                    cameraGeospatialPose.longitude,
//                    cameraGeospatialPose.horizontalAccuracy,
//                    cameraGeospatialPose.altitude,
//                    cameraGeospatialPose.verticalAccuracy,
//                    cameraGeospatialPose.heading,
//                    cameraGeospatialPose.headingAccuracy
//                )
//            statusText.text = activity.resources.getString(
//                R.string.earth_state,
//                earth?.earthState.toString(),
//                earth?.trackingState.toString(),
//                poseText
//            )
        }
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        val stampInfo = activity.stampInfo

        Log.d(TAG, "onCreate: ${stampInfo.heritageLat}")
        if (stampInfo.heritageLat != "null") {
            Log.d(TAG, "onCreate: ${stampInfo.heritageLat}, ${stampInfo.heritageLng}")
            mapView?.earthMarker?.apply {
                position =
                    LatLng(stampInfo.heritageLat.toDouble(), stampInfo.heritageLng.toDouble())
                isVisible = true
            }
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        surfaceView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        surfaceView.onPause()
    }
}
