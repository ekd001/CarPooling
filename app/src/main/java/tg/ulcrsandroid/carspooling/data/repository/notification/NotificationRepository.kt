package tg.ulcrsandroid.carspooling.data.repository.notification

import android.util.Log
import tg.ulcrsandroid.carspooling.data.datasource.NotificationRemoteDataSource

class NotificationRepository(private val notificationRemoteDataSource: NotificationRemoteDataSource) {

    fun sendDemandReservationNotification(driverUid: String, onComplete: (Boolean) -> Unit) {
        notificationRemoteDataSource.sendDemandReservationNotification(driverUid, onComplete)
    }
}