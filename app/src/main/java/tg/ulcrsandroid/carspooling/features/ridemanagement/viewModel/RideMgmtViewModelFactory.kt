package tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.domain.usecases.notification.NotificationDemanadReservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.AddRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.DeleteRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.MakereservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.SearchRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.UpdateStatusUseCase

class RideMgmtViewModelFactory(
    private val addRideUseCase: AddRideUseCase,
    private val deleteRideUseCase: DeleteRideUseCase,
    private val searchRideUseCase: SearchRideUseCase,
    private val makereservationUseCase: MakereservationUseCase,
    private val sendDemandReservationNotificationUseCase: NotificationDemanadReservationUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(RideMgmtViewModel::class.java)){
            RideMgmtViewModel(
                addRideUseCase,
                deleteRideUseCase,
                searchRideUseCase,
                makereservationUseCase,
                sendDemandReservationNotificationUseCase,
                updateStatusUseCase) as T
        } else {
            throw IllegalArgumentException("Not RideMgmtViewModel class")
        }
    }
}