package com.lfkekpoint.blockchain.task.service.retrofit

class ApiClient<T>(methodsInterfaceClass: Class<T>) {

    private val retrofit = RetrofitInstance.retrofit
    private var mApiMethods: T = retrofit.create(methodsInterfaceClass)

    fun getService(): T = mApiMethods
}