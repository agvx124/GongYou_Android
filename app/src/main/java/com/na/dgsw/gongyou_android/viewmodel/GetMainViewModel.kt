package com.na.dgsw.gongyou_android.viewmodel

import android.app.Application
import com.na.dgsw.gongyou_android.base.BaseViewModel
import com.na.dgsw.gongyou_android.utils.SingleLiveEvent


/**
 * Created by NA on 2020-06-25
 * skehdgur8591@naver.com
 */
class GetMainViewModel(application: Application): BaseViewModel<Any>(application) {

    val onQrCodeScannerEvent = SingleLiveEvent<Unit>()


    fun qrCodeScannerBtnClick() {
        onQrCodeScannerEvent.call()
    }

}