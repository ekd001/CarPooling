package tg.ulcrsandroid.carspooling.data.repository.user

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.core.utils.GlobalUser
import tg.ulcrsandroid.carspooling.domain.entities.User

class FirebaseUserRepository(auth: FirebaseAuth, db: FirebaseFirestore): UserRepository {
    private val auth = auth
    private val db = db

    override suspend fun getCurrentUser(): UserModel? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            UserModel(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName,
                carLicenseNumber = null
            )
        }
    }

    override suspend fun signInWithGoogle(idToken: String): UserModel? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        val firebaseUser = result.user
        return firebaseUser?.let {
            val userModel = UserModel(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName,
                carLicenseNumber = null
            )
            saveUser(userModel, null)
            userModel
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        username:String,
        onResult: (FirebaseUser?, String?,String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Crée un utilisateur avec l'email et le mot de passe
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                val user = authResult.user

                if (user != null) {
                    // Obtenir le token Firebase ID
                    val tokenResult = user.getIdToken(true).await()
                    val token = tokenResult.token

                    // Sauvegarder l'utilisateur dans Firestore (vérifiez que saveUser est suspend)
                    saveUser(UserModel(user.uid, user.email, username, null), null)

                    // Retourne le résultat au thread principal
                    withContext(Dispatchers.Main) {
                        onResult(user, token, null)
                    }
                } else {
                    // Gestion du cas où l'utilisateur est null
                    withContext(Dispatchers.Main) {
                        onResult(null, null, "User creation failed: user is null.")
                    }
                }
            } catch (e: Exception) {
                // Capture et retourne les exceptions
                withContext(Dispatchers.Main) {
                    onResult(null, null, "Error: ${e.message}")
                }
            }
        }
    }

    override suspend fun login(
        email: String,
        password: String,
        onResult: (UserModel?, String?, String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Connecter l'utilisateur avec l'email et le mot de passe
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user

                if (firebaseUser != null) {
                    // Obtenir le token Firebase ID
                    val idToken = firebaseUser.getIdToken(true).await().token
                    if (idToken != null) {
                        // Créer un objet UserModel à partir des informations de Firebase
                        val baseUser = UserModel(
                            uid = firebaseUser.uid,
                            email = firebaseUser.email,
                            displayName = null,
                            carLicenseNumber = null
                        )
                        Log.i(Constants.TAG_AUTH, "Base User: $baseUser")

                        // Récupérer les informations supplémentaires depuis Firestore
                        val fullUser = fetchUser(baseUser)

                        // Retourner les informations combinées
                        withContext(Dispatchers.Main) {
                            onResult(fullUser ?: baseUser, idToken, null)
                        }
                    } else {
                        // Gestion du cas où le token est null
                        withContext(Dispatchers.Main) {
                            onResult(null, null, "Failed to retrieve token")
                        }
                    }
                } else {
                    // Gestion du cas où l'utilisateur Firebase est null
                    withContext(Dispatchers.Main) {
                        onResult(null, null, "User not found")
                    }
                }
            } catch (e: Exception) {
                // Capture et retourne les exceptions
                withContext(Dispatchers.Main) {
                    onResult(null, null, e.message)
                }
            }
        }
    }

    override suspend fun saveUser(userModel: UserModel, carLicenseNumber: String?) {
        // Vérifier si l'utilisateur existe déjà dans Firestore
        val userRef = db.collection("users").document(userModel.uid)
        val documentSnapshot = userRef.get().await()

        if (documentSnapshot.exists()) {
            // L'utilisateur existe déjà, donc on ne fait rien
            Log.i(Constants.TAG_STORAGE,"User already exist")
        } else {
            // Enregistrement dans Firestore
            var user = userModel
            user.carLicenseNumber = carLicenseNumber
            userRef.set(user).await()
            Log.i(Constants.TAG_STORAGE,"User registered successfully")
        }
    }

    override suspend fun fetchUser(userModel: UserModel): UserModel? {
        return try {
            val document = db.collection("users")
                .document(userModel.uid)
                .get()
                .await()

            if (document.exists()) {
                Log.d(Constants.TAG_STORAGE, "Document data: ${document.data}")
                val fetchedUser = document.toObject(UserModel::class.java)
                if (fetchedUser != null) {
                    //val user = User(fetchedUser.uid, fetchedUser.displayName, fetchedUser.email, fetchedUser.carLicenseNumber)
                    //user.setConnected(true)
                    GlobalUser.setUser(fetchedUser) // Définit l'utilisateur global
                    Log.d(Constants.TAG_STORAGE, "User mapped successfully: $fetchedUser")
                } else {
                    Log.e(Constants.TAG_STORAGE, "Mapping failed: fetchedUser is null")
                }
                fetchedUser
            } else {
                Log.e(Constants.TAG_STORAGE, "Document does not exist for UID: ${userModel.uid}")
                null
            }
        } catch (e: Exception) {
            Log.e(Constants.TAG_STORAGE, "Error fetching user: ${e.message}")
            null // Gérer les erreurs si nécessaire
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}