package com.example.watsonsrevenge.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
object TimerUtil {
    // TimerUtil is an object, so it's a singleton and can be accessed from anywhere in the app
    private var timerJob: Job? = null
    private val _timeElapsed = MutableLiveData<Long>(0)
    val timeElapsed: LiveData<Long> = _timeElapsed
    private var startTime = 0L
    private var pauseTime = 0L

    fun startTimer() {
        // If the timer is not running or has been cancelled, start it
        if (timerJob == null || timerJob?.isCancelled == true) {

            // If resuming from a pause, adjust the startTime by the pause duration
            if (pauseTime != 0L) {
                startTime += System.currentTimeMillis() - pauseTime
                pauseTime = 0L // Reset pauseTime after resuming
            } else {
                // If starting fresh, use the current time as the start time
                startTime = System.currentTimeMillis()
            }
            // Start the timer coroutine
            timerJob = CoroutineScope(Dispatchers.Default).launch {
                while (isActive) {
                    _timeElapsed.postValue(System.currentTimeMillis() - startTime)
                    delay(1000) // Update every second
                }
            }

        }
    }

    fun pauseTimer() {
        // If the timer is running, cancel it
        timerJob?.cancel()
        pauseTime = System.currentTimeMillis() // Record the time when paused
    }

    fun resetTimer() {
        pauseTimer() // Ensure the timer is paused before resetting
        _timeElapsed.postValue(0) // Reset the time elapsed to 0
        startTime = 0L // Reset startTime for a fresh start
        pauseTime = 0L // Reset pauseTime to clear any previous pause state
    }
}
