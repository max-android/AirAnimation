package com.my_project.airanimation.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.appcompat.queryTextChanges
import com.my_project.airanimation.R
import com.my_project.airanimation.data.entities.City
import com.my_project.airanimation.utilities.DialogUtils
import com.my_project.airanimation.utilities.SEARCH_TIMEOUT
import com.my_project.airanimation.utilities.SELECT_CITY
import gone
import hideSoftKeyboard
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.preloader.*
import timber.log.Timber
import visible
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, SearchActivity::class.java)
    }

    private lateinit var viewModel: SearchViewModel
    private lateinit var cityAdapter: SearchAdapter
    private lateinit var searchView: SearchView
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        createSearchView(menu)
        observeSearch()
        return true
    }

    override fun onDestroy() {
        subscriptions.dispose()
        super.onDestroy()
    }

    private fun init() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        observeData()
        initAdapter()
    }

    private fun observeData() = viewModel.sLiveData.observe(this, Observer { processViewState(it) })

    private fun processViewState(viewState: SearchViewState?) {
        viewState?.let {
            when (it) {
                is Loading -> showLoading()
                is SuccessSearch -> showCities(it.cities)
                is Error -> showError(it.error)
            }
        }
    }

    private fun showLoading() {
        showProgress()
        emptyImageView.gone()
        searchRecyclerView.gone()
    }

    private fun showCities(cities: List<City>) {
        cityAdapter.submitList(cities)
        cityAdapter.onItemClick { onItemCityClick(it) }
        removeProgress()
        emptyImageView.gone()
        searchRecyclerView.visible()
    }

    private fun createSearchView(menu: Menu) {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.searchView).actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
    }

    private fun observeSearch() {
        searchView.queryTextChanges()
            .distinct()
            .debounce(SEARCH_TIMEOUT, TimeUnit.MILLISECONDS)
            .filter { !TextUtils.isEmpty(it) }
            .subscribe { query -> search(query.toString()) }.addTo(subscriptions)
    }

    private fun search(query: String) = viewModel.search(query)

    private fun initAdapter() {
        cityAdapter = SearchAdapter()
        searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this@SearchActivity, LinearLayout.VERTICAL))
            adapter = cityAdapter
        }
    }

    private fun onItemCityClick(city: City) {
        hideSoftKeyboard(searchRecyclerView)
        returnCity(city)
        finish()
    }

    private fun returnCity(city: City) {
        val intent = Intent()
        intent.putExtra(SELECT_CITY, city)
        setResult(RESULT_OK, intent)
    }

    private fun showProgress() = preloaderLinearLayout.visible()

    private fun removeProgress() = preloaderLinearLayout.gone()

    private fun showError(error: Throwable) {
        Timber.e(error)
        removeProgress()
        emptyImageView.visible()
        if (error is ConnectException || error is UnknownHostException)
            DialogUtils(this).showNoConnect()
    }
}
