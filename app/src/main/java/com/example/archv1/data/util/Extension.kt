package com.example.archv1.data.util

import com.example.archv1.domain.model.ResponseResult
import retrofit2.Response

fun <T: Any> Response<T>.responseResult() : ResponseResult<T> {
    return this.body()?.let {
        ResponseResult.Success(it)
    } ?: run {
        ResponseResult.Failure("HttpCode: ${this.code()}")
    }
}