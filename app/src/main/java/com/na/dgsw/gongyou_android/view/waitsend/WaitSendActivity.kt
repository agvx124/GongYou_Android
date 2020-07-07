package com.na.dgsw.gongyou_android.view.waitsend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import androidx.lifecycle.Observer
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.na.dgsw.gongyou_android.BR
import com.na.dgsw.gongyou_android.Constants
import com.na.dgsw.gongyou_android.R
import com.na.dgsw.gongyou_android.base.BaseActivity
import com.na.dgsw.gongyou_android.data.network.response.FileResponse
import com.na.dgsw.gongyou_android.databinding.ActivityWaitSendBinding
import com.na.dgsw.gongyou_android.viewmodel.WaitSendViewModel
import java.lang.StringBuilder
import java.sql.Date
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class WaitSendActivity : BaseActivity<ActivityWaitSendBinding, WaitSendViewModel>() {

    private var dataList: ArrayList<FileResponse> = ArrayList()

    override val viewModelClass: Class<WaitSendViewModel>
        get() = WaitSendViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.activity_wait_send
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun setUp() {
        dataList = intent.getSerializableExtra("dataList") as ArrayList<FileResponse>

        binding.eigenValueTextView.text = setTwoCharSpacing(dataList.get(0).fileEigenValue.toString())

        val multiFormatWriter = MultiFormatWriter()
        val bitMatrix = multiFormatWriter.encode(Constants.DEFAULT_HOST, BarcodeFormat.QR_CODE, 200, 200)
        val barcodeEncode = BarcodeEncoder()
        val bitmap = barcodeEncode.createBitmap(bitMatrix)
        binding.qrCreateImageView.setImageBitmap(bitmap)
    }

    override fun observerViewModel() {
        with(viewModel) {
            currentTime.observe(this@WaitSendActivity, Observer {
                binding.remainTimeTextView.text = DateUtils.formatElapsedTime(it)
            })
        }
    }

    private fun setTwoCharSpacing(str: String): String {
        var result = StringBuilder()

        for (i in 0.. str.length) {
            if ((i + 1) % 2 == 0) {
                result.append(str.get(i) + " ")
                if (i + 1 == str.length) break
            }
            else {
                result.append(str.get(i))
            }
        }

        return result.toString()
    }


}