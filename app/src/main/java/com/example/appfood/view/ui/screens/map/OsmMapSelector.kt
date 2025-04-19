@file:Suppress("DEPRECATION")

package com.example.appfood.view.ui.screens.map

import android.location.Geocoder
import android.preference.PreferenceManager
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.appfood.viewModel.LocationViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*

@Composable
fun OsmMapSelector(
    locationViewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
        Configuration.getInstance().userAgentValue = context.packageName
    }

    AndroidView(
        modifier = modifier,
        factory = {
            val mapView = MapView(it).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                controller.setZoom(15.0)
                controller.setCenter(GeoPoint(10.762622, 106.660172))

                val bboxVN = BoundingBox(23.393395, 109.469245, 8.565667, 102.14441)
                setScrollableAreaLimitDouble(bboxVN)
            }

            val updateMarker: (GeoPoint) -> Unit = { point ->
                mapView.overlays.removeIf { it is Marker }
                val marker = Marker(mapView).apply {
                    position = point
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                mapView.overlays.add(marker)
                mapView.controller.setZoom(17.0) // 🆕 Zoom gần hơn
                mapView.controller.animateTo(point) // 🆕 Animate tới vị trí mới
            }

            locationViewModel.selectedPoint?.let { updateMarker(it) }

            val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {
                        updateMarker(it)
                        locationViewModel.updateSelectedPoint(it)
                        try {
                            val geocoder = Geocoder(context, Locale("vi", "VN"))
                            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                            if (!addresses.isNullOrEmpty()) {
                                locationViewModel.updateSelectedAddress(addresses[0].getAddressLine(0))
                            } else {
                                locationViewModel.updateSelectedAddress("Không tìm thấy địa chỉ")
                            }
                        } catch (e: Exception) {
                            locationViewModel.updateSelectedAddress("Lỗi khi lấy địa chỉ")
                        }
                    }
                    return true
                }

                override fun longPressHelper(p: GeoPoint?): Boolean = false
            })

            mapView.overlays.add(eventsOverlay)

            mapView
        },
        update = { mapView ->
            locationViewModel.selectedPoint?.let { point ->
                mapView.controller.setZoom(17.0)
                mapView.controller.animateTo(point) // 🆕 Animate khi cập nhật từ ViewModel
                mapView.overlays.removeIf { it is Marker }
                val marker = Marker(mapView).apply {
                    position = point
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }
                mapView.overlays.add(marker)
            }
        }
    )
}
