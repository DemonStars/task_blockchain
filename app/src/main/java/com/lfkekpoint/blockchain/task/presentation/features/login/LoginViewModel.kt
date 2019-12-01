package com.lfkekpoint.blockchain.task.presentation.features.login

import android.view.View
import androidx.databinding.ObservableField
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.domain.features.api.ApiInteractor
import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseViewModel
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.Message
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.OnError
import com.lfkekpoint.blockchain.task.presentation.helper.ResHelper.getString

class LoginViewModel : BaseViewModel() {

    val login = ObservableField("")
    val password = ObservableField("")

    fun onClickFill(view: View) {
        login.set("hello@karta.com")
        password.set("12345678")
    }

    fun onClickLogin(view: View) {

        if (login.get().isNullOrBlank() && password.get().isNullOrBlank()) {
            baseStateSubject.onNext(Message(getString(R.string.valid_fill_both_pass_login)))
        } else {
            disposable.add(applyStandard(ApiInteractor().login(LoginReqData(login.get()!!, password.get()!!)))
                .subscribe(
                    { allOk ->
                        if (allOk) {
                            stateSubject.onNext(GoMain)
                        }
                    },
                    { baseStateSubject.onNext(OnError(it)) }
                ))
        }
    }
}
