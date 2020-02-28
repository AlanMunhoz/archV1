package com.example.archv1.data.model

import androidx.lifecycle.MutableLiveData

data class ResponseResult (
    var isUpdating : MutableLiveData<Boolean> = MutableLiveData(),
    var messageResult: MutableLiveData<String> = MutableLiveData()
)