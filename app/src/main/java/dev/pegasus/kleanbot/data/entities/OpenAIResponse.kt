package dev.pegasus.kleanbot.data.entities

import com.google.gson.annotations.SerializedName

data class OpenAIResponse(
    @SerializedName("id") val id: String,
    @SerializedName("created") val created: Int,
    @SerializedName("model") val model: String,
    @SerializedName("choices") val choices: List<Choice>
)