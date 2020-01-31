package com.lfkekpoint.blockchain.task.presentation.features.main

import com.lfkekpoint.blockchain.task.BR
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseBindableFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.StateObserver
import io.reactivex.Observer

class MainFragment: BaseBindableFragment() {

    override val layoutId = R.layout.fragment_main
    override val variableId = BR.vmMain
    override val controllerClass = MainViewModel::class.java

    override val stateObserver = StateObserver<MainState>(
    onNextAction = { state ->
        when (state) {
        }
    },
    onSubscribeAction = { controller.disposable.add(it) }
    ) as Observer<Any>
}