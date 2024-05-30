package com.mash.frogmi.domain.model

data class Store(
    val name: String,
    val code: String,
    val active: Boolean,
    val fullAddress: String
)

data class StoreResponse(
    val stores: List<Store>,
    val linkNext: String?
)