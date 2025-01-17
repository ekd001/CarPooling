package tg.ulcrsandroid.carspooling.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import tg.ulcrsandroid.carspooling.core.models.UserModel

class FirebaseUserRepository:UserRepository {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun getCurrentUser(): UserModel? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            UserModel(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName
            )
        }
    }

    override suspend fun signInWithGoogle(idToken: String): UserModel? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        val firebaseUser = result.user
        return firebaseUser?.let {
            UserModel(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName
            )
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        username:String,
        onResult: (FirebaseUser?, String?,String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    val user = task.result?.user
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            val token = tokenTask.result?.token
                            if (token != null) {
                                onResult(user, token, null)
                            }
                        } else {
                            onResult(null, null, tokenTask.exception?.message)
                        }
                    }
                }else{
                    onResult(null, null, task.exception?.message)
                }
            }
    }

    override suspend fun login(
        email: String,
        password: String,
        onResult: (UserModel?, String?,String?) -> Unit
    ) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let {
                        // Récupérer le token ID
                        it.getIdToken(true).addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val idToken = tokenTask.result?.token
                                // Appeler le callback avec l'utilisateur et le token
                                val user = UserModel(
                                    uid = it.uid,
                                    email = it.email,
                                    displayName = null
                                )
                                onResult(user, idToken, null)
                            } else {
                                onResult(null, null,"Failed to retrieve token")
                            }
                        }
                    }
                } else {
                    onResult(null, null,task.exception?.message ?: "Login failed")
                }
            }
        } catch (e: Exception) {
            onResult(null, null,e.message)
        }
    }
    override suspend fun signOut() {
        auth.signOut()
    }
}