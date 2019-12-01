package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import com.lfkekpoint.blockchain.task.presentation.base.arch.interfaces.BaseRootView
import io.reactivex.Observer

interface BaseBindableRootView : BaseRootView {

    override val controller: BaseViewModel
    override val controllerClass: Class<out BaseViewModel>

    val variableId: Int
    val stateObserver: Observer<Any>
    val baseStateObserver: Observer<BaseState>
}