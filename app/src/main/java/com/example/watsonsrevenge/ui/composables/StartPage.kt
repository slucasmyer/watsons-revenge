package com.example.watsonsrevenge.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.watsonsrevenge.R
import com.example.watsonsrevenge.model.MainViewModel

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
@Composable
fun StartPage(viewModel: MainViewModel) {

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Display app name
        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(16.dp))

        // Display game intro
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = stringResource(id = R.string.game_rules_intro), style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.game_rules_headline), style = MaterialTheme.typography.headlineLarge)

        // Display game rules
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = stringResource(id = R.string.game_rules_body), style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Start the game
        Button(
            onClick = { viewModel.startGame() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Start Treasure Hunt")
        }

    }
}

