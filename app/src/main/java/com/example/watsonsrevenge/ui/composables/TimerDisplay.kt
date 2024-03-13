package com.example.watsonsrevenge.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.util.TimerUtil
import java.util.concurrent.TimeUnit

@Composable
fun TimerDisplay() {
    val timeElapsed by TimerUtil.timeElapsed.observeAsState(0)
    val formattedTime = remember(timeElapsed) {
        // Format timeElapsed from milliseconds to a human-readable format
        String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % TimeUnit.MINUTES.toSeconds(1))
    }

    Text(
        text = "Time Elapsed: $formattedTime",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(8.dp)
    )
}