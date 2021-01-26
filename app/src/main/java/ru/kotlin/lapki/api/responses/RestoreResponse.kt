package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class RestoreResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("message")
        val message: String
)