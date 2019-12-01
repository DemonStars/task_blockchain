package com.lfkekpoint.blockchain.task.presentation.helper

import android.widget.Toast
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState

class ToastHelper {
    fun showToast(message: String?, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(AppState.appContext, message, duration).show()
    }
}