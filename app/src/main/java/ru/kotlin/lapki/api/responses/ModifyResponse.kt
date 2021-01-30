package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class ModifyResponse (
        @SerializedName("error")
        val isError: Boolean,
        @SerializedName("id")
        val id: Int
)