package com.lfkekpoint.blockchain.task.service.network

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.subjects.BehaviorSubject

object NetworkController {

    private val mReceivers = ArrayList<NetworkChangeReceiver>()

    fun addConnectListener(context: Context): BehaviorSubject<Boolean> {
        return tryAddSubjectToReceiver(context)
    }

    fun removeConnectListener(context: Context, subject: BehaviorSubject<Boolean>) {
        stopBroadcastIfNeed(context, subject)
    }

    private fun tryAddSubjectToReceiver(context: Context): BehaviorSubject<Boolean> {
        val receiver = mReceivers.find { it.context == context }

        val subject = BehaviorSubject.create<Boolean>()

        receiver?.let { it.addSubject(subject) } ?: startNewReceiver(context, subject)

        return  subject
    }

    private fun startNewReceiver(context: Context, subject: BehaviorSubject<Boolean>) {
        val receiver = NetworkChangeReceiver()
        receiver.registerItSelf(context, subject)
        mReceivers.add(receiver)
    }

    private fun stopBroadcastIfNeed(context: Context, subject: BehaviorSubject<Boolean>) {
        val receiver = mReceivers.find { it.context == context }
        receiver?.let { it.removeSubject(context, subject) }
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
