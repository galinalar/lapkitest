package ru.kotlin.lapki.api.responses

import com.google.gson.annotations.SerializedName
import ru.kotlin.lapki.api.entities.Results

data class ResultDataResponse (
    @SerializedName("error")
    val isError: Boolean,
    @SerializedName("results")
    val result: List<Results>
)