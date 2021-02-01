package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.PetAccount
import ru.kotlin.lapki.api.entities.User

class UserAccountResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("user")
        val user: List<User>
)