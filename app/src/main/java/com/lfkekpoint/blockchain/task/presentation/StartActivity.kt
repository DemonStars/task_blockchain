package com.lfkekpoint.blockchain.task.presentation

import android.os.Bundle
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.extensions.openMainActivity
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity

class StartActivity: BaseActivity() {

    override val layoutId = R.layout.base_fragment_container
    override val controllerClass = null
    override val controller = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        openMainActivity()
        finish()
    }
}