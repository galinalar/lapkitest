package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
        @SerializedName("Name")
        val user_name: String,
        @SerializedName("LastName")
        val surname: String,
        @SerializedName("BirthDate")
        val birth_date: Date,
        @SerializedName("Sex")
        val sex: String,
        @SerializedName("Login")
        val login: String,
        @SerializedName("City")
        val city: String,
        @SerializedName("Telephone")
        val telephone: String,
        @SerializedName("Discribe")
        val describe: String,
        @SerializedName("Photo")
        val photo_url: String,
        @SerializedName("Email")
        val email: String
) : Parcelable