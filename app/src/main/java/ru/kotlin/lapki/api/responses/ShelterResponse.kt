package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Shelter
import ru.kotlin.lapki.api.entities.UserID

data class ShelterResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("list")
        val list: List<Shelter>
)