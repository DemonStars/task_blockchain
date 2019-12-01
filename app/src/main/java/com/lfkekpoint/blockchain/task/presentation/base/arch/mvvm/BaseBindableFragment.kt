package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseFragment

abstract class BaseBindableFragment : BaseFragment(), BaseBindableRootView {

    override val controller by lazy { ViewModelProviders.of(this).get(controllerClass) }

    override val baseStateObserver = StateObserver<BaseState>(

        onNextAction = { baseState ->
            when (baseState) {
                is Message -> showMessage(baseState.message)
                is ShowSpinner -> showSpinnerPopup()
                is DismissSpinner -> dismissSpinnerPopup()
                is OnBackPressed -> onBackPressed()
                is OnError -> this@BaseBindableFragment.onError(baseState.error)
            }
        },
        onSubscribeAction = { controller.disposable.add(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeSubject(controller.stateSubject, stateObserver)
        observeSubject(controller.baseStateSubject, baseStateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        binding.setVariable(variableId, controller)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.onViewCreated()
    }
}
