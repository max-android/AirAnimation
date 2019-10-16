package com.my_project.airanimation.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my_project.airanimation.App
import com.my_project.airanimation.data.repository.SearchRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class SearchViewModel : ViewModel() {

    @Inject
    lateinit var searchRepository: SearchRepository
    private val subscriptions = CompositeDisposable()
    val sLiveData = MutableLiveData<SearchViewState>()

    init {
        App.appComponent.inject(this)
    }

    fun search(query: String) {
        searchRepository
            .cities(query)
            .doOnSubscribe { startProgress() }
            .map { it.cities }
            .subscribe(
                { cities -> sLiveData.value = SuccessSearch(cities) },
                { error ->
                    run {
                        sLiveData.value = Error(error)
                        sLiveData.value = null
                    }
                }
            ).addTo(subscriptions)
    }

    private fun startProgress() {
        sLiveData.postValue(Loading)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

}