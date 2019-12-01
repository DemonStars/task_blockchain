package com.lfkekpoint.blockchain.task.presentation.base.state

import android.os.Bundle
import androidx.lifecycle.Observer

class StateResHandleObserver<T>(
    private val handleAction: (state: T, data: Bundle?) -> Unit
) : Observer<StateResource<T>> {

    override fun onChanged(stateRes: StateResource<T>?) {
        stateRes?.state.let { handleAction.invoke(stateRes?.state!!, stateRes.data) }
    }
}