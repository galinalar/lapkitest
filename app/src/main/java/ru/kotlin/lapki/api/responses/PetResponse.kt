package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Pet


data class PetResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("list")
        val list: List<Pet>
)