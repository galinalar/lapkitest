package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class ResultResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("result")
    val result: Long
)