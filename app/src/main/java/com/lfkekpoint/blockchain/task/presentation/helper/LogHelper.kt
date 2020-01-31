package com.lfkekpoint.blockchain.task.presentation.helper

import android.util.Log
import com.lfkekpoint.blockchain.task.BuildConfig

private val LOG_TAG = "LOGG_____-------"

fun String?.i(tag: String? = LOG_TAG) {
    if (BuildConfig.DEBUG) {
        Log.i(tag, this ?: "NO DATA =((")
    }
}

fun String?.e(tag: String? = LOG_TAG) {

    if (BuildConfig.DEBUG) {
        Log.e(tag, this ?: "NO DATA =((")
    }
}