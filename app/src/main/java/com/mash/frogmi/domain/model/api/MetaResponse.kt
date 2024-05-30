package com.mash.frogmi.domain.model.api

import com.squareup.moshi.Json
import kotlin.math.roundToInt

data class MetaResponse(
    val pagination: PaginationData
)

data class PaginationData(
    @Json(name = "current_page")
    val currentPage: Int,
    val total: Int,
    @Json(name = "per_page")
    val perPage: Int
) {
    fun totalPages(): Int {
        val rem = total % perPage
        return if (rem == 0) {
            total.div(perPage)
        } else {
            total.toDouble().div(perPage).roundToInt()
        }
    }
}
