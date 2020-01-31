package com.lfkekpoint.blockchain.task.presentation

import com.lfkekpoint.blockchain.task.presentation.base.arch.container.ContainerActivity
import com.lfkekpoint.blockchain.task.presentation.features.main.MainFragment

class MainActivity: ContainerActivity() {
    override fun getFirstFragmentForContainer() = MainFragment()
}