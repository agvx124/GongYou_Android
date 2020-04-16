package com.na.dgsw.gongyou_android.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Created by NA on 2020-04-16
 * skehdgur8591@naver.com
 */

abstract class BaseActivity<T: ViewDataBinding, V: BaseViewModel<*>> : AppCompatActivity(), BaseFragment.CallBack {

    private lateinit var mViewDataBinding: T

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getViewModel(): V

    /**
     * Binding 을 위한 함수
     */
    abstract fun getBindingVariable(): Int

    abstract fun setUp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        setUp()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.setVariable(getBindingVariable(), getViewModel())
        mViewDataBinding.executePendingBindings()
    }

    fun getViewDataBinding() : T {
        return mViewDataBinding
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }
}