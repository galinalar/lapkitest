package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Answers(
    @SerializedName("IDAnswer")
    val id_answer: Int,
    @SerializedName("IDQuestion")
    val id_question: Int,
    @SerializedName("Answer")
    val answer: String
) : Parcelable