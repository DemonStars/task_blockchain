package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity
import com.lfkekpoint.blockchain.task.presentation.helper.SnackHelper
import io.reactivex.Observer
import android.graphics.drawable.ColorDrawable
import com.lfkekpoint.blockchain.task.domain.base.ApiMessageException
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.dialogs.info.InfoDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject


abstract class BaseBindableDialogFragment : AppCompatDialogFragment(), BaseBindableRootView {

    override val controller by lazy { ViewModelProviders.of(this).get(controllerClass) }
    val name = this::class.java.canonicalName
    override val baseStateObserver = StateObserver<BaseState>(
        onNextAction = { baseState ->

            when (baseState) {
                is Message -> showMessage(baseState.message)
                is ShowSpinner -> showSpinnerPopup()
                is DismissSpinner -> dismissSpinnerPopup()
                is OnBackPressed -> onBackPressed()
                is OnError -> this@BaseBindableDialogFragment.onError(baseState.error)
            }
        },
        onSubscribeAction = { controller.disposable.add(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeSubject(controller.stateSubject, stateObserver)
        observeSubject(controller.baseStateSubject, baseStateObserver)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        binding.setVariable(variableId, controller)

        return binding.root
    }

    override fun <T> observeSubject(subject: BehaviorSubject<T>, observer: Observer<T>) {
        subject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    override fun onBackPressed() {
        (activity as BaseActivity).onBackPressed()
    }

    override fun showSpinnerPopup() {
        (activity as BaseActivity?)?.showSpinnerPopup()
    }

    override fun dismissSpinnerPopup() {
        (activity as BaseActivity?)?.dismissSpinnerPopup()
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.onViewCreated()
    }

    override fun showMessage(message: String, lengthIfNeed: Int) {
        SnackHelper().show(
            activity = activity,
            text = message,
            duration = lengthIfNeed)

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
