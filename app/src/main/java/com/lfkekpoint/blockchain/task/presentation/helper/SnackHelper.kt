package com.lfkekpoint.blockchain.task.presentation.helper

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.lfkekpoint.blockchain.task.R


class SnackHelper {

    @JvmOverloads
    fun show(activity: Activity?, text: String,
             duration: Int = Snackbar.LENGTH_LONG,
             actionBtnTextResId: Int? = null,
             action: (() -> Unit)? = null) {

        val rootView = activity?.findViewById<View>(android.R.id.content)

        val snack = create(rootView ?: return, action, actionBtnTextResId, duration, text)

        snack.show()
    }

    @JvmOverloads
    fun create(root: View,
               action: (() -> Unit)? = null,
               actionBtnTextResId: Int? = null,
               duration: Int,
               text: String?): Snackbar {

        val errorText = text ?: ResHelper.getString(R.string.error_default, root.context)
        val snack = Snackbar.make(root, errorText, duration)

        if (actionBtnTextResId != null) {
            snack.setAction(actionBtnTextResId, { action?.invoke() })
        }
        return snack
    }
}


