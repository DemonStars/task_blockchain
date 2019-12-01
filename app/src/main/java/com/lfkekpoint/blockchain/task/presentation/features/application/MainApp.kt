package com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application

import android.app.Application
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState.appContext

class MainApp : Application() {

    private var mCurrentActivity: BaseActivity? = null

    override fun onCreate() {
        super.onCreate()

        appContext = this

    }

    fun getCurrentActivity(): BaseActivity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: BaseActivity?) {
        this.mCurrentActivity = mCurrentActivity
    }
}
