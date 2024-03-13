package com.example.watsonsrevenge.ui.composables

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.watsonsrevenge.MyForegroundService
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsPage(viewModel: MainViewModel) {

    // Get the context
    val context = LocalContext.current
    // Create a permission state for location
    val locationPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Permissions Page", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))

        Spacer(modifier = Modifier.height(8.dp))

        // Use LaunchedEffect to start the location service and navigate to the start page
        LaunchedEffect(key1 = locationPermissionState.hasPermission) {
            if (locationPermissionState.hasPermission) {
                startLocationService(context) // Simplified, assuming context handling as described.
                viewModel.navigateTo(Screen.StartPage)
            }
        }

        // Display the permission request UI
        when {

            locationPermissionState.hasPermission -> {
                startLocationService(context)
                viewModel.navigateTo(Screen.StartPage)
            }

            locationPermissionState.shouldShowRationale -> {
                Text("We need location permissions to proceed with the treasure hunt.")
                // Show rationale and request permission
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text("Grant permission")
                }
            }

            else -> {
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text("Grant Permissions")
                }
            }

        }

    }
}

fun startLocationService(context: Context) {
    // Start the foreground service
    val intent = Intent(context, MyForegroundService::class.java)
    ContextCompat.startForegroundService(context, intent)
}