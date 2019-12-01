package com.lfkekpoint.blockchain.task.data.base

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.common.Const.UNDEFINED_BOOLEAN
import com.lfkekpoint.blockchain.task.common.Const.UNDEFINED_LONG
import com.lfkekpoint.blockchain.task.common.Const.UNDEFINED_STRING
import com.lfkekpoint.blockchain.task.presentation.helper.ResHelper
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState.appContext
import java.io.Serializable

open class BasePrefs {

    private val mPrefs: SharedPreferences by lazy {
        initPrefs()
    }

    private fun initPrefs(): SharedPreferences {
        return appContext.getSharedPreferences(getPrefsAppKey(), Context.MODE_PRIVATE)
    }

    open protected fun getPrefsAppKey(): String {
        return ResHelper.getString(R.string.app_name)
    }

    protected fun remove(key: String) {
        mPrefs.edit().remove(key).apply()
    }

    protected fun put(key: String, value: String) {
        mPrefs.edit().putString(key, value)
                .apply()
    }

    protected fun put(key: String, value: Boolean) {
        mPrefs.edit().putBoolean(key, value)
                .apply()
    }

    protected fun put(key: String, value: Int) {
        mPrefs.edit().putInt(key, value)
                .apply()
    }

    protected fun put(key: String, value: Long) {
        mPrefs.edit().putLong(key, value)
                .apply()
    }

    protected fun put(key: String, value: Set<String>) {
        mPrefs.edit().putStringSet(key, value)
                .apply()
    }

    protected fun put(key: String, value: Double) {
        mPrefs.edit().putLong(key, value.toRawBits())
                .apply()
    }

    protected fun put(key: String, value: Serializable) {
        val gson = Gson()
        val json = gson.toJson(value)

        put(key, json)
    }

    protected fun put(key: String, value: ArrayList<Serializable>) {
        val gson = Gson()
        val json = gson.toJson(value)

        put(key, json)
    }

    protected fun getString(key: String, value: String = ""): String? {
        return mPrefs.getString(key, value)
    }


    protected inline fun <reified T : Serializable> getSerializable(key: String, value: String = ""): T? {
        val json = getString(key, value)
        val gson = Gson()

        return gson.fromJson(json, T::class.java)
    }

    protected fun <T : Serializable> getArrayListOfSerializable(key: String): ArrayList<T>? {
        val jsonString = getString(key)
        val type = object : TypeToken<ArrayList<T>>() {}.type

        val gson = Gson()

        val listOfSerializable = gson.fromJson<ArrayList<T>>(jsonString, type)

        return listOfSerializable?.let { ArrayList(it) }
    }

    protected fun getBoolean(key: String, value: Boolean = UNDEFINED_BOOLEAN): Boolean {
        return mPrefs.getBoolean(key, value)
    }

    protected fun getDouble(key: String): Double {
        return Double.fromBits(mPrefs.getLong(key, UNDEFINED_LONG))
    }

    protected fun getLong(key: String, value: Long = UNDEFINED_LONG): Long {
        return mPrefs.getLong(key, value)
    }

    protected fun getInt(key: String, value: Int): Int {
        return mPrefs.getInt(key, value)
    }

    protected fun getStringSet(key: String): MutableSet<String>? {
        return mPrefs.getStringSet(key, setOf(UNDEFINED_STRING))
    }

    fun clearAllPrefs() {
        mPrefs.edit().clear().apply()
    }
}