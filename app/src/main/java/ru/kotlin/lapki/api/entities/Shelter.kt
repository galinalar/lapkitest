package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Shelter(
        @SerializedName("IDShelter")
        val id_shelter: Int,
        @SerializedName("Name")
        val shelter_name: String,
        @SerializedName("BirthDate")
        val birth_date: Date,
        @SerializedName("City")
        val city: String,
        @SerializedName("Photo")
        val photo_url: String
) : Parcelable
        //CommonSpinnerElement