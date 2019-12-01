package com.lfkekpoint.blockchain.task.presentation.helper

import android.util.Log
import com.lfkekpoint.blockchain.task.BuildConfig

object LogHelper {

    private val LOG_TAG = "LOGG_____-------"

    fun i(logMessage: String?, tag: String? = LOG_TAG) {

        if (BuildConfig.DEBUG) {
            Log.i(tag, logMessage ?: "NO DATA =((")
        }
    }

    fun e(logMessage: String?, tag: String? = LOG_TAG) {

        if (BuildConfig.DEBUG) {
            Log.e(tag, logMessage ?: "NO DATA =((")
        }
    }

    fun logInAllBuilds(logMessage: String?, tag: String? = LOG_TAG) {
        Log.i(tag, logMessage ?: "empty log string")
    }
}