package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class MakereservationUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(reservation: ReservationModel, onResult: (Boolean) -> Unit) {
        rideRepository.reservation(reservation, onResult)
    }
}