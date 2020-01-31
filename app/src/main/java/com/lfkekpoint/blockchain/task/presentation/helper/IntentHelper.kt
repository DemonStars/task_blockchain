package com.lfkekpoint.blockchain.task.presentation.helper

import android.app.Activity
import android.content.Intent
import com.lfkekpoint.blockchain.task.presentation.MainActivity
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity


fun BaseActivity.logoutToStartActivity() {

    }

fun Activity.startMainActivity() {
    this.startActivity(Intent(this, MainActivity::class.java))
}