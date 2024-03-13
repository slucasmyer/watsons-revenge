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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
@Composable
fun TreasureHuntCompletedPage(viewModel: MainViewModel) {
    // Observe current clue state since we need to display it on this page
    val finalClue by viewModel.currentClue.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Congratulations!", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Treasure Hunt Completed!", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        TimerDisplay()

        Spacer(modifier = Modifier.height(16.dp))

        // Display the final clue if it exists
        finalClue?.let {
            Text(text = it.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.info, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.navigateTo(Screen.StartPage)
                viewModel.quitGame()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Home")
        }

    }
}
