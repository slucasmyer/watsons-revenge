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
fun StartPage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val location by viewModel.locationUpdates.observeAsState()
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Start Page", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        location?.let {
            Text("Current Location: Lat: ${it.latitude}, Lon: ${it.longitude}")
        } ?: Text("Waiting for location...")
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.startGame() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Start Treasure Hunt")
        }
    }
}

