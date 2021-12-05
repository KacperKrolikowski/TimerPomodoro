package com.krolikowski.timerpomodoro.ui.pomodorolist.items

import android.view.View
import androidx.navigation.findNavController
import com.krolikowski.timerpomodoro.R
import com.krolikowski.timerpomodoro.data.db.entities.SinglePomodoro
import com.krolikowski.timerpomodoro.databinding.ItemPomodoroBinding
import com.krolikowski.timerpomodoro.ui.pomodorolist.PomodoroListFragmentDirections
import com.xwray.groupie.viewbinding.BindableItem

class PomodoroItem(
    private val currentPomodoro: SinglePomodoro
) : BindableItem<ItemPomodoroBinding>() {

    override fun getLayout() = R.layout.item_pomodoro

    override fun initializeViewBinding(view: View): ItemPomodoroBinding {
        return ItemPomodoroBinding.bind(view)
    }

    override fun bind(viewBinding: ItemPomodoroBinding, position: Int) {
        viewBinding.apply {
            titleText.text = currentPomodoro.name
            if (currentPomodoro.description.isNotEmpty()) descriptionText.apply {
                text = currentPomodoro.description
                setAnimationDuration(500)
                setEllipsizedText("See more")
                setVisibleLines(1)
                setIsExpanded(false)
                setEllipsizedTextColor(android.R.color.holo_red_light)
                setOnClickListener {
                    descriptionText.toggle()
                }
            }

            goToPomodoroButton.setOnClickListener {
                val goToPomodoro = PomodoroListFragmentDirections.actionPomodoroListFragmentToPomodoroFragment(false, currentPomodoro)
                it.findNavController().navigate(goToPomodoro)
            }

            titleText.setOnClickListener {
                val goToPomodoroEdit = PomodoroListFragmentDirections.actionPomodoroListFragmentToPomodoroCreatorFragment(false, currentPomodoro)
                it.findNavController().navigate(goToPomodoroEdit)
            }
        }
    }
}