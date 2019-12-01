package com.lfkekpoint.blockchain.task.data.features.api

import com.lfkekpoint.blockchain.task.data.base.requestApiWithNetworkListener
import com.lfkekpoint.blockchain.task.data.base.subscribeBaseResponseOnEntitySubject
import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import com.lfkekpoint.blockchain.task.domain.features.service.ServiceInteractor
import com.lfkekpoint.blockchain.task.domain.features.shareds.shareds.SharedPrefs.TokenData.accessToken
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class ApiRepositoryImpl : ApiRepository {

    val serviceInteractor = ServiceInteractor()
    val mapper = ApiDataMapper()
    val apiService = serviceInteractor.getApiService(ApiMethods::class.java)

    override fun login(reqData: LoginReqData): BehaviorSubject<Boolean> {

        val subject = BehaviorSubject.create<Boolean>()
        subject.requestApiWithNetworkListener(serviceInteractor) {
            val apiObservable = loginFromApi(reqData)
            apiObservable.subscribeBaseResponseOnEntitySubject(subject) { response ->
                response.token?.let {
                    accessToken = it
                    true
                } ?: false
            }
        }
        return subject
    }

    private fun loginFromApi(reqData: LoginReqData) = apiService
        .login(reqData)
        .subscribeOn(Schedulers.io())
}