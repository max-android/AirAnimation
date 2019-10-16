package com.my_project.airanimation.presentation.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.my_project.airanimation.App
import com.my_project.airanimation.R
import com.my_project.airanimation.router.Router
import com.my_project.airanimation.router.Screen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.appComponent.inject(this)
        router.init(supportFragmentManager)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.showParenthetical()
        }
    }

    override fun onBackPressed() = when (router.actualScreen) {
        Screen.INTRODUCTION -> super.onBackPressed()
        Screen.MAP -> viewModel.back()
    }

    override fun onDestroy() {
        router.detach()
        super.onDestroy()
    }
}
