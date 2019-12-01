package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity
import com.lfkekpoint.blockchain.task.presentation.helper.SnackHelper

abstract class BaseBindableActivity : BaseActivity(), BaseBindableRootView {

    override val controller by lazy { ViewModelProviders.of(this).get(controllerClass) }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        val viewModel = ViewModelProviders.of(this as FragmentActivity).get(controller::class.java)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this as Activity, layoutId)
        binding.setVariable(variableId, viewModel)
        binding.lifecycleOwner = this
    }

    override fun showMessage(message: String, lengthIfNeed: Int) {
        SnackHelper().show(this, message, lengthIfNeed)
    }
}


