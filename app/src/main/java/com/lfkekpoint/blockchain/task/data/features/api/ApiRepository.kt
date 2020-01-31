package com.lfkekpoint.blockchain.task.data.features.api

import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import com.lfkekpoint.blockchain.task.domain.features.api.logout.LogoutReqData
import com.lfkekpoint.blockchain.task.domain.features.api.profile.ProfileEntity
import io.reactivex.subjects.BehaviorSubject

interface ApiRepository {
    fun login(reqData: LoginReqData): BehaviorSubject<Boolean>
    fun logout(): BehaviorSubject<Boolean>
    fun getProfileData(): BehaviorSubject<ProfileEntity>
}