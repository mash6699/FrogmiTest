package com.mash.frogmi

import android.content.res.Resources
import androidx.annotation.StringRes

interface StringResourceProvider {
    fun getString(@StringRes resourceId: Int): String
    fun getString(@StringRes resourceId: Int, parameter: String): String
}

class StringResourceProviderImpl(private val resource: Resources): StringResourceProvider {
    override fun getString(resourceId: Int): String =
        resource.getString(resourceId)

    override fun getString(resourceId: Int, parameter: String): String =
        resource.getString(resourceId, parameter)

}