package com.my_project.airanimation.presentation.map

import android.animation.Animator
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.my_project.airanimation.R
import com.my_project.airanimation.data.entities.Loc
import com.my_project.airanimation.data.entities.PairCity
import com.my_project.airanimation.presentation.base.BaseFragment
import com.my_project.airanimation.utilities.*
import createMarkerOptions
import getScreenWidth
import org.jetbrains.anko.collections.forEachWithIndex

/**
 * Created Максим on 11.10.2019.
 * Copyright © Max
 */
class MapFragment : BaseFragment(), OnMapReadyCallback {

    companion object {
        private const val MAP_KEY = "map_key"
        @JvmStatic
        fun newInstance(pairCities: PairCity): Fragment = MapFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MAP_KEY, pairCities)
            }
        }
    }

    private lateinit var viewModel: MapViewModel
    private var pairCities: PairCity? = null
    private lateinit var mMap: GoogleMap
    private var padding: Int = 0
    private var animator: Animator? = null
    private var aircraftMarker: Marker? = null

    override fun getLayoutRes(): Int = R.layout.fragment_map

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        init()
    }

    override fun onStart() {
        super.onStart()
        animator?.resume()
    }

    override fun onStop() {
        animator?.pause()
        super.onStop()
    }

    override fun onDestroyView() {
        animator?.cancel()
        animator = null
        super.onDestroyView()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val uiSettings: UiSettings? = googleMap?.uiSettings
        uiSettings?.apply {
            isCompassEnabled = false
            isMapToolbarEnabled = false
            isRotateGesturesEnabled = false
            isIndoorLevelPickerEnabled = false
        }
        googleMap?.let {
            mMap = it
            it.clear()
            pairCities?.let { pairCities ->
                initPositionsCities(pairCities)
                showRoute(pairCities)
                startAirAnimation(pairCities)
            }
        }
    }

    private fun init() {
        initData()
        initMap()
    }

    private fun initData() {
        pairCities = arguments?.getParcelable(MAP_KEY) as? PairCity
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.newMapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initPositionsCities(pairCities: PairCity) {
        val boundsBuilder = LatLngBounds.Builder()
        listOf(pairCities.fromCity, pairCities.toCity).forEachWithIndex { i, city ->
            val location = LatLng(city.loc.lat, city.loc.lon)
            boundsBuilder.include(location)
            if (city.iata.isNotEmpty()) setIconsCities(city.loc, city.iata.first(), i)
            else setIconsCities(city.loc, DEFAULT_IATA, i)
        }
        val bounds = boundsBuilder.build()
        padding = when {
            context!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> context!!.getScreenWidth() / 4
            else -> context!!.getScreenWidth() / 8
        }
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.animateCamera(cameraUpdate)
    }

    private fun setIconsCities(location: Loc, name: String, position: Int) {
        val cityView = LayoutInflater.from(context).inflate(R.layout.layout_city_marker, null)
        val markerCityTextView = cityView.findViewById<AppCompatTextView>(R.id.markerCityTextView)
        markerCityTextView.text = name
        val option = context!!.createMarkerOptions(cityView, location, false)
        when (position) {
            FIRST_POS -> option.anchor(0.25f, 0.5f)
            SECOND_POS -> option.anchor(1.0f, 0.5f)
        }
        mMap.addMarker(option)
    }

    private fun updatePositionAircraft(currentLoc: Loc, angle: Float) {
        aircraftMarker?.remove()
        val aircraftView =
            LayoutInflater.from(context).inflate(R.layout.layout_aircraft_marker, null)
        val image = aircraftView.findViewById<AppCompatImageView>(R.id.aircraftImageView)
        image.rotation = angle
        val option = context!!.createMarkerOptions(aircraftView, currentLoc, true)
        aircraftMarker = mMap.addMarker(option)
    }

    private fun initPositionsDotes(dots: List<Loc>) {
        dots.forEach {
            val aircraftView =
                LayoutInflater.from(context).inflate(R.layout.layout_dote_marker, null)
            val option = context!!.createMarkerOptions(aircraftView, it, false)
            mMap.addMarker(option)
        }
    }

    private fun showRoute(pairCities: PairCity) {
        val points = getPoints(pairCities.fromCity.loc, pairCities.toCity.loc)
        val dots = getDotsForRoute(points)
        initPositionsDotes(dots)
    }

    private fun startAirAnimation(pairCities: PairCity) {
        val points = getPoints(pairCities.fromCity.loc, pairCities.toCity.loc)
        animator = getAircraftAnimator(points) { loc, angle -> updatePositionAircraft(loc, angle) }
        animator?.start()
        animator?.resume()
    }
}