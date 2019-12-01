package com.lfkekpoint.blockchain.task.presentation.base.arch.interfaces

import androidx.lifecycle.LifecycleObserver

interface BaseRootView : IBaseView {
    val layoutId: Int
    val controllerClass: Class<out LifecycleObserver>?
    val controller: LifecycleObserver?
}