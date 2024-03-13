package com.example.watsonsrevenge.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.example.watsonsrevenge.util.TimerUtil

@Composable
fun ClueSolvedPage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentClue by viewModel.currentClue.observeAsState()
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Clue Solved!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        TimerDisplay()
        Spacer(modifier = Modifier.height(16.dp))
        currentClue?.let {
            Text(text = it.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.info, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.goToNextClue()
                TimerUtil.startTimer() // Restart the timer for the next clue
                viewModel.navigateTo(Screen.CluePage)

            }
        ) {
            Text("Continue")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.navigateTo(Screen.TreasureHuntCompletedPage) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Complete Hunt")
        }
    }
}