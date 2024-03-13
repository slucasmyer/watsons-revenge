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

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    private val _dialogTitle = MutableLiveData<String>("")
    val dialogTitle: LiveData<String> = _dialogTitle

    private val _dialogMessage = MutableLiveData<String>("")
    val dialogMessage: LiveData<String> = _dialogMessage

    fun updateDialog(title: String?, message: String?, show: Boolean) {
        _dialogTitle.value = title ?: ""
        _dialogMessage.value = message ?: ""
        _showDialog.value = show
    }

    val clueProximityThreshold = .05

    val locationUpdates: LiveData<Location> = LocationRepository.locationUpdates

    val currentClue: LiveData<Clue?> = ClueRepository.currentClue
    fun startGame() {
        TimerUtil.startTimer()
        navigateTo(Screen.CluePage)
    }
    fun goToNextClue() {
        ClueRepository.nextClue()
    }
    fun isLastClue(): Boolean {
        return ClueRepository.isLastClue()
    }
    fun quitGame() {
        ClueRepository.resetClueIndex()
        navigateTo(Screen.StartPage)
        TimerUtil.resetTimer()
    }

    private var _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _currentScreen.value = screen
        }
    }
    init {
        navigateTo(Screen.PermissionsPage)
    }
}