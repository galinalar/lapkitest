package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.ShelterAccaunt

data class ShelterAccauntResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("shelter")
    val shelter: List<ShelterAccaunt>
)