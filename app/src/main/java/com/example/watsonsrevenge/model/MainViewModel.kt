package com.example.watsonsrevenge.model

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.watsonsrevenge.data.Clue
import com.example.watsonsrevenge.repository.LocationRepository
import com.example.watsonsrevenge.repository.ClueRepository
import com.example.watsonsrevenge.util.TimerUtil
import kotlinx.coroutines.launch

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
enum class Screen {
    PermissionsPage,
    StartPage,
    CluePage,
    ClueSolvedPage,
    TreasureHuntCompletedPage
}

class MainViewModel : ViewModel() {

    // LiveData for dialog state
    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    // LiveData for dialog title
    private val _dialogTitle = MutableLiveData<String>("")
    val dialogTitle: LiveData<String> = _dialogTitle

    // LiveData for dialog message
    private val _dialogMessage = MutableLiveData<String>("")
    val dialogMessage: LiveData<String> = _dialogMessage

    fun updateDialog(title: String?, message: String?, show: Boolean) {
        // Update dialog state
        _dialogTitle.value = title ?: ""
        _dialogMessage.value = message ?: ""
        _showDialog.value = show
    }

    // Proximity threshold for clues (50 Meter radius)
    val clueProximityThreshold = .05

    // LiveData for location updates
    val locationUpdates: LiveData<Location> = LocationRepository.locationUpdates

    // LiveData for current clue
    val currentClue: LiveData<Clue?> = ClueRepository.currentClue
    fun startGame() {
        // Start game
        TimerUtil.startTimer()
        navigateTo(Screen.CluePage)
    }
    fun goToNextClue() {
        // Go to next clue
        ClueRepository.nextClue()
    }
    fun isLastClue(): Boolean {
        // Check if current clue is the last clue
        return ClueRepository.isLastClue()
    }
    fun quitGame() {
        // Quit game
        ClueRepository.resetClueIndex()
        navigateTo(Screen.StartPage)
        TimerUtil.resetTimer()
    }

    // LiveData for current screen
    private var _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    fun navigateTo(screen: Screen) {
        // Navigate to screen
        viewModelScope.launch {
            _currentScreen.value = screen
        }
    }
    init {
        // Initialize
        navigateTo(Screen.PermissionsPage)
    }
}