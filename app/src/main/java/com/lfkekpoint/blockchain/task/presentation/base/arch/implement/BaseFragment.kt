package com.lfkekpoint.blockchain.task.presentation.base.arch.implement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.lfkekpoint.blockchain.task.domain.base.ApiMessageException
import com.lfkekpoint.blockchain.task.presentation.base.arch.interfaces.BaseRootView
import com.lfkekpoint.blockchain.task.presentation.helper.SnackHelper
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.dialogs.info.InfoDialog
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

abstract class BaseFragment : Fragment(), BaseRootView {

    val name: String = this::class.java.canonicalName!!

    override val controller: LifecycleObserver? = controllerClass?.newInstance() ?: null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(controller ?: return)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun showSpinnerPopup() {
        (activity as BaseActivity?)?.showSpinnerPopup()
    }

    override fun dismissSpinnerPopup() {
        (activity as BaseActivity?)?.dismissSpinnerPopup()
    }

    override fun showMessage(message: String, lengthIfNeed: Int) {

        dismissSpinnerPopup()

        SnackHelper().show(
            activity = activity,
            text = message,
            duration = lengthIfNeed
        )
    }

    override fun <T> observeSubject(subject: BehaviorSubject<T>, observer: Observer<T>) {
        subject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    override fun onBackPressed() {
        (activity as BaseActivity).onBackPressed()
    }

    override fun onError(e: Throwable) {

        dismissSpinnerPopup()

        when (e) {
            is ApiMessageException -> {
                val fragemnt = InfoDialog()
                val bundle = Bundle()
                bundle.putString(InfoDialog.EXTRA_INFO_TITLE, e.message?.let { "${e.code}. $it" } ?: "Что-то пошло не так")
                fragemnt.arguments = bundle
                fragemnt.isCancelable = false

                fragemnt.show(activity?.supportFragmentManager ?: return, fragemnt.tag)
            }
        }
    }
}