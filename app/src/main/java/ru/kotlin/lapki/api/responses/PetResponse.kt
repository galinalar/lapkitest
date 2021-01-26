package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.Pet
import ru.kotlin.lapki.Shelter

data class PetResponse(
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("list")
        val list: List<Pet>
)