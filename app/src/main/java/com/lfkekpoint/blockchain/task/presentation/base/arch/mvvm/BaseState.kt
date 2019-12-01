package com.lfkekpoint.blockchain.task.presentation.base.arch.mvvm

sealed class BaseState
object ShowSpinner : BaseState()
object DismissSpinner : BaseState()
object Logout : BaseState()
object OnBackPressed : BaseState()
class Message(val message: String) : BaseState()
class OnError(val error: Throwable) : BaseState()
