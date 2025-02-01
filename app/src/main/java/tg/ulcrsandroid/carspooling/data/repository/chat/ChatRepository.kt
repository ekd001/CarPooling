package tg.ulcrsandroid.carspooling.data.repository.chat

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import tg.ulcrsandroid.carspooling.core.models.ChatModel
import tg.ulcrsandroid.carspooling.data.datasource.ChatDataSource

class ChatRepository(private val chatDataSource: ChatDataSource) {
    fun createChat(senderId: String, receiverId: String): Task<DocumentReference> {
        return chatDataSource.createChat(senderId, receiverId)
    }

    // Récupérer tous les chats d'un utilisateur
    fun getChatsForUser(userId: String, onResult: (List<ChatModel>) -> Unit) {
        return chatDataSource.getChatsForUser(userId, onResult)
    }

    // Envoyer un message
    fun sendMessage(chatId: String, message: String, senderId: String): Task<DocumentReference> {
        return chatDataSource.sendMessage(chatId, message, senderId)
    }

    // Récupérer les messages d'un chat
    fun getMessages(chatId: String): Query {
        return chatDataSource.getMessages(chatId)
    }
}