package tg.ulcrsandroid.carspooling.domain.usecases.chat

import androidx.lifecycle.LiveData
import tg.ulcrsandroid.carspooling.core.models.ChatModel
import tg.ulcrsandroid.carspooling.data.repository.chat.ChatRepository

class GetUserChatUseCase(private val chatRepository: ChatRepository) {

    fun execute(userId: String, onResult: (List<ChatModel>) -> Unit) {
        return chatRepository.getChatsForUser(userId,onResult)
    }
}