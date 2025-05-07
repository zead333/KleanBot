package dev.pegasus.kleanbot.data.entities

import com.google.gson.annotations.SerializedName

data class OpenAIRequest(
    @SerializedName("model") val model: String,
    @SerializedName("messages") val messages: List<Message>,
    @SerializedName("temperature") val temperature: Double,
    @SerializedName("max_completion_tokens") val max_completion_tokens: Int
)