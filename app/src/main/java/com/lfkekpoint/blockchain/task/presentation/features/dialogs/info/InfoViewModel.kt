package com.lfkekpoint.blockchain.task.presentation.features.dialogs.info

import android.view.View
import androidx.databinding.ObservableField
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseViewModel

class InfoViewModel: BaseViewModel() {

    val title = ObservableField("")

    fun onClickOk(view: View) {
        stateSubject.onNext(InfoOkPressed)
    }
}