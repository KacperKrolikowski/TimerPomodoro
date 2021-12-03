package com.krolikowski.timerpomodoro.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.krolikowski.timerpomodoro.R
import com.krolikowski.timerpomodoro.databinding.ActivityMainBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {

    override val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val viewModelClass = MainActivityViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        navHostFragment.findNavController()
            .addOnDestinationChangedListener { controller, destination, arguments ->
                when(destination.id){
                    R.id.pomodoroListFragment, R.id.pomodoroFragment, R.id.settingsFragment ->{
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    }
                }
            }
    }
}