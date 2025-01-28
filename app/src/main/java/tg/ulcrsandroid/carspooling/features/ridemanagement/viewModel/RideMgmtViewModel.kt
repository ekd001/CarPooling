package tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.AddRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.DeleteRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.SearchRideUseCase

class RideMgmtViewModel(
    private val addRideUseCase: AddRideUseCase,
    private val deleteRideUseCase: DeleteRideUseCase,
    private val searchRideUseCase: SearchRideUseCase
) : ViewModel() {
    val ride = MutableLiveData<RideModel?>()
    val error = MutableLiveData<String?>()

    fun addRide(rideModel: RideModel){
        viewModelScope.launch {
            try {
                addRideUseCase.execute(rideModel){result ->
                    if(result){
                        Log.i(Constants.TAG_STORAGE, "Ride is saved successfuly!")
                    } else {
                        Log.i(Constants.TAG_STORAGE, "Ride is not saved!")
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

    fun searchRide(departure: String, arrival:String, date:String, placeNumber:Int){
        viewModelScope.launch {
            try {
                searchRideUseCase.execute(departure,arrival,date,placeNumber){rideList ->
                    if(rideList.isNotEmpty()){
                        Log.i(Constants.TAG_STORAGE, "List of ride ${rideList.size}")
                    }else{
                        Log.i(Constants.TAG_STORAGE, "List of ride is empty!")
                    }
                }
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }
}