package com.mash.frogmi.domain.model.api

import com.squareup.moshi.Json

data class DataResponse(
    val id: String,
    val type: String,
    val attributes: AttributeResponse,
    val links: LinkData,
    @Json(name = "relationships")
    val relationShips: RelationShipResponse,
)

data class LinkData(
    val self: String
)

