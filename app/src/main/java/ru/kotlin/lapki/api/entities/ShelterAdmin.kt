package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShelterAdmin (
        @SerializedName("ID")
        val id: Int,
        @SerializedName("IDShelter")
        val id_shelter: Int,
        @SerializedName("IDUser")
        val id_user: Int
) : Parcelable