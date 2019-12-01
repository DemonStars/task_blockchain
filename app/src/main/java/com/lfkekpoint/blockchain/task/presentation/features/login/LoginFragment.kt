package com.lfkekpoint.blockchain.task.presentation.features.login

import com.lfkekpoint.blockchain.task.BR
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseBindableFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.StateObserver
import io.reactivex.Observer

class LoginFragment : BaseBindableFragment() {

    override val layoutId = R.layout.fragment_login
    override val variableId = BR.vmLogin
    override val controllerClass = LoginViewModel::class.java

    override val stateObserver = StateObserver<LoginState>(
        onNextAction = { state ->
                            when (state) {
                                GoMain -> showMessage("GoMain")
                    }
        },
        onSubscribeAction = { controller.disposable.add(it) }
    ) as Observer<Any>
}
