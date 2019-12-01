package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : LifecycleObserver, ViewModel(), BaseViewModelMethods {

    val stateSubject = BehaviorSubject.create<Any>()
    val baseStateSubject = BehaviorSubject.create<BaseState>()
    val disposable = CompositeDisposable()

    override fun onViewCreated() {

    }

    override fun <F> applyStandard(
        observable: Observable<F>,
        needDismiss: Boolean,
        needShowSpinner: Boolean
    ): Observable<F> {

        return observable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                if (needDismiss) {
                    baseStateSubject.onNext(ShowSpinner)
                }
            }
            .doOnNext {
                if (needDismiss) {
                    baseStateSubject.onNext(DismissSpinner)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

    override fun handleResourceErrors(errorMessage: String?) {
        baseStateSubject.onNext(Message(errorMessage ?: "Что то пошло не так..."))
    }

}
