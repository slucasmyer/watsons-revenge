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

    private val _dialogMessage = MutableLiveData<String>("")
    val dialogMessage: LiveData<String> = _dialogMessage

    fun updateDialog(message: String?, show: Boolean) {
        _dialogMessage.value = message ?: ""
        _showDialog.value = show
    }

//    val clueProximityThreshold = 100.0
    val clueProximityThreshold = .05

    // LiveData to manage proximity message visibility and content
    private val _proximityMessage = MutableLiveData<String?>(null)
    val proximityMessage: LiveData<String?> = _proximityMessage

    // Function to update proximity message
    fun updateProximityMessage(message: String?) {
        _proximityMessage.value = message
    }

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
        // Delegate the check to the ClueRepository which knows about the clues' sequence
        return ClueRepository.isLastClue()
    }

    fun quitGame() {
        ClueRepository.resetClueIndex()
        updateProximityMessage(null)
        navigateTo(Screen.StartPage)
        TimerUtil.resetTimer()
    }


    private var _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean> = _permissionGranted

    private var _currentScreen = MutableLiveData<Screen>()
    val currentScreen: LiveData<Screen> get() = _currentScreen

    fun updatePermissionStatus(granted: Boolean) {
        viewModelScope.launch {
            _permissionGranted.value = granted
        }
    }

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _currentScreen.value = screen
        }
    }

    init {
        navigateTo(Screen.PermissionsPage)
    }

}