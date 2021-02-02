package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class ErrorResponse (
        @SerializedName("error")
        val isError: Boolean
)