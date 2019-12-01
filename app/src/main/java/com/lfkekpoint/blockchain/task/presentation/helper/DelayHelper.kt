package com.lfkekpoint.blockchain.task.presentation.helper

import android.os.Handler

class DelayHelper {

    private val DEFAULT_DELAY = 1000L
    fun postWithDelay(action: (() -> Unit), delay: Long = DEFAULT_DELAY) {
        Handler().postDelayed({ action.invoke() }, delay)
    }
}