package com.krolikowski.timerpomodoro.helpers.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.krolikowski.timerpomodoro.data.db.repositories.PomodoroDatabaseRepository
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<VB: ViewBinding, VM: BaseViewModel> :
DaggerFragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    protected abstract val binding: VB
    protected abstract val viewModelClass: Class<VM>
    protected lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(viewModelClass)
        return binding.root
    }
}