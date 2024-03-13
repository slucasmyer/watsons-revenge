package com.example.watsonsrevenge.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.watsonsrevenge.data.Clue

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
object ClueRepository {
    private var cluesList = emptyList<Clue?>()
    private fun getClues(): List<Clue?> = cluesList
    fun populateClues(clues: List<Clue?>) {
        cluesList = clues
        if (areCluesLoaded()) {
            _currentClueIndex.value = 0
        }
        Log.d("ClueRepository", "Clues populated")
        for (clue in cluesList) {
            Log.d("ClueRepository", "Clue: $clue")
        }
    }
    fun areCluesLoaded(): Boolean = cluesList.isNotEmpty()

    private val _currentClueIndex = MutableLiveData<Int?>(null) // Make index nullable
    val currentClue: LiveData<Clue?> = _currentClueIndex.map { index ->
        index?.let { getClues().getOrNull(it) }
    }
    fun nextClue() {
        _currentClueIndex.value?.let { currentIndex ->
            if (currentIndex < cluesList.size - 1) {
                _currentClueIndex.value = currentIndex + 1
            } else {
                Log.d("ClueRepository", "No more clues. Should be unreachable.")
            }
        }
    }
    fun resetClueIndex() {
        _currentClueIndex.value = 0
    }
    fun isLastClue(): Boolean {
        _currentClueIndex.value?.let {
            return it >= cluesList.size - 1
        }
        return false
    }
}