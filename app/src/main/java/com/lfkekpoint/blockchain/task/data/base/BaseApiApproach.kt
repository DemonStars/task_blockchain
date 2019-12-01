package com.lfkekpoint.blockchain.task.data.base

import android.annotation.SuppressLint
import com.google.gson.JsonSyntaxException
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.domain.features.service.ApiInteractor
import com.lfkekpoint.blockchain.task.presentation.helper.ResHelper.getString
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState.appContext
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import retrofit2.HttpException
import com.lfkekpoint.blockchain.task.domain.base.ApiMessageException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseApiMapper<Response, Entity> {
    abstract fun mapToEntity(response: Response): Entity
}

@SuppressLint("CheckResult")
fun Subject<*>.requestApiWithNetworkListener(
    apiIteractor: ApiInteractor,
    actionRequest: () -> Unit
) {
    if (apiIteractor.isInternetAvailable(appContext)) {

        actionRequest.invoke()
    } else {

        //todo do we need subject here? Maybe Observable??
        val networkSubject = apiIteractor.registerNetworkListener(appContext)

        networkSubject.subscribe { connected ->
            if (connected) {

                actionRequest.invoke()

                apiIteractor.unRegisterNetworkListener(appContext, networkSubject)
            }
        }
    }
}


@SuppressLint("CheckResult")
fun <Response, Entity> Observable<Response>.subscribeBaseResponseOnEntitySubject(
    subject: Subject<Entity>,
    mapAction: (response: Response) -> Entity
) {
    this.subscribe(
        {
            it?.let { response -> subject.onNext(mapAction.invoke(response)) }
        },
        {
            subject.handleRetrofitErrors(it)
        }
    )
}

@SuppressLint("CheckResult")
fun <Entity> Completable.subscribeCompletableOnEntitySubject(
    subject: Subject<Entity>,
    mapAction: () -> Entity
) {
    this.subscribe(
        {
            subject.onNext(mapAction.invoke())
        },
        {
            subject.handleRetrofitErrors(it)
        }
    )
}

private fun Subject<*>.handleRetrofitErrors(e: Throwable) {
    when (e) {
        is SocketTimeoutException -> this.onError(ApiMessageException (getString(R.string.error_ping_timeout), 404))
        is JsonSyntaxException -> this.onError(ApiMessageException (getString(R.string.server_answer_wrong), 404))
        is HttpException -> this.onError(ApiMessageException (getString(R.string.error_server), 404))
        is UnknownHostException -> this.onError(ApiMessageException (getString(R.string.error_no_connect), 404))
        is KotlinNullPointerException -> this.onError(ApiMessageException (getString(R.string.error_knpe), -1))
        else -> this.onError(e)
    }
}