package com.lfkekpoint.blockchain.task.service.retrofit

import com.auth0.android.jwt.JWT
import com.google.gson.GsonBuilder
import com.lfkekpoint.blockchain.task.BuildConfig
import com.lfkekpoint.blockchain.task.common.Const
import com.lfkekpoint.blockchain.task.domain.features.shareds.shareds.SharedPrefs.TokenData.accessToken
import com.lfkekpoint.blockchain.task.presentation.helper.LogHelper
import com.lfkekpoint.blockchain.task.service.retrofit.RetrofitInstance.JwtSyncState.SYNCHRONIZED
import com.lfkekpoint.blockchain.task.service.retrofit.RetrofitInstance.JwtSyncState.WITHOUT_TOKENS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object RetrofitInstance {

    private var mRetryCount = 0

    @Volatile
    private var mJwtSyncState = if (accessToken == null) WITHOUT_TOKENS else SYNCHRONIZED

    val retrofit: Retrofit = getInstance()

    private fun getInstance(): Retrofit {

        val okBuilder = OkHttpClient.Builder()

        addJwtInterceptorToBuilder(okBuilder)
        addJwtInterceptorListenerToBuilder(okBuilder)
//        addErrorIntercaptor(okBuilder)

//        if (BuildConfig.DEBUG) {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//
//            okBuilder.addInterceptor(interceptor)
//        }

        val client = okBuilder.build()

        val gsonBuilder =
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .serializeNulls()
                    .create()
            )

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(gsonBuilder)
            .build()
    }

//    private fun addAuthentificatorToBuilder(okBuilder: OkHttpClient.Builder) {
//        okBuilder.authenticator { route, response ->
//            if (response.code() == 401) {
//                val refreshResponse = ApiInteractor()
//                    .getApiService(AuthApiMethods::class.java)
//                    .refreshToken(AuthRefreshReqData(refreshToken!!))
//                    .execute()
//
//                if (response.isSuccessful) {
//
//                    val body = refreshResponse.body()
//                    accessToken = body?.data?.accessToken
//                    refreshToken = body?.data?.refreshToken
//
//                    return@authenticator response.request().newBuilder()
//                        .header(JWT_HEADER, "$BEARER_PREFFIX$accessToken")
//                        .build()
//                } else {
//                    return@authenticator null
//                }
//            } else return@authenticator null
//
//        }
//    }

    private fun addJwtInterceptorToBuilder(okBuilder: OkHttpClient.Builder) {

        okBuilder.addInterceptor { chain ->

//            if (accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true) {
//
//                if (isJwtExpired()) {
//                    return@addInterceptor requestAccessToken(chain)
//                } else {
//                    return@addInterceptor requestWithAccessToken(chain)
//                }
//            } else {
//                mJwtSyncState = WITHOUT_TOKENS

                return@addInterceptor chain.proceed(chain.request())
//            }
        }
    }

    private fun isJwtExpired(): Boolean {
        val jwt = JWT(accessToken!!)
        return (jwt.expiresAt?.time ?: 0) - Date().time < 30 * 1000
    }

//    @Synchronized
//    private fun requestAccessToken(chain: Interceptor.Chain): Response {
//
//        mJwtSyncState = WAITING
//
//        accessToken = null
//
//        val response = ApiInteractor()
//            .getApiService(AuthApiMethods::class.java)
//            .refreshToken(AuthRefreshReqData(refreshToken!!))
//            .execute()
//
//        mRetryCount++
//
//        return when (response.isSuccessful) {
//
//            true -> {
//                val body = response.body()
//                accessToken = body?.data?.accessToken
//                refreshToken = body?.data?.refreshToken
//
//                mRetryCount = 0
//
//                requestWithAccessToken(chain)
//            }
//
//            else -> {
//                mJwtSyncState = WITHOUT_TOKENS
//                (appContext as MainApp).getCurrentActivity()?.logout()
//                chain.proceed(chain.request())
//            }
//        }
//    }

    private fun requestWithAccessToken(chain: Interceptor.Chain): Response {

        mJwtSyncState = SYNCHRONIZED

        val original = chain.request()
        val builder = original.newBuilder()
            .method(original.method(), original.body())

        if (accessToken.isNullOrBlank().not()) {
            builder.header(Const.JWT_HEADER, "${Const.BEARER_PREFFIX}$accessToken")
        }

        val request = builder.build()

        return chain.proceed(request)
    }

    private fun addJwtInterceptorListenerToBuilder(okBuilder: OkHttpClient.Builder) {

        okBuilder.addInterceptor { chain ->
            returnChainWhenNeed(chain)
        }
    }

//    private fun addErrorIntercaptor(okBuilder: OkHttpClient.Builder) {
//
//        okBuilder.addInterceptor { chain ->
//            val response: Response = chain.proceed(chain.request())
//
//            if (response.isSuccessful.not()) {
//                val stringBody = response.body()?.string()
//                val entity = GsonBuilder().create().fromJson<ApiResponseError>(stringBody, ApiResponseError::class.java)
//                entity.error?.message.let {
//                    if (response.code() != 401) {
//                        throw ApiMessageException(it!!, entity?.error?.code)
//                    }
//                }
//            }
//
//            response
//        }
//    }

    private fun returnChainWhenNeed(chain: Interceptor.Chain): Response {

        mRetryCount++

        LogHelper.i("retryCount: $mRetryCount", "returnChainWhenNeed")

        Thread.sleep(500L) //fixme - переделать же некрасиво ну

        return when (mJwtSyncState) {
            WITHOUT_TOKENS, SYNCHRONIZED -> chain.proceed(chain.request())
            else -> returnChainWhenNeed(chain)
        }
    }

    private enum class JwtSyncState {
        WITHOUT_TOKENS,
        WAITING,
        SYNCHRONIZED
    }
}