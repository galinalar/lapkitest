package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerResult(
    @SerializedName("IDTest")
    val id_test: Long,
    @SerializedName("IDAnswer")
    val id_answer: Int,
    @SerializedName("IDQ")
    val id_question: Int,
    @SerializedName("IDRubric")
    val id_rubric: Int
) : Parcelable