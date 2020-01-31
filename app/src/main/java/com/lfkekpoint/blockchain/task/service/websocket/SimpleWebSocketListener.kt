package com.lfkekpoint.blockchain.task.service.websocket

import com.lfkekpoint.blockchain.task.presentation.helper.i
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SimpleWebSocketListener: WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        "onOpen".i("WebSocketListener")
        super.onOpen(webSocket, response)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        "onFailure".i("WebSocketListener")
        super.onFailure(webSocket, t, response)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        "onClosing".i("WebSocketListener")
        super.onClosing(webSocket, code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        "onMessage".i("WebSocketListener")
        super.onMessage(webSocket, text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        "onMessage".i("WebSocketListener")
        super.onMessage(webSocket, bytes)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        "onClosed".i("WebSocketListener")
        super.onClosed(webSocket, code, reason)
    }
}