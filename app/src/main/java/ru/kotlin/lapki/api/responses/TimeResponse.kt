package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Answers

data class TimeResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("time")
    val time: Int
)