package com.lfkekpoint.blockchain.task.dev.presentation

import com.lfkekpoint.blockchain.task.dev.presentation.features.login.LoginFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.container.ContainerActivity
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseFragment

class LoginActivity: ContainerActivity() {
    override fun getFirstFragmentForContainer() = LoginFragment()

}