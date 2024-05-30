package com.mash.frogmi.domain.model.api


data class RelationShipResponse(
    val brands: RelationData,
    val zones: RelationData,
)

class RelationData(
    val data: RelationShipData?
)

data class RelationShipData(
    val id: String,
    val type: String
)




