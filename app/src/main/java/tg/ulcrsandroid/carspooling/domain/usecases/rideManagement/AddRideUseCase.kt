package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class AddRideUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(rideModel: RideModel, onResult: (Boolean) -> Unit){
        rideRepository.add(rideModel, onResult)
    }
}