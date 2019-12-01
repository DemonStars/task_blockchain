package com.lfkekpoint.blockchain.task.service

import android.content.Context
import com.lfkekpoint.blockchain.task.service.network.NetworkController
import com.lfkekpoint.blockchain.task.service.retrofit.ApiClient
import io.reactivex.subjects.BehaviorSubject

class NetworkManager {

    fun registerConnectListener(context: Context): BehaviorSubject<Boolean> {
        return NetworkController.addConnectListener(context)
    }

    fun unregisterConnectListener(context: Context, subject: BehaviorSubject<Boolean>) {
        NetworkController.removeConnectListener(context, subject)
    }

    fun isInternetAvailable(context: Context): Boolean {
        return NetworkController.isInternetAvailable(context)
    }

    fun <T> getApiService(methodsInterfaceClass: Class<T>): T {
        return ApiClient(methodsInterfaceClass).getService()
    }
}