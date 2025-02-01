package tg.ulcrsandroid.carspooling.core.models

import java.util.UUID

data class MessageModel(
    var messageId: String = UUID.randomUUID().toString(),
    var message: String = "",
    var timestamp: Long = System.currentTimeMillis()
)
