package com.lfkekpoint.blockchain.task.service.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import io.reactivex.subjects.BehaviorSubject

class NetworkChangeReceiver : BroadcastReceiver() {

    private val mListeners = ArrayList<BehaviorSubject<Boolean>>()

    var context: Context? = null
        private set

    override fun onReceive(context: Context, intent: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        activeNetwork?.let {
            when (activeNetwork.type) {
                ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE ->
                    mListeners.forEach { it.onNext(true) }
                else ->
                    mListeners.forEach { it.onNext(false) }
            }
        }
            ?: mListeners.forEach { it.onNext(false) }
    }

    fun registerItSelf(context: Context, subject: BehaviorSubject<Boolean>) {

        this.context = context
        mListeners.add(subject)

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")

        context.registerReceiver(this, intentFilter)
    }

    fun unregister(context: Context?) {

        try {
            context?.unregisterReceiver(this)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    fun addSubject(subject: BehaviorSubject<Boolean>) {
        mListeners.add(subject)
    }

    fun removeSubject(context: Context, subject: BehaviorSubject<Boolean>) {
        mListeners.remove(subject)
        if (mListeners.size == 0) {
            unregister(context)
        }
    }
}