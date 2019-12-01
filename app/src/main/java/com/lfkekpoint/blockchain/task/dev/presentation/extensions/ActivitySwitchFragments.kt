package com.lfkekpoint.blockchain.task.dev.presentation.extensions

import android.content.Intent
import com.lfkekpoint.blockchain.task.dev.presentation.LoginActivity
import com.lfkekpoint.blockchain.task.dev.presentation.StartActivity

fun StartActivity.openMainActivity(){
    this.startActivity(Intent(this, LoginActivity::class.java))
}