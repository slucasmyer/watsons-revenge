package com.example.watsonsrevenge.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.example.watsonsrevenge.util.Geo
import com.example.watsonsrevenge.util.TimerUtil

@Composable
fun CluePage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentClue by viewModel.currentClue.observeAsState()
    val location by viewModel.locationUpdates.observeAsState()
    val proximityMessage by viewModel.proximityMessage.observeAsState()
    val showHint = remember { mutableStateOf(false) }

    val showDialog by viewModel.showDialog.observeAsState(false)
    val dialogMessage by viewModel.dialogMessage.observeAsState("")

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.updateDialog(null, false)
            },
            title = { Text("Hint") },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateDialog(null, false)
                }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Clue Page", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        TimerDisplay()
        Spacer(modifier = Modifier.height(16.dp))
        location?.let {
            Text("Current Location: Lat: ${it.latitude}, Lon: ${it.longitude}")
        } ?: Text("Waiting for location...")
        Spacer(modifier = Modifier.height(16.dp))
        currentClue?.let {
            Text("Clue Location: Lat: ${it.latitude}, Lon: ${it.longitude}")
            Spacer(modifier = Modifier.height(16.dp))
            Text("Clue: ${it.description}")
            if (showHint.value) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Hint: ${it.hint}")
            }
        } ?: Text("No clue available")
        Spacer(modifier = Modifier.height(16.dp))
        proximityMessage?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hint Button
        Button(onClick = { showHint.value = !showHint.value }) {
            Text("Get Hint")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            location?.let { currentLocation ->
                // Create Geo instances for user location and clue location
                val userGeo = Geo(currentLocation.latitude, currentLocation.longitude)
                val clueGeo = currentClue?.let { clue -> Geo(clue.latitude, clue.longitude) }

                clueGeo?.let {
                    // Calculate the distance
                    val distance = userGeo.haversine(it)

                    if (distance <= viewModel.clueProximityThreshold) {
                        TimerUtil.pauseTimer()
                        viewModel.updateProximityMessage(null)
                        viewModel.navigateTo(Screen.ClueSolvedPage)
                        if (viewModel.isLastClue()) {
                            viewModel.navigateTo(Screen.TreasureHuntCompletedPage)
                        } else {
                            viewModel.navigateTo(Screen.ClueSolvedPage)
                        }
                    } else {
                        viewModel.updateDialog("You're still $distance km away from the clue. Keep looking!", true)
                    }
                }
            }
        }) {
            Text("Found It!")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Quit Button
        Button(onClick = {
            viewModel.quitGame()
        }) {
            Text("Quit")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.navigateTo(Screen.ClueSolvedPage) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("ClueSolvedPage ->")
        }
    }
}