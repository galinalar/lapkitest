package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.RequestVol

data class RequestVolResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("req")
        val request: List<RequestVol>
)