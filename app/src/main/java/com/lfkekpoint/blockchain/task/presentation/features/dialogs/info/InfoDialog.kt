package com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.dialogs.info

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.lfkekpoint.blockchain.task.BR
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseBindableDialogFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.StateObserver
import com.lfkekpoint.blockchain.task.presentation.features.dialogs.info.InfoOkPressed
import com.lfkekpoint.blockchain.task.presentation.features.dialogs.info.InfoState
import com.lfkekpoint.blockchain.task.presentation.features.dialogs.info.InfoViewModel
import io.reactivex.Observer

class InfoDialog : BaseBindableDialogFragment() {

    override val controllerClass = InfoViewModel::class.java
    override val variableId = BR.dialogInfo
    override val layoutId = R.layout.dialog_info
    override val stateObserver = StateObserver<InfoState>(

            onNextAction = { state ->

                dismiss()

                when (state) {
                    is InfoOkPressed -> {
                        targetFragment?.onActivityResult(targetRequestCode, RESULT_OK, Intent())
                        dismiss()
                    }
                }
            },

            onSubscribeAction = { controller.disposable.add(it) }

    ) as Observer<Any>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (controller as InfoViewModel).title.set(arguments?.getString(EXTRA_INFO_TITLE))
    }

    companion object {
        val EXTRA_INFO_TITLE = "EXTRA_INFO_TITLE"
    }
}