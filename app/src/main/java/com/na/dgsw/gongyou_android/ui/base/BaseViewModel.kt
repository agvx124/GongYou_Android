package com.na.dgsw.gongyou_android.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

/**
 * Created by NA on 2020-04-16
 * skehdgur8591@naver.com
 */

abstract class BaseViewModel<N>(application: Application) : AndroidViewModel(application) {

    private var navigator : WeakReference<N>? = null

    private val compositeDisposable = CompositeDisposable()
    val mIsLoading = MutableLiveData<Boolean>(false)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun addCompositeDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun setNavigator(navigator: N) {
        this.navigator = WeakReference(navigator)
    }

    fun getNavigator() : N? {
        return navigator?.get()
    }
}