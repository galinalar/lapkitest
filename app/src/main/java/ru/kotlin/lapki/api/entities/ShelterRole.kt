package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShelterRole(
        @SerializedName("IDRole")
        val id_role: Int,
        @SerializedName("Status")
        val role: String
) : Parcelable
//: CommonSpinnerElementSt