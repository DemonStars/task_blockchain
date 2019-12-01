package com.lfkekpoint.blockchain.task.domain.features.api

import com.lfkekpoint.blockchain.task.data.features.api.ApiRepositoryImpl
import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import io.reactivex.subjects.BehaviorSubject

class ApiInteractor {

    private val mRepository = ApiRepositoryImpl()

    fun login(reqData: LoginReqData): BehaviorSubject<Boolean> {
        return mRepository.login(reqData)
    }
}