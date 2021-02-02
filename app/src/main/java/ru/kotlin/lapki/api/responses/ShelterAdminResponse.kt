package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.ShelterAccount
import ru.kotlin.lapki.api.entities.ShelterAdmin

class ShelterAdminResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("admin")
        val admin: List<ShelterAdmin>
)