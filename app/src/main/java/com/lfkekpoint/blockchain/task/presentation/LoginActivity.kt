package com.lfkekpoint.blockchain.task.presentation

import com.lfkekpoint.blockchain.task.presentation.base.arch.container.ContainerActivity
import com.lfkekpoint.blockchain.task.presentation.features.login.LoginFragment

class LoginActivity: ContainerActivity() {
    override fun getFirstFragmentForContainer() = LoginFragment()

}