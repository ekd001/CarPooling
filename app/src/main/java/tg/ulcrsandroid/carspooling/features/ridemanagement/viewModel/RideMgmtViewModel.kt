package tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.domain.usecases.notification.NotificationDemanadReservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.AddRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.DeleteRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.GetReservationPassengerUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.ListRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.MakereservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.SearchRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.UpdateStatusUseCase

class RideMgmtViewModel(
    private val addRideUseCase: AddRideUseCase,
    private val deleteRideUseCase: DeleteRideUseCase,
    private val searchRideUseCase: SearchRideUseCase,
    private val makereservationUseCase: MakereservationUseCase,
    private val sendDemandReservationNotificationUseCase: NotificationDemanadReservationUseCase,
    private val updateStatusUseCase: UpdateStatusUseCase,
    private val listRideUseCase: ListRideUseCase,
    private val getReservationPassengerUseCase: GetReservationPassengerUseCase
) : ViewModel() {
    private val _rides = MutableLiveData<List<RideModel>>()
    val rides: LiveData<List<RideModel>> get() = _rides
    private val _reservations = MutableLiveData<List<ReservationModel>>()
    val reservations: LiveData<List<ReservationModel>> get() = _reservations
    val error = MutableLiveData<String?>()

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: MutableLiveData<String?> get() = _toastMessage

    fun addRide(rideModel: RideModel) {
        viewModelScope.launch {
            try {
                addRideUseCase.execute(rideModel){result ->
                    if(result){
                        Log.i(Constants.TAG_STORAGE, "Ride is saved successfuly!")
                        _toastMessage.postValue("Trajet sauvegardé avec succès!")
                    } else {
                        Log.i(Constants.TAG_STORAGE, "Ride is not saved!")
                        _toastMessage.postValue("Echec de sauvegarde de trajet!")
                    }
                }
            } catch (e:Exception){
                error.value = e.message
            }
        }
    }

    fun deleteRide(rideId: String){
        viewModelScope.launch {
            try {
                deleteRideUseCase.execute(rideId){result ->
                    if(result){
                        Log.i(Constants.TAG_STORAGE, "Ride is delete!")
                    } else {
                        Log.i(Constants.TAG_STORAGE, "Ride is not delete!")
                    }
                }
            } catch (e:Exception){
                error.value = e.message
            }
        }
    }

    fun searchRide(departure: String, arrival:String) {
        var riders = listOf<RideModel>()
        viewModelScope.launch {
            try {
                searchRideUseCase.execute(departure,arrival){rideList ->
                    if(rideList.isNotEmpty()){
                        Log.i(Constants.TAG_STORAGE, "List of ride ${rideList.size}")
                        _rides.postValue(rideList)
                        Log.i(Constants.TAG_STORAGE, "List of ride ${riders.size}")
                    }else{
                        Log.i(Constants.TAG_STORAGE, "List of ride is empty!")
                        _rides.postValue(emptyList())
                    }
                }
            }catch (e:Exception){
                error.value = e.message
                _rides.postValue(emptyList())
            }
        }
    }

    fun makeReservation(reservation: ReservationModel, driverUid: String) {
        viewModelScope.launch {
            try {
                makereservationUseCase.execute(reservation){result ->
                    if(result){
                        Log.i(Constants.TAG_STORAGE, "Reservation is saved successfuly!")
                        sendDemandReservationNotificationUseCase.execute(driverUid){result ->
                            if(result){
                                Log.i(Constants.TAG_NOTIFICATION, "Notification is sent successfuly!")
                            } else {
                                Log.i(Constants.TAG_NOTIFICATION, "Notification is not sent!")
                            }
                        }
                    } else {
                        Log.i(Constants.TAG_STORAGE, "Reservation is not saved!")
                    }
                }
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }

    fun updateStatus(reservationId: String, status: String) {
        viewModelScope.launch {
            try {
                updateStatusUseCase.execute(reservationId, status){result ->
                    if(result){
                        Log.i(Constants.TAG_STORAGE, "Status is updated successfuly!")
                    }else{
                        Log.i(Constants.TAG_STORAGE, "Status is not updated!")
                    }
                }
            }catch (e:Exception) {
                error.value = e.message
            }

        }

    }

    fun getlistRide(driverId: String) {
        viewModelScope.launch {
            try {
                listRideUseCase.execute(driverId){rideList ->
                    if(rideList.isNotEmpty()){
                        Log.i(Constants.TAG_STORAGE, "List of ride ${rideList.size}")
                        _rides.postValue(rideList)
                    }else{
                        Log.i(Constants.TAG_STORAGE, "List of ride is empty!")
                        _rides.postValue(emptyList())
                    }
                }
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }

    fun getReservationPassenger(passengerId: String) {
        viewModelScope.launch {
            try {
                getReservationPassengerUseCase.execute(passengerId) { reservationList ->
                    if (reservationList.isNotEmpty()) {
                        Log.i(Constants.TAG_STORAGE, "List of reservation ${reservationList.size}")
                        _reservations.postValue(reservationList)
                    } else {
                        Log.i(Constants.TAG_STORAGE, "List is empty")
                        _reservations.postValue(emptyList())
                    }
                }
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }

    fun clearToastMessage() {
        _toastMessage.value = null
    }
}

