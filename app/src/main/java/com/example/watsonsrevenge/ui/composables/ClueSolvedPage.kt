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
import com.example.watsonsrevenge.util.TimerUtil

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
@Composable
fun ClueSolvedPage(viewModel: MainViewModel) {

    val currentClue by viewModel.currentClue.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Clue Solved!", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(16.dp))

//        Spacer(modifier = Modifier.height(16.dp))
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
                TimerUtil.startTimer()
                viewModel.navigateTo(Screen.CluePage)
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Continue to next clue!")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quit Button
        Button(onClick = {
            viewModel.quitGame()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Text("Quit")
        }
    }
}