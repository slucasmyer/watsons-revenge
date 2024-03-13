package com.example.watsonsrevenge.geofencing

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

//class GeofenceTransitionsJobIntentService : JobIntentService() {
//    override fun onHandleWork(intent: Intent) {
//        val geofencingEvent = GeofencingEvent.fromIntent(intent)
//        if (geofencingEvent != null) {
//            if (geofencingEvent.hasError()) {
//                // Handle error
//                return
//            }
//        }
//
//        // Check for transition type
//        if (geofencingEvent != null) {
//            if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
//                // Handle entering geofence
//            }
//        }
//    }
//
//    companion object {
//        fun enqueueWork(context: Context, intent: Intent) {
//            enqueueWork(context, GeofenceTransitionsJobIntentService::class.java, 1, intent)
//        }
//    }
//}
