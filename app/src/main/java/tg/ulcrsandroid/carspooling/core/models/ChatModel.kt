package tg.ulcrsandroid.carspooling.core.models

import java.util.UUID

data class ChatModel (
    var chatId: String = UUID.randomUUID().toString(),
    var senderId: String = "",
    var receiverId: String = ""
)
