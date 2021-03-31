package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Answers

data class AnswerResponse(
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("answer")
    val answer: List<Answers>
)