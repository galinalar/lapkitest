package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.PetAccount

class PetAccountResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("pet")
        val pet: List<PetAccount>
)