package tg.ulcrsandroid.carspooling

import android.app.Application
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import tg.ulcrsandroid.carspooling.data.repository.ride.FirebaseRideRepository
import tg.ulcrsandroid.carspooling.data.repository.user.FirebaseUserRepository
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignOutUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.AddRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.DeleteRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.SearchRideUseCase
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModelFactory
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModelFactory
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tg.ulcrsandroid.carspooling.data.datasource.NotificationRemoteDataSource
import tg.ulcrsandroid.carspooling.data.repository.notification.NotificationRepository
import tg.ulcrsandroid.carspooling.domain.usecases.notification.NotificationDemanadReservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.GetReservationPassengerUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.ListRideUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.MakereservationUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.rideManagement.UpdateStatusUseCase

val ONESIGNAL_APP_ID = "b6a8c024-67c0-4285-9b92-8f267e83d722"

class CarSpoolingApplication: Application() {
    lateinit var factoryAuthentifcation: AuthViewModelFactory
    lateinit var factoryRideManagement: RideMgmtViewModelFactory
    lateinit var authentification : FirebaseAuth
    lateinit var database : FirebaseFirestore

    override fun onCreate() {
        super.onCreate()

        authentification = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        initializeAuthDependencies(authentification, database)
        initializeRideDependencies(database)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        /*CoroutineScope(Dispatchers.IO).launch {
            Log.i("OneSignal", "OneSignal initialized correctly.")
            OneSignal.Notifications.requestPermission(false)
        }*/
    }

    private fun initializeAuthDependencies(auth: FirebaseAuth, db: FirebaseFirestore) {
        val userRepository = FirebaseUserRepository(auth, db)
        val loginUseCase = LoginUseCase(userRepository)
        val registerUserCase = RegisterUserCase(userRepository)
        val signInWithGoogleUseCase = SignInWithGoogleUseCase(userRepository)
        val signOutUseCase = SignOutUseCase(userRepository)

        factoryAuthentifcation = AuthViewModelFactory(
            signInWithGoogleUseCase,
            registerUserCase,
            loginUseCase,
            signOutUseCase
        )
    }

    private fun initializeRideDependencies(db: FirebaseFirestore) {
        val rideRepository = FirebaseRideRepository(db)
        val notificationRemoteDataSource = NotificationRemoteDataSource(this)
        val notificationRepository = NotificationRepository(notificationRemoteDataSource)
        val addRideUseCase = AddRideUseCase(rideRepository)
        val deleteRideUseCase = DeleteRideUseCase(rideRepository)
        val searchRideUseCase = SearchRideUseCase(rideRepository)
        val makereservationUseCase = MakereservationUseCase(rideRepository)
        val sendDemandReservationNotificationUseCase = NotificationDemanadReservationUseCase(notificationRepository)
        val updateStatusUseCase = UpdateStatusUseCase(rideRepository)
        val listRideUseCase: ListRideUseCase = ListRideUseCase(rideRepository)
        val getReservationPassengerUseCase = GetReservationPassengerUseCase(rideRepository)
        factoryRideManagement = RideMgmtViewModelFactory(
            addRideUseCase,
            deleteRideUseCase,
            searchRideUseCase,
            makereservationUseCase,
            sendDemandReservationNotificationUseCase,
            updateStatusUseCase,
            listRideUseCase,
            getReservationPassengerUseCase
        )
    }
}