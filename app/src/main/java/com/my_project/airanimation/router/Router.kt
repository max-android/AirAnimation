package com.my_project.airanimation.router

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.my_project.airanimation.R
import com.my_project.airanimation.data.entities.PairCity
import com.my_project.airanimation.presentation.introduction.IntroductionFragment
import com.my_project.airanimation.presentation.map.MapFragment

class Router {

    private var fragmentManager: FragmentManager? = null
    var actualScreen: Screen = Screen.INTRODUCTION

    fun init(fragmentManager: FragmentManager?) {
        this.fragmentManager = fragmentManager
    }

    fun replace(screen: Screen, data: Any = Any()) {
        if (backStackCount() > 0) {
            clearBackStack()
        }
        applyCommand(screen, Command.REPLACE, data)
    }

    fun forward(screen: Screen, data: Any = Any()) {
        applyCommand(screen, Command.FORWARD, data)
    }

    fun detach() {
        fragmentManager = null
    }

    private fun clearBackStack() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun back() {
        fragmentManager?.popBackStack()
        updateActualBackScreen()
    }

    fun backTo(nameFrag: String?) {
        fragmentManager?.popBackStackImmediate(nameFrag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun backStackCount() = fragmentManager?.backStackEntryCount ?: 0

    private fun applyCommand(screen: Screen, command: Command, data: Any) {
        updateActualScreen(screen)
        doFragmentTransaction(screen, command, data)
    }

    private fun updateActualScreen(screen: Screen) {
        actualScreen = screen
    }

    private fun doFragmentTransaction(screen: Screen, command: Command, data: Any) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.main_container, getFragment(screen, data))
            ?.apply { if (command == Command.FORWARD) addToBackStack(screen.name) }
            ?.setCustomAnimations(
                R.animator.slide_in_right,
                R.animator.slide_out_left,
                R.animator.slide_in_left,
                R.animator.slide_out_right
            )
            ?.commitAllowingStateLoss()
    }

    private fun getFragment(screen: Screen, data: Any): Fragment {
        return when (screen) {
            Screen.INTRODUCTION -> IntroductionFragment.newInstance()
            Screen.MAP -> MapFragment.newInstance(data as PairCity)
        }
    }

    private fun updateActualBackScreen() {
        actualScreen = when (actualScreen) {
            Screen.MAP -> Screen.INTRODUCTION
            else -> Screen.INTRODUCTION
        }
    }
}