package com.lfkekpoint.blockchain.task.presentation.base.arch.interfaces

import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject

interface IBaseView {
    fun showMessage(message: String, lengthIfNeed: Int = Snackbar.LENGTH_LONG)
    fun <T> observeSubject(subject: BehaviorSubject<T>, observer: Observer<T>)
    fun onError(e: Throwable)
    fun onBackPressed()

    fun showSpinnerPopup()
    fun dismissSpinnerPopup()
}