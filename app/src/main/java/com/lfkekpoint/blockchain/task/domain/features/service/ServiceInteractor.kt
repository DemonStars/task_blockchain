package com.lfkekpoint.blockchain.task.domain.features.service

import android.content.Context
import com.lfkekpoint.blockchain.task.service.NetworkManager
import io.reactivex.subjects.BehaviorSubject

class ServiceInteractor {

    private val mController = NetworkManager()

    fun registerNetworkListener(context: Context): BehaviorSubject<Boolean> {
        return mController.registerConnectListener(context)
    }

    fun unRegisterNetworkListener(context: Context, subject: BehaviorSubject<Boolean>) {
        mController.unregisterConnectListener(context, subject)
    }

    fun isInternetAvailable(context: Context): Boolean {
        return mController.isInternetAvailable(context)
    }

    fun <T> getApiService(methodsInterfaceClass: Class<T>): T {
        return mController.getApiService(methodsInterfaceClass)
    }

    fun getDatabaseClient() {
        //todo
    }
}