package tg.ulcrsandroid.carspooling.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import tg.ulcrsandroid.carspooling.core.models.ChatModel

class ChatDataSource(db: FirebaseFirestore) {
    private val chatCollection = db.collection("chats")
    private val messageCollection = db.collection("messages")

    fun createChat(senderId: String, receiverId: String): Task<DocumentReference> {
        val chat = hashMapOf("senderId" to senderId, "receiverId" to receiverId)
        return chatCollection.add(chat)
    }

    fun getChatsForUser(userId: String, onResult: (List<ChatModel>) -> Unit) {
        val chatsCollection = FirebaseFirestore.getInstance().collection("chats")

        val chatList = mutableListOf<ChatModel>()

        // Requête 1 : Rechercher où l'utilisateur est l'expéditeur
        chatsCollection.whereEqualTo("senderId", userId).get()
            .addOnSuccessListener { senderChats ->
                for (document in senderChats) {
                    document.toObject(ChatModel::class.java)?.let { chatList.add(it) }
                }

                // Requête 2 : Rechercher où l'utilisateur est le destinataire
                chatsCollection.whereEqualTo("receiverId", userId).get()
                    .addOnSuccessListener { receiverChats ->
                        for (document in receiverChats) {
                            document.toObject(ChatModel::class.java)?.let { chatList.add(it) }
                        }
                        // Retourner la liste des chats fusionnée
                        onResult(chatList)
                    }
            }
    }

    fun sendMessage(chatId: String, message: String, senderId: String): Task<DocumentReference> {
        val messageMap = hashMapOf(
            "chatId" to chatId,
            "senderId" to senderId,
            "message" to message,
            "timestamp" to FieldValue.serverTimestamp()
        )
        return messageCollection.add(messageMap)
    }

    // Récupérer les messages d'un chat
    fun getMessages(chatId: String): Query {
        return messageCollection.whereEqualTo("chatId", chatId).orderBy("timestamp")
    }
}