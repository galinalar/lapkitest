package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

import ru.kotlin.lapki.api.entities.ShelterRole
import ru.kotlin.lapki.api.entities.UserID

data class ShelterRoleResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("role")
        val role: List<ShelterRole>
)