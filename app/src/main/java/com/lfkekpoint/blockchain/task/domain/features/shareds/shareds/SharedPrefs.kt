package com.lfkekpoint.blockchain.task.domain.features.shareds.shareds

import com.lfkekpoint.blockchain.task.data.base.BasePrefs

object SharedPrefs : BasePrefs() {

    private val PREFS_IS_FIRST_LAUNCH = "PREFS_IS_FIRST_LAUNCH"
    private val PREFS_ACCESS_TOKEN = "PREFS_ACCESS_TOKEN"
    private val PREFS_REFRESH_TOKEN = "PREFS_REFRESH_TOKEN"
    private val PREFS_CONFIRM_CODE = "PREFS_CONFIRM_CODE"
    private val PREFS_API_VERSION = "PREFS_API_VERSION"

    override fun getPrefsAppKey() = "SharedPrefs"

    var isFirstLaunch: Boolean
        set(value) {
            put(PREFS_IS_FIRST_LAUNCH, value)
        }
        get() = getBoolean(PREFS_IS_FIRST_LAUNCH, true)

    object TokenData {

        var confirmCode: String?
            set(value) {
                if (value.isNullOrBlank()) {
                    remove(PREFS_CONFIRM_CODE)
                } else {
                    put(PREFS_CONFIRM_CODE, value)
                }
            }
            get() = getString(PREFS_CONFIRM_CODE)

        var accessToken: String?
            set(value) {
                if (value.isNullOrBlank()) {
                    remove(PREFS_ACCESS_TOKEN)
                } else {
                    put(PREFS_ACCESS_TOKEN, value)
                }
            }
            get() = getString(PREFS_ACCESS_TOKEN)

        var refreshToken: String?
            set(value) {
                if (value.isNullOrBlank()) {
                    remove(PREFS_REFRESH_TOKEN)
                } else {
                    put(PREFS_REFRESH_TOKEN, value)
                }
            }
            get() = getString(PREFS_REFRESH_TOKEN)

        var apiVersion: String?
            set(value) {
                if (value.isNullOrBlank()) {
                    remove(PREFS_API_VERSION)
                } else {
                    put(PREFS_API_VERSION, value)
                }
            }
            get() = getString(PREFS_API_VERSION)
    }
}