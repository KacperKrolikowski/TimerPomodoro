package com.krolikowski.timerpomodoro.ui.pomodorolist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.databinding.FragmentPomodoroListBinding
import com.krolikowski.timerpomodoro.helpers.core.BaseFragment
import com.krolikowski.timerpomodoro.ui.pomodorolist.items.PomodoroItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_main.*

class PomodoroListFragment: BaseFragment<FragmentPomodoroListBinding, PomodoroListViewModel>() {
    override val binding by lazy { FragmentPomodoroListBinding.inflate(layoutInflater) }
    override val viewModelClass = PomodoroListViewModel::class.java

    private val pomodoroAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPomodoroRecycler()
        setupButton()
    }

    private fun setupPomodoroRecycler(){
        pomodoroAdapter.clear()
        viewModel.pomodoroList.observe(viewLifecycleOwner, { it ->
            it.forEach {
                pomodoroAdapter.add(
                    PomodoroItem(
                        it
                    )
                )
            }
        })

        binding.pomodoroRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = pomodoroAdapter
            onFlingListener = null
        }
    }

    private fun setupButton(){
        binding.addNewPomodoroButton.setOnClickListener {
            val newPomodoro = SinglePomodoro("", "", 25, 4, 5, 15, 3)
            val goToAddNewPomodoro = PomodoroListFragmentDirections.actionPomodoroListFragmentToPomodoroCreatorFragment(true, newPomodoro)
            findNavController().navigate(goToAddNewPomodoro)
        }
    }

}