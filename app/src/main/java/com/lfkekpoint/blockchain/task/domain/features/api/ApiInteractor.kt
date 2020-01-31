package com.lfkekpoint.blockchain.task.domain.features.api

import com.lfkekpoint.blockchain.task.data.features.api.ApiRepositoryImpl
import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import com.lfkekpoint.blockchain.task.domain.features.api.logout.LogoutReqData
import com.lfkekpoint.blockchain.task.domain.features.api.profile.ProfileEntity
import io.reactivex.subjects.BehaviorSubject

class ApiInteractor {

    private val mRepository = ApiRepositoryImpl()

    fun login(reqData: LoginReqData): BehaviorSubject<Boolean> {
        return mRepository.login(reqData)
    }

    fun logout(): BehaviorSubject<Boolean> {
        return mRepository.logout()
    }

    fun getProfileData(): BehaviorSubject<ProfileEntity> {
        return mRepository.getProfileData()
    }
}