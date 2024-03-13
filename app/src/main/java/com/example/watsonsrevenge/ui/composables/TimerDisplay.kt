package com.example.watsonsrevenge.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.util.TimerUtil
import java.util.concurrent.TimeUnit

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
@Composable
fun TimerDisplay() {

    val timeElapsed by TimerUtil.timeElapsed.observeAsState(0)
    val formattedTime = remember(timeElapsed) {
        String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(timeElapsed),
            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % TimeUnit.MINUTES.toSeconds(1))
    }

    Text(
        text = "Time Elapsed: $formattedTime",
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(8.dp)
    )

}