/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ssafy.heritage.helpers


import android.content.res.Resources
import android.graphics.*
import androidx.annotation.ColorInt
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.ssafy.heritage.HelloGeoActivity
import com.ssafy.heritage.R

class MapView(val activity: HelloGeoActivity, val googleMap: GoogleMap) {

  private val CAMERA_MARKER_COLOR: Int = Color.argb(255, 0, 255, 0)
  private val EARTH_MARKER_COLOR: Int = Color.argb(255, 125, 125, 125)

  var setInitialCameraPosition = false
  val cameraMarker = createMarker(CAMERA_MARKER_COLOR)
  var cameraIdle = true


  val earthMarker =  createMarker(EARTH_MARKER_COLOR)


  init {
    googleMap.uiSettings.apply {
      isMapToolbarEnabled = false
      isIndoorLevelPickerEnabled = false
      isZoomControlsEnabled = false
      isTiltGesturesEnabled = false
      isScrollGesturesEnabled = false
    }

    googleMap.setOnMarkerClickListener { unused -> false }

    // Add listeners to keep track of when the GoogleMap camera is moving.
    googleMap.setOnCameraMoveListener { cameraIdle = false }
    googleMap.setOnCameraIdleListener { cameraIdle = true }
  }

  fun updateMapPosition(latitude: Double, longitude: Double, heading: Double) {
    val position = LatLng(latitude, longitude)

    activity.runOnUiThread {

      // If the map is already in the process of a camera update, then don't move it.
      if (!cameraIdle) {
        return@runOnUiThread
      }
      cameraMarker.isVisible = true
      cameraMarker.position = position
      cameraMarker.rotation = heading.toFloat()

      val cameraPositionBuilder: CameraPosition.Builder = if (!setInitialCameraPosition) {
        // Set the camera position with an initial default zoom level.
        setInitialCameraPosition = true

        CameraPosition.Builder().zoom(21f).target(position)

      } else {
        // Set the camera position and keep the same zoom level.
        CameraPosition.Builder()
          .zoom(googleMap.cameraPosition.zoom)
          .target(position)
      }
      googleMap.moveCamera(
        CameraUpdateFactory.newCameraPosition(cameraPositionBuilder.build()))
    }
  }

  /** Creates and adds a 2D anchor marker on the 2D map view.  */
  private fun createMarker(
    color: Int,
  ): Marker {
    val markersOptions = MarkerOptions()
      .position(LatLng(0.0,0.0))
      .draggable(false)
      .anchor(0.5f, 0.5f)
      .flat(true)
      .visible(false)
      .icon(BitmapDescriptorFactory.fromBitmap(createColoredMarkerBitmap(color)))
    return googleMap.addMarker(markersOptions)!!
  }

  private fun createMarker2(): Marker {
    val markersOptions = MarkerOptions()
      .position(LatLng(0.0,0.0))
      .draggable(false)
      .anchor(0.5f, 0.5f)
      .flat(true)
      .visible(false)
      .icon(BitmapDescriptorFactory.fromBitmap(createHeritageMarkerBitmap()))
    return googleMap.addMarker(markersOptions)!!
  }

  private fun createColoredMarkerBitmap(@ColorInt color: Int): Bitmap {
    val opt = BitmapFactory.Options()
    opt.inMutable = true
    val navigationIcon =
      BitmapFactory.decodeResource(activity.resources, R.drawable.ic_navigation_white_48dp, opt)
    val p = Paint()
    p.colorFilter = LightingColorFilter(color,  /* add= */1)
    val canvas = Canvas(navigationIcon)
    canvas.drawBitmap(navigationIcon,  /* left= */0f,  /* top= */0f, p)
    return navigationIcon
  }

  private fun createHeritageMarkerBitmap(): Bitmap {
    val navigationIcon =
      decodeSampledBitmapFromResource(activity.resources,R.drawable.monster, 20, 20)
    val canvas = Canvas(navigationIcon)
    canvas.drawBitmap(navigationIcon,  /* left= */0f,  /* top= */0f, null)
    return navigationIcon
  }
  fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    // Raw height and width of image
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

      val halfHeight: Int = height / 2
      val halfWidth: Int = width / 2

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
        inSampleSize *= 2
      }
    }

    return inSampleSize
  }

  fun decodeSampledBitmapFromResource(
    res: Resources,
    resId: Int,
    reqWidth: Int,
    reqHeight: Int
  ): Bitmap {
    // First decode with inJustDecodeBounds=true to check dimensions
    return BitmapFactory.Options().run {
      inJustDecodeBounds = true
      BitmapFactory.decodeResource(res, resId, this)

      // Calculate inSampleSize
      inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

      // Decode bitmap with inSampleSize set
      inJustDecodeBounds = false
      inMutable = true
      BitmapFactory.decodeResource(res, resId, this)
    }
  }

}