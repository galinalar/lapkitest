package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName

data class TestResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("test")
    val id_test: Int
)