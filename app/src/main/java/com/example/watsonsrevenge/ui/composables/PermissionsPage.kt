package com.example.watsonsrevenge.ui.composables

import android.content.Context
import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.watsonsrevenge.MyForegroundService
import com.example.watsonsrevenge.model.MainViewModel
import com.example.watsonsrevenge.model.Screen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsPage(viewModel: MainViewModel) {
    val context = LocalContext.current
    val location by viewModel.locationUpdates.observeAsState()
    val locationPermissionState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Permissions Page", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))

        LaunchedEffect(key1 = locationPermissionState.hasPermission) {
            if (locationPermissionState.hasPermission) {
                viewModel.updatePermissionStatus(granted = true)
                startLocationService(context) // Simplified, assuming context handling as described.
                viewModel.navigateTo(Screen.StartPage)
            }
        }

        when {
            locationPermissionState.hasPermission -> {
                Log.d("PermissionsPage", "Permission Granted.")
                viewModel.updatePermissionStatus(true)
                Log.d("PermissionsPage", "Starting Location Service.")
                startLocationService(context)
                Log.d("PermissionsPage", "Navigation to start page.")
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
                // Permission is not granted, nor should show rationale. This is likely the first launch
                Button(onClick = { locationPermissionState.launchPermissionRequest() }) {
                    Text("Grant Permissions")
                }
            }
        }
    }
}

fun startLocationService(context: Context) {
    val intent = Intent(context, MyForegroundService::class.java)
    ContextCompat.startForegroundService(context, intent)
}