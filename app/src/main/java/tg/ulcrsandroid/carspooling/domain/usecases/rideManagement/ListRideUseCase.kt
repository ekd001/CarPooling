package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class ListRideUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(driverId: String, onResult: (List<RideModel>) -> Unit) {
        rideRepository.getRide(driverId, onResult)
    }
}