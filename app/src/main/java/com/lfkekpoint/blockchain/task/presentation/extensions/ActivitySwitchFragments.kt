package com.lfkekpoint.blockchain.task.presentation.extensions

import android.content.Intent
import com.lfkekpoint.blockchain.task.presentation.LoginActivity
import com.lfkekpoint.blockchain.task.presentation.StartActivity

fun StartActivity.openMainActivity(){
    this.startActivity(Intent(this, LoginActivity::class.java))
}