package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class DeleteRideUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(rideId: String, onResult: (Boolean) -> Unit){
        rideRepository.delete(rideId, onResult)
    }
}