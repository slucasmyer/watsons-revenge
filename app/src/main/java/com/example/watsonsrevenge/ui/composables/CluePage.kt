package com.example.watsonsrevenge.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.example.watsonsrevenge.util.Geo
import com.example.watsonsrevenge.util.TimerUtil

@Composable
fun CluePage(viewModel: MainViewModel) {

    val currentClue by viewModel.currentClue.observeAsState()
    val location by viewModel.locationUpdates.observeAsState()
    val proximityMessage by viewModel.proximityMessage.observeAsState()
    val showHint = remember { mutableStateOf(false) }
    val showDialog by viewModel.showDialog.observeAsState(false)
    val dialogTitle by viewModel.dialogTitle.observeAsState("")
    val dialogMessage by viewModel.dialogMessage.observeAsState("")

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.updateDialog(null, null, false)
            },
            title = { Text(dialogTitle) },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateDialog(null,null, false)
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

        Text(text = "Clue", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(16.dp))

        TimerDisplay()

        Spacer(modifier = Modifier.height(16.dp))

//        location?.let {
//            Text("Current Location: Lat: ${it.latitude}, Lon: ${it.longitude}")
//        } ?: Text("Waiting for location...")
//
//        Spacer(modifier = Modifier.height(16.dp))

        currentClue?.let {
            Text(it.description, style = MaterialTheme.typography.headlineLarge)
            if (showHint.value) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(it.hint)
            }
        } ?: Text("No clue available")

        Spacer(modifier = Modifier.height(16.dp))
        proximityMessage?.let {
            Text(it, style = MaterialTheme.typography.bodyLarge, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Hint Button
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
//                    showHint.value = !showHint.value
                    currentClue?.let { clue ->
                        viewModel.updateDialog(
                            "Hint",
                            clue.hint,
                            true
                        )
                    }
//                    viewModel.updateDialog(
//                        "Hint",
//                        "You're still $distance km away from the clue. Keep looking!",
//                        true
//                    )
                }
            ) {
                Text("Get Hint")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(modifier = Modifier.weight(1f), onClick = {
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
                            viewModel.updateDialog(
                                "Not Quite!",
                                "You're still $distance km away from the clue. Keep looking!",
                                true
                            )
                        }
                    }
                }
            }) {
                Text("Found It!")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quit Button
        Button(onClick = {
            viewModel.quitGame()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text("Quit")
        }

    }
}