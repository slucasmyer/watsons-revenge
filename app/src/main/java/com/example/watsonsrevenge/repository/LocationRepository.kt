package com.example.watsonsrevenge.repository

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
object LocationRepository {
    private val _locationUpdates = MutableLiveData<Location>()
    val locationUpdates: LiveData<Location> = _locationUpdates

    fun postLocationUpdate(location: Location) {
        _locationUpdates.postValue(location)
    }

}

