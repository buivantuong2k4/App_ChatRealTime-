package com.example.appchat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val isShowBottom = MutableLiveData<Boolean>(false)
    val isShowtop = MutableLiveData<Boolean>(false)


    fun setisShowBottom(isShow: Boolean) {
        isShowBottom.value = isShow
    }

    fun getisShowBottom(): Boolean? {
        return isShowBottom.value
    }
    fun setisShowtop(isShow: Boolean) {
        isShowtop.value = isShow
    }

    fun getisShowtop(): Boolean? {
        return isShowtop.value
    }
}