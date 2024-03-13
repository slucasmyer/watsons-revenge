package com.example.watsonsrevenge

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.watsonsrevenge.repository.LocationRepository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */
class MyForegroundService : Service() {
    private val locationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        requestLocationUpdates()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun requestLocationUpdates() {

        val locationRequest = LocationRequest.Builder(5000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    LocationRepository.postLocationUpdate(location)
                }
            }
        }

        try {
            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        } catch (unlikely: SecurityException) {
            Log.e("LocationService", "Failed to request location updates", unlikely)
        }
    }




    companion object {
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "ForegroundServiceChannel"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun createNotification(): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("Running...")
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setContentIntent(pendingIntent)
            .build()
    }

}