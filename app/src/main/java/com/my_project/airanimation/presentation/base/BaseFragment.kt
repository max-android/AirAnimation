package com.my_project.airanimation.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
abstract class BaseFragment : Fragment()  {

    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

}