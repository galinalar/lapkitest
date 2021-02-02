package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserID(
        @SerializedName("IDUser")
        var id: Int,
        @SerializedName("IDRole")
        var role: Int
) : Parcelable