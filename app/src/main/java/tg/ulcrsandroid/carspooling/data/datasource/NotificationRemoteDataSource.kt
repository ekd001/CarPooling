package tg.ulcrsandroid.carspooling.data.datasource

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class NotificationRemoteDataSource(private val context: Context) {

    fun sendDemandReservationNotification(driverUid: String, onComplete: (Boolean) -> Unit) {
        val jsonBody = JSONObject().apply {
            put("app_id", "b6a8c024-67c0-4285-9b92-8f267e83d722")
            put("include_external_user_ids", JSONArray().put(driverUid)) // Utilise l'UID Firebase du conducteur
            put("headings", JSONObject().put("en", "Nouvelle réservation !"))
            put("contents", JSONObject().put("en", "Un passager a réservé un trajet. Consultez votre application."))
        }

        val request = object : JsonObjectRequest(
            Request.Method.POST,
            "https://onesignal.com/api/v1/notifications",
            jsonBody,
            { response ->
                Log.i("OneSignal", "Notification envoyée: $response")
                onComplete(true)
            },
            { error ->
                Log.i("OneSignal", "Erreur lors de l'envoi: ${error.message}")
                onComplete(false)
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf(
                    "Authorization" to "Basic os_v2_app_w2umajdhybbilg4sr4th5a6xelzu5ur435seykvqjanqsn33ksj4d5ccy2rcq2s75hdskvar7qrr3jjjr6bt3zzvvblqvjwu7joywky",
                    "Content-Type" to "application/json"
                )
            }
        }

        Volley.newRequestQueue(context).add(request)
    }
}