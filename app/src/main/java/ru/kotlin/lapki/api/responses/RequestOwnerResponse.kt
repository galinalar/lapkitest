package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.RequestOwner

data class RequestOwnerResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("req")
        val request: List<RequestOwner>
)