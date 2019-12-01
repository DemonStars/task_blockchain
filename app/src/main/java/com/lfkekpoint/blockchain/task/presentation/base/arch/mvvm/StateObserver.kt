package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class StateObserver<T>(val onNextAction: (state: T) -> Unit, val onSubscribeAction: (d: Disposable) -> Unit) : Observer<T> {

    override fun onNext(t: T) { onNextAction.invoke(t) }
    override fun onSubscribe(d: Disposable) { onSubscribeAction.invoke(d) }
    override fun onComplete() {}
    override fun onError(e: Throwable) {}
}
