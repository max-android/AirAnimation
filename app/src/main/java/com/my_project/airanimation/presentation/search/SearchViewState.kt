package com.my_project.airanimation.presentation.search

import com.my_project.airanimation.data.entities.City

/**
 * Created Максим on 12.10.2019.
 * Copyright © Max
 */
sealed class SearchViewState
class SuccessSearch(val cities: List<City>): SearchViewState()
object Loading:SearchViewState()
class Error(val error: Throwable): SearchViewState()
