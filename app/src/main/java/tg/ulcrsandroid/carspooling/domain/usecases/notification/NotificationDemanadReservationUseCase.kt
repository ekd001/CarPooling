package tg.ulcrsandroid.carspooling.domain.usecases.notification

import android.util.Log
import tg.ulcrsandroid.carspooling.data.repository.notification.NotificationRepository

class NotificationDemanadReservationUseCase(private val notificationRepository: NotificationRepository) {

    fun execute(driverUid: String, onComplete: (Boolean) -> Unit) {
        notificationRepository.sendDemandReservationNotification(driverUid, onComplete)
    }
}