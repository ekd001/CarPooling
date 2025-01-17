package tg.ulcrsandroid.carspooling.core.models

/**
 * @author EKLOU
 *
 */
data class UserModel(
    val uid: String, // id du compte
    val email: String?, // email du compte
    val displayName: String? // nom de l'utilisateur du compte
)