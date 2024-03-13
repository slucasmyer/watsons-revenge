package com.example.watsonsrevenge.repository

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watsonsrevenge.data.Clue
import androidx.lifecycle.map

object LocationRepository {
    private val _locationUpdates = MutableLiveData<Location>()
    val locationUpdates: LiveData<Location> = _locationUpdates

    fun postLocationUpdate(location: Location) {
        _locationUpdates.postValue(location)
    }

    fun isNearClue(clue: Clue, threshold: Double = 100.0): LiveData<Boolean> = _locationUpdates.map { location ->
        location?.let {
            val results = FloatArray(1)
            Location.distanceBetween(it.latitude, it.longitude, clue.latitude, clue.longitude, results)
            results[0] < threshold
        } ?: false
    }
}

