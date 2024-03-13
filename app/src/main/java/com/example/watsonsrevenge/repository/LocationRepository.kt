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
    // LocationRepository is an object, so it's a singleton and can be accessed from anywhere
    private val _locationUpdates = MutableLiveData<Location>()
    val locationUpdates: LiveData<Location> = _locationUpdates

    fun postLocationUpdate(location: Location) {
        // Post location update to LiveData
        _locationUpdates.postValue(location)
    }

}

