package com.example.android_bootcamp.presentation.screen.store_location

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MapClusterItem(
    val name: String?,
    val latitude: Double,
    val longitude: Double,
    val address: String
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)

    override fun getTitle(): String? = name

    override fun getSnippet(): String? = null
}