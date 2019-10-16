package com.my_project.airanimation.presentation.introduction

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.my_project.airanimation.R
import com.my_project.airanimation.data.entities.City
import com.my_project.airanimation.data.entities.PairCity
import com.my_project.airanimation.presentation.base.BaseFragment
import com.my_project.airanimation.presentation.search.SearchActivity
import com.my_project.airanimation.utilities.PLACE_FROM_AUTOCOMPLETE_REQUEST_CODE
import com.my_project.airanimation.utilities.PLACE_LENGTH
import com.my_project.airanimation.utilities.PLACE_TO_AUTOCOMPLETE_REQUEST_CODE
import com.my_project.airanimation.utilities.SELECT_CITY
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_introduction.*


/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class IntroductionFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance(): Fragment = IntroductionFragment()
    }

    private val subscriptions = CompositeDisposable()
    private lateinit var viewModel: IntroductionViewModel

    override fun getLayoutRes(): Int = R.layout.fragment_introduction

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IntroductionViewModel::class.java)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PLACE_FROM_AUTOCOMPLETE_REQUEST_CODE -> {
                    val city = data?.getParcelableExtra<City>(SELECT_CITY)
                    city?.let {
                        viewModel.fromCity = it
                        fromTextInputEditText.setText(it.town)
                    }
                }
                PLACE_TO_AUTOCOMPLETE_REQUEST_CODE -> {
                    val city = data?.getParcelableExtra<City>(SELECT_CITY)
                    city?.let {
                        viewModel.toCity = it
                        toTextInputEditText.setText(it.town)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        subscriptions.dispose()
        super.onDestroy()
    }

    private fun init() {
        initData()
        observeFromEditTextClicks()
        observeToEditTextClicks()
        observeSearchButtonState()
        observeSearchButtonClicks()
    }

    private fun initData() {
        viewModel.fromCity?.let { fromTextInputEditText.setText(it.town) }
        viewModel.toCity?.let { toTextInputEditText.setText(it.town) }
    }

    private fun observeFromEditTextClicks() = fromTextInputEditText.clicks().subscribe {
        launchPlaceSearch(PLACE_FROM_AUTOCOMPLETE_REQUEST_CODE)
    }.addTo(subscriptions)

    private fun observeToEditTextClicks() = toTextInputEditText.clicks().subscribe {
        launchPlaceSearch(PLACE_TO_AUTOCOMPLETE_REQUEST_CODE)
    }.addTo(subscriptions)

    private fun observeSearchButtonClicks() =
        searchButton.clicks().subscribe { launchMap() }.addTo(subscriptions)

    private fun observeSearchButtonState() {
        Observables.combineLatest(
            fromTextInputEditText.textChanges(),
            toTextInputEditText.textChanges()
        ) { fromPlace, toPlace ->
            fromPlace.length >= PLACE_LENGTH && fromPlace.isNotBlank() && toPlace.length >= PLACE_LENGTH && toPlace.isNotBlank()
        }.subscribe { searchButtonEnabled ->
            searchButton.isEnabled = searchButtonEnabled
        }.addTo(subscriptions)
    }

    private fun launchPlaceSearch(typeField: Int) {
        startActivityForResult(
            SearchActivity.newInstance(this@IntroductionFragment.context!!),
            typeField
        )
    }

    private fun launchMap() {
        val fromCity = viewModel.fromCity
        val toCity = viewModel.toCity
        if (fromCity != null && toCity != null)
            viewModel.showMap(PairCity(fromCity, toCity))
    }

}