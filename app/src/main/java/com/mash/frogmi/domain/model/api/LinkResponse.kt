package com.mash.frogmi.domain.model.api

data class LinkResponse(
    val prev: String?,
    val next: String?,
    val first: String,
    val last: String,
    val self: String
)
