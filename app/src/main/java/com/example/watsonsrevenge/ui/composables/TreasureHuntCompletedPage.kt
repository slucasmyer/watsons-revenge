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

//@Composable
//fun TreasureHuntCompletedPage(viewModel: MainViewModel) {
//    Column(
//        modifier = Modifier.fillMaxWidth().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Treasure Hunt Completed Page", style = MaterialTheme.typography.headlineLarge)
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(
//            onClick = { viewModel.navigateTo(Screen.StartPage) },
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text("Home")
//        }
//    }
//}

@Composable
fun TreasureHuntCompletedPage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val finalClue by viewModel.currentClue.observeAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Congratulations!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "You've completed the treasure hunt!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        // Reuse the TimerDisplay composable to show the elapsed time
        TimerDisplay()

        Spacer(modifier = Modifier.height(16.dp))
        finalClue?.let {
            Text(text = it.name, style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.info, style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.navigateTo(Screen.StartPage)
                viewModel.quitGame() // Reset the game state for a new start
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Home")
        }
    }
}
