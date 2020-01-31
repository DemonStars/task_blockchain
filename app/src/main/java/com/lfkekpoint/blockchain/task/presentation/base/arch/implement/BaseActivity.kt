package com.lfkekpoint.blockchain.task.presentation.base.arch.implement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.domain.base.ApiMessageException
import com.lfkekpoint.blockchain.task.domain.features.shareds.shareds.SharedPrefs
import com.lfkekpoint.blockchain.task.presentation.base.arch.interfaces.BaseRootView
import com.lfkekpoint.blockchain.task.presentation.helper.SnackHelper
import com.lfkekpoint.blockchain.task.presentation.helper.logoutToStartActivity
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.MainApp
import io.reactivex.subjects.BehaviorSubject

abstract class BaseActivity : AppCompatActivity(), BaseRootView {

    protected var mMyApp: MainApp? = null

    //todo Попробовать сделать диалогом, чтобы невозможно было отменять
    private var spinnerPopup: PopupWindow? = null

    override fun showMessage(message: String, lengthIfNeed: Int) {
        dismissSpinnerPopup()

        SnackHelper().show(this, message, lengthIfNeed)
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        mMyApp = this.applicationContext as MainApp

        setContentView(layoutId)

        lifecycle.addObserver(controller ?: return)
    }

    override fun onResume() {
        super.onResume()

        mMyApp?.setCurrentActivity(this)
    }

    override fun onPause() {
        clearReferences()
        super.onPause()
    }


    override fun <T> observeSubject(subject: BehaviorSubject<T>, observer: io.reactivex.Observer<T>) {
        subject.subscribe(observer)
    }

    override fun showSpinnerPopup() {
        if (this.hasWindowFocus()) {
            spinnerPopup?.let { showSpinner() } ?: inflateAndShow()
        }
    }

    private fun showSpinner() {
        val view = findViewById<ViewGroup>(android.R.id.content)
        spinnerPopup?.showAtLocation(view as ViewGroup, Gravity.CENTER, 0, 0)
    }

    private fun inflateAndShow() {
        val view = findViewById<ViewGroup>(android.R.id.content)
        val layout = LayoutInflater.from(this).inflate(R.layout.popup_fulscreen_spinner, view as ViewGroup, false)

        spinnerPopup =
                PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true)
        spinnerPopup?.isOutsideTouchable = false

        showSpinner()
    }

    override fun dismissSpinnerPopup() {
        spinnerPopup?.dismiss()
    }

    override fun onBackPressed() {

        when (supportFragmentManager.backStackEntryCount) {
            0, 1 -> finish()
            else -> supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        clearReferences()
        spinnerPopup?.dismiss()
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearReferences() {
        val currActivity = mMyApp?.getCurrentActivity()
        if (this == currActivity) {
            mMyApp?.setCurrentActivity(null)
        }
    }

    override fun onError(e: Throwable) {
        when (e) {
            is ApiMessageException -> showMessage(e.message?.let { "${e.code}. $it" } ?: "Что-то пошло не так")
        }
    }

    @SuppressLint("CheckResult")
    fun logout() {
        SharedPrefs.clearAllPrefs()
        logoutToStartActivity()
    }
}