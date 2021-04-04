package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.AnswerResult

data class AnswerResultResponse(
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("answer")
    val answer: List<AnswerResult>
)