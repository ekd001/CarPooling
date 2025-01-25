package tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.AddRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.DeleteRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.SearchRideUseCase

class RideMgmtViewModelFactory(
    private val addRideUseCase: AddRideUseCase,
    private val deleteRideUseCase: DeleteRideUseCase,
    private val searchRideUseCase: SearchRideUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(RideMgmtViewModel::class.java)){
            RideMgmtViewModel(addRideUseCase, deleteRideUseCase, searchRideUseCase) as T
        } else {
            throw IllegalArgumentException("Not RideMgmtViewModel class")
        }
    }
}