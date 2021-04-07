package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Results(
    @SerializedName("IDComb")
    val id_comb: Long,
    @SerializedName("IDPet")
    val id_pet: Long,
    @SerializedName("Options")
    val options: Double,
    @SerializedName("Training")
    val training: Double,
    @SerializedName("Family")
    val family: Double,
    @SerializedName("Environment")
    val environment: Double,
    @SerializedName("Characters")
    val characters: Double,
    @SerializedName("Honesty")
    val honesty: Double,
    @SerializedName("Result")
    val result: Double,
    @SerializedName("DateTime")
    val date: Date
) : Parcelable