package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class UpdateStatusUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(reservationId: String, status: String, onResult: (Boolean) -> Unit) {
        rideRepository.update(reservationId, status, onResult)
    }
}