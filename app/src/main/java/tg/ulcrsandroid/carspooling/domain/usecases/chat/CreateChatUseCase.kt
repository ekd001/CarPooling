package tg.ulcrsandroid.carspooling.domain.usecases.chat

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import tg.ulcrsandroid.carspooling.data.repository.chat.ChatRepository

class CreateChatUseCase(private val chatRepository: ChatRepository) {

    fun execute(senderId: String, receiverId: String): Task<DocumentReference> {
        return chatRepository.createChat(senderId, receiverId)
    }
}