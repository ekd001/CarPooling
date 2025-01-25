package tg.ulcrsandroid.carspooling

import android.app.Application
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
        val addRideUseCase = AddRideUseCase(rideRepository)
        val deleteRideUseCase = DeleteRideUseCase(rideRepository)
        val searchRideUseCase = SearchRideUseCase(rideRepository)

        factoryRideManagement = RideMgmtViewModelFactory(
            addRideUseCase,
            deleteRideUseCase,
            searchRideUseCase
        )
    }
}