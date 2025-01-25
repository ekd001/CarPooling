package tg.ulcrsandroid.carspooling.core.models

/**
 * @author EKLOU
 *
 */
data class UserModel(
    val uid: String = "", // id du compte
    val email: String? = null, // email du compte
    val displayName: String? = null, // nom de l'utilisateur du compte
    var carLicenseNumber:String? = null //numero de permis
)