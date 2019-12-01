package com.lfkekpoint.blockchain.task.data.features.api

import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import io.reactivex.subjects.BehaviorSubject

interface ApiRepository {
    fun login(reqData: LoginReqData): BehaviorSubject<Boolean>
}