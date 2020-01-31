package com.lfkekpoint.blockchain.task.service.retrofit

import com.auth0.android.jwt.JWT
import com.google.gson.GsonBuilder
import com.lfkekpoint.blockchain.task.BuildConfig
import com.lfkekpoint.blockchain.task.common.Const
import com.lfkekpoint.blockchain.task.data.features.api.ApiMethods
import com.lfkekpoint.blockchain.task.domain.features.api.refresh.AuthRefreshReqData
import com.lfkekpoint.blockchain.task.domain.features.service.ServiceInteractor
import com.lfkekpoint.blockchain.task.domain.features.shareds.shareds.SharedPrefs.TokenData.accessToken
import com.lfkekpoint.blockchain.task.domain.features.shareds.shareds.SharedPrefs.TokenData.refreshToken
import com.lfkekpoint.blockchain.task.presentation.helper.i
import com.lfkekpoint.blockchain.task.service.retrofit.RetrofitInstance.JwtSyncState.SYNCHRONIZED
import com.lfkekpoint.blockchain.task.service.retrofit.RetrofitInstance.JwtSyncState.WITHOUT_TOKENS
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.MainApp
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState.appContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitInstance {

    private var mRetryCount = 0

    @Volatile
    private var mJwtSyncState = if (accessToken == null) WITHOUT_TOKENS else SYNCHRONIZED

    val retrofit: Retrofit = getInstance()

    private fun getInstance(): Retrofit {

        val okBuilder = OkHttpClient.Builder()

        addJwtInterceptorToBuilder(okBuilder)
        addJwtInterceptorListenerToBuilder(okBuilder)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okBuilder.addInterceptor(interceptor)
            trustAllCertificates(okBuilder)
        }

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

    private fun trustAllCertificates(builder: OkHttpClient.Builder) {
        val trustManagers = provideTrustManagers()
        val provideSslSocketFactory = provideSslSocketFactory(trustManagers)

        provideSslSocketFactory?.let { builder.sslSocketFactory(it, trustManagers[0] as X509TrustManager) }
    }

    private fun provideSslSocketFactory(trustManagers: Array<TrustManager>): SSLSocketFactory? {
        return try {
            val trustAllSslContext = SSLContext.getInstance("SSL")
            trustAllSslContext.init(null, trustManagers, java.security.SecureRandom())
            trustAllSslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun provideTrustManagers(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
    }


    private fun addJwtInterceptorToBuilder(okBuilder: OkHttpClient.Builder) {

        okBuilder.addInterceptor { chain ->

            if (accessToken?.isNotBlank() == true && refreshToken?.isNotBlank() == true) {

                if (isJwtExpired()) {
                    return@addInterceptor requestAccessToken(chain)
                } else {
                    return@addInterceptor requestWithAccessToken(chain)
                }
            } else {
                mJwtSyncState = WITHOUT_TOKENS

                return@addInterceptor chain.proceed(chain.request())
            }
        }
    }

    private fun isJwtExpired(): Boolean {
        val jwt = JWT(accessToken!!)
        return (jwt.expiresAt?.time ?: 0) - Date().time < 30 * 1000
    }

    @Synchronized
    private fun requestAccessToken(chain: Interceptor.Chain): Response {

        mJwtSyncState = JwtSyncState.WAITING

        accessToken = null

        val response = ServiceInteractor()
            .getApiService(ApiMethods::class.java)
            .refreshToken(AuthRefreshReqData(refreshToken!!))
            .execute()

        mRetryCount++

        return when (response.isSuccessful) {

            true -> {
                val body = response.body()
                accessToken = body?.accessToken
                refreshToken = body?.refreshToken

                mRetryCount = 0

                requestWithAccessToken(chain)
            }

            else -> {
                mJwtSyncState = WITHOUT_TOKENS
                (appContext as MainApp).getCurrentActivity()?.logout()
                chain.proceed(chain.request())
            }
        }
    }

    private fun requestWithAccessToken(chain: Interceptor.Chain): Response {

        mJwtSyncState = SYNCHRONIZED

        val original = chain.request()
        val builder = original.newBuilder()
            .method(original.method, original.body)

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

    private fun returnChainWhenNeed(chain: Interceptor.Chain): Response {

        mRetryCount++

        "retryCount: $mRetryCount".i("returnChainWhenNeed")

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