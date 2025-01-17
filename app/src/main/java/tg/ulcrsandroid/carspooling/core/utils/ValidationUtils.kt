package tg.ulcrsandroid.carspooling.core.utils

/**
 * Les utilitaires pour tout l'application
 * isValidEmail : valide un email
 * isValidPassword : valide un mot de passe
 *
 * @author EKLOU
 */
object ValidationUtils {
    /**
     * Renvoie <code>true</code> si l'adresse email est valide.
     *
     *
     * @param <code>email</code> Email a vérifier
     * @return <code>true</code> si l'adresse email respecte la convention de android
     */
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String):Boolean{
        return  password.length >= 4
    }
}