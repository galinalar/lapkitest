package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    @SerializedName("IDQuestion")
    val id_question: Int,
    @SerializedName("Question")
    val question: String,
    @SerializedName("IDType")
    val id_type: Int,
    @SerializedName("IDRubric")
    val id_rubric: Int
) : Parcelable