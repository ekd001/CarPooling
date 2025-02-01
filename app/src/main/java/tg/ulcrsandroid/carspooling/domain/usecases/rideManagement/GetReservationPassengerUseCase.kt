package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class GetReservationPassengerUseCase(private val rideRepository: RideRepository) {

    suspend fun execute(passengerId: String, onResult: (List<ReservationModel>) -> Unit) {
        rideRepository.getReservationPassenger(passengerId, onResult)
    }
}