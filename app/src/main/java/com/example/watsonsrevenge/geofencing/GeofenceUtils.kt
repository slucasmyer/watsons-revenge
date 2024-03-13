package com.example.watsonsrevenge.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.watsonsrevenge.data.Clue
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest

//object GeofencingUtil {
//
//
//    fun createGeofenceForClue(clue: Clue, radius: Float): Geofence {
//        return Geofence.Builder()
//            .setRequestId(clue.id.toString()) // Geofence ID
//            .setCircularRegion(
//                clue.latitude,
//                clue.longitude,
//                radius
//            )
//            .setExpirationDuration(Geofence.NEVER_EXPIRE)
//            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
//            .build()
//    }
//
//    fun getGeofencingRequest(geofence: Geofence): GeofencingRequest {
//        return GeofencingRequest.Builder().apply {
//            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//            addGeofence(geofence)
//        }.build()
//    }
//
//    fun getGeofencePendingIntent(context: Context): PendingIntent {
//        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
//        // Use FLAG_UPDATE_CURRENT so that you get the same pending intent back when calling add and remove.
//        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//    }
//
//
//    fun addGeofencesForClues(context: Context, geofencingClient: GeofencingClient, clues: List<Clue>) {
//        val geofenceList = clues.map { clue ->
//            createGeofenceForClue(clue, 100f) // Assuming a radius of 100 meters for each clue
//        }
//
//        val geofencingRequest = GeofencingRequest.Builder().apply {
//            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//            addGeofences(geofenceList)
//        }.build()
//
//        val pendingIntent = getGeofencePendingIntent(context)
//
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
//            addOnSuccessListener {
//                // Handle success
//            }
//            addOnFailureListener {
//                // Handle failure
//            }
//        }
//    }
//
//}