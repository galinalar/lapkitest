package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.ShelterType


data class ShelterTypeResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("type")
        val type: List<ShelterType>
)