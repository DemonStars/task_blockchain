package com.raketa.im.lfkekpoint.utaircashbox.presentation.helper

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseFragment
import com.raketa.im.lfkekpoint.utaircashbox.presentation.modules.application.appLifeClasses.AppState

class PermissionHelper(
        private val mFragment: BaseFragment
) {

    fun checkPermission(
            reqCode: Int,
            permission: String,
            explanationAction: () -> Unit,
            alreadyGrantedAction: () -> Unit
    ) {

        if (ContextCompat.checkSelfPermission(
                AppState.appContext,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mFragment.activity as Activity,
                    permission)) {
                explanationAction.invoke()
            } else {
                mFragment.requestPermissions(arrayOf(permission), reqCode)
            }
        } else {
            alreadyGrantedAction.invoke()
        }
    }

    fun requestPermission(
            reqCode: Int,
            permission: String
    ){
        mFragment.requestPermissions(arrayOf(permission), reqCode)
    }
}