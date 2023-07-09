package com.woosung.domain

sealed class Result<out T : DomainModel> {
    data class Success<out T : DomainModel>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}
