package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.UserID

data class SessionResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("users")
        val id: Long
)