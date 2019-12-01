package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import io.reactivex.Observable

interface BaseViewModelMethods {
    fun onViewCreated()
    fun <F> applyStandard(
        observable: Observable<F>,
        needDismiss: Boolean = true,
        needShowSpinner: Boolean = true
    ): Observable<F>

    fun handleResourceErrors(errorMessage: String?)
}