package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShelterType(
        @SerializedName("IDType")
        val id_type: Int,
        @SerializedName("Type")
        val type: String
) : Parcelable