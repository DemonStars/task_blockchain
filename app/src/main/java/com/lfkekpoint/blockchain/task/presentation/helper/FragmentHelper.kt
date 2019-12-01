package com.lfkekpoint.blockchain.task.presentation.helper

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.lfkekpoint.blockchain.task.R
import com.lfkekpoint.blockchain.task.presentation.base.arch.implement.BaseFragment
import com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm.BaseBindableDialogFragment

object FragmentHelper {

    enum class Animation {
        SLIDE_LEFT,
        SLIDE_RIGHT,
        FADE
    }

    fun add(fm: FragmentManager, frameId: Int, fragment: BaseFragment, needAddToBackStack: Boolean = true,
            anim: Animation = Animation.FADE
    ) {

        val transaction = fm.beginTransaction()
        addAnimationsIfNeed(transaction, anim)

        if (needAddToBackStack) {
            transaction.addToBackStack(fragment.name)
        }

        transaction.add(frameId, fragment, fragment.name).commit()
    }

    fun add(fm: FragmentManager, frameId: Int, fragment: BaseBindableDialogFragment, needAddToBackStack: Boolean = true,
            anim: Animation = Animation.FADE
    ) {

        val transaction = fm.beginTransaction()
        addAnimationsIfNeed(transaction, anim)

        if (needAddToBackStack) {
            transaction.addToBackStack(fragment.name)
        }

        transaction.add(frameId, fragment, fragment.name).commit()
    }

    fun replace(fm: FragmentManager, frameId: Int, fragment: BaseFragment, needAddToBackStack: Boolean = true,
                anim: Animation = Animation.FADE
    ) {

        val transaction = fm.beginTransaction()
        addAnimationsIfNeed(transaction, anim)

        if (needAddToBackStack) {
            transaction.addToBackStack(fragment.name)
        }

        transaction.replace(frameId, fragment, fragment.name).commit()
    }


    fun remove(fm: FragmentManager, fragment: BaseFragment, anim: Animation = Animation.FADE) {

        val transaction = fm.beginTransaction()
        addAnimationsIfNeed(transaction, anim)

        transaction.remove(fragment).commit()
    }

    fun removeLastFragment(fm: FragmentManager) {
        fm.popBackStack()
    }

    fun getLastFragment(fm: FragmentManager): BaseFragment {
        val fragments = fm.fragments
        return fragments[fragments.size - 1] as BaseFragment
    }

    private fun addAnimationsIfNeed(transaction: FragmentTransaction,
                                    animation: Animation?) {
        if (animation != null) {
            when (animation) {
                Animation.FADE -> transaction.setCustomAnimations(
                        R.anim.fade_in, R.anim.fade_out,
                        R.anim.fade_in, R.anim.fade_out)

                Animation.SLIDE_LEFT -> transaction.setCustomAnimations(
                        R.anim.slide_left, R.anim.slide_left_out,
                        R.anim.slide_right, R.anim.slide_right_out)

                Animation.SLIDE_RIGHT -> transaction.setCustomAnimations(
                        R.anim.slide_right, R.anim.slide_right_out,
                        R.anim.slide_left, R.anim.slide_left_out)
            }
        }
    }

    fun getFragmentByName(fragmentName: String, fm: FragmentManager?): BaseFragment? {
        return fm?.findFragmentByTag(fragmentName) as BaseFragment?
    }
}
