package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class ValuesResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("value")
    val value: Int
)