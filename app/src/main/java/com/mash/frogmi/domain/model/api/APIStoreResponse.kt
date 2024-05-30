package com.mash.frogmi.domain.model.api


data class APIStoreResponse(
    val data: List<DataResponse>,
    val meta: MetaResponse,
    val links: LinkResponse
)
