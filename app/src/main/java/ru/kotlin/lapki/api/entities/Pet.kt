package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Pet(
        @SerializedName("IDPet")
        val id_pet: Int,
        @SerializedName("Name")
        val pet_name: String,
        @SerializedName("BirthDate")
        val birth_date: Date,
        @SerializedName("IDType")
        val id_type: Int,
        @SerializedName("Shelter")
        val shelter: String,
        @SerializedName("IDRole")
        val id_role: Int,
        @SerializedName("Sex")
        val sex: String,
        @SerializedName("Status")
        val status: String,
        @SerializedName("Photo")
        val photo_url: String
) : Parcelable