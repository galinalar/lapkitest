package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.UserID

data class LoginResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("users")
        val users: List<UserID>
)