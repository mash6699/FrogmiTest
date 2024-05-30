package com.mash.frogmi.domain.model.api

import com.squareup.moshi.Json

data class AttributeResponse(
    val name: String,
    val code: String,
    val active: Boolean,
    @Json(name = "full_address")
    val fullAddress: String,
    val coordinates: Coordinates,
    @Json(name = "created_at")
    val createdAt: String,
)

data class Coordinates(
    val latitude: Double?,
    val longitude: Double?
)