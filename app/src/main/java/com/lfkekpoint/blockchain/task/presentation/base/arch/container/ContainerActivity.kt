package com.lfkekpoint.blockchain.task.presentation.base.arch.container

import android.os.Bundle
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseActivity
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseBindableDialogFragment
import com.lfkekpoint.blockchain.task.presentation.helper.FragmentHelper

abstract class ContainerActivity : BaseActivity() {

    override val layoutId = R.layout.base_fragment_container
    override val controller = null
    override val controllerClass = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        initFragmentIfNeed()
    }

    private fun initFragmentIfNeed() {

        if (supportFragmentManager.backStackEntryCount == 0) {
            addFragmentToContainer(getFirstFragmentForContainer())
        }
    }

    fun addFragmentToContainer(

        fragmentForContainer: BaseFragment,
        needAddToBackStack: Boolean = true

    ) {

        FragmentHelper.add(
                fm = supportFragmentManager,
                frameId = getIdContainerRoot(),
                fragment = fragmentForContainer,
                needAddToBackStack = needAddToBackStack)
    }

    fun addFragmentToContainer(

        fragmentForContainer: BaseBindableDialogFragment,
        needAddToBackStack: Boolean = true

    ) {

        FragmentHelper.add(
                fm = supportFragmentManager,
                frameId = getIdContainerRoot(),
                fragment = fragmentForContainer,
                needAddToBackStack = needAddToBackStack)
    }

    fun replaceFragmentToContainer(

        fragmentForContainer: BaseFragment,
        needAddToBackStack: Boolean = true

    ) {

        FragmentHelper.replace(
                fm = supportFragmentManager,
                frameId = getIdContainerRoot(),
                fragment = fragmentForContainer,
                needAddToBackStack = needAddToBackStack)
    }


    protected fun findFragmentByName(fragmentName: String): BaseFragment? {
        return FragmentHelper.getFragmentByName(fragmentName, supportFragmentManager)
    }

    open fun getIdContainerRoot(): Int = R.id.container_fragment_root

    abstract fun getFirstFragmentForContainer(): BaseFragment
}