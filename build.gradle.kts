// Top-level build file where you can add configuration options common to all sub-projects/modules.
//@Suppress("DSL_SCOPE_VIOLATION")
@file:Suppress("DSL_SCOPE_VIOLATION")
plugins {
    //id("com.android.application") version "8.1.0" apply false
    alias(libs.plugins.com.android.application) apply false
    //id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    id("com.android.library") version "8.1.3" apply false //
    id("com.google.dagger.hilt.android") version "2.50" apply false
}