package com.krolikowski.timerpomodoro.helpers.core

import android.os.Bundle
import android.os.PersistableBundle
import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<VB: ViewBinding, VM: BaseViewModel> :
DaggerAppCompatActivity(){

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    protected abstract val binding: VB
    protected abstract val viewModelClass: Class<VM>
    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Timber.v("onCreate ${javaClass.simpleName}")
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(viewModelClass)
        setContentView(binding.root)
    }
}