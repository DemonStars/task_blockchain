package com.lfkekpoint.blockchain.task.domain.base

class ApiMessageException(val errorMessage: String, val code: Int?): Exception(errorMessage)