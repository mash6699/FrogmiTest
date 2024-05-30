package com.mash.frogmi.domain.mapper

import com.mash.frogmi.domain.model.Store
import com.mash.frogmi.domain.model.api.DataResponse

fun DataResponse.toStore() = Store(
    name = this.attributes.name,
    code = this.attributes.code,
    fullAddress = this.attributes.code,
    active = this.attributes.active,
)