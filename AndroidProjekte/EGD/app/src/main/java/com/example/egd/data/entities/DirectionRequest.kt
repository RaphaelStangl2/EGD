package com.example.egd.data.entities

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json

data class DirectionsResponse(
    @Json(name = "routes") val routes: List<Route>
)

data class Route(
    @Json(name = "legs") val legs: List<Leg>
)

data class Leg(
    @Json(name = "distance") val distance: Distance
)

data class Distance(
    @Json(name = "value") val value: Int // Represents the distance in meters
)
