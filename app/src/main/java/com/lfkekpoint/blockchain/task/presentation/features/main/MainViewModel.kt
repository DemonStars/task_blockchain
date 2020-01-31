package com.lfkekpoint.blockchain.task.presentation.features.main

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.lfkekpoint.blockchain.task.domain.features.api.ApiInteractor
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseViewModel
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.Logout
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.OnError
import com.lfkekpoint.blockchain.task.presentation.helper.i
import io.reactivex.Observable
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import java.util.*


class MainViewModel : BaseViewModel() {

    private val client = OkHttpClient()
    val interactor = ApiInteractor()

    fun onClickLogout(view: View) {
        disposable.add(
            applyStandard(interactor.logout())
                .subscribe(
                    { baseStateSubject.onNext(Logout) },
                    { baseStateSubject.onNext(OnError(it)) })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        val request = Request.Builder().url("wss://ws.blockchain.info/inv").build()
        val listener = EchoWebSocketListener()
        val ws = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }


    private inner class EchoWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            webSocket.send("Hello, it's SSaurel !")
            webSocket.send("What's up ?")
            webSocket.send("deadbeef".decodeHex())
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
//            output("Receiving : " + text!!)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//            output("Receiving bytes : " + bytes.hex())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null)
//            output("Closing : $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {

        }
    }

    companion object {
        private val NORMAL_CLOSURE_STATUS = 1000
    }
}