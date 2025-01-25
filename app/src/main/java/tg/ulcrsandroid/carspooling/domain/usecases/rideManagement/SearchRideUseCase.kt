package tg.ulcrsandroid.carspooling.domain.usecases.rideManagement

import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.data.repository.ride.RideRepository

class SearchRideUseCase(private val rideRepository: RideRepository) {
    suspend fun execute(
        departure: String,
        arrival: String,
        date:String,
        placeNumber:Int,
        onResult: (List<RideModel>) -> Unit) {
        rideRepository.search(departure, arrival, date, placeNumber, onResult)
    }
}