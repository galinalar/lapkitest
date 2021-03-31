package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Question

data class QuestionResponse(
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("question")
    val question: List<Question>
)