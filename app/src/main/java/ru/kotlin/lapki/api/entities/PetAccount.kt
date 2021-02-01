package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PetAccount(
        @SerializedName("IDPet")
        val id_pet: Long,
        @SerializedName("Name")
        val pet_name: String,
        @SerializedName("BirthDate")
        val birth_date: Date,
        @SerializedName("Discribe")
        val pet_describe: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("IdShelt")
        val id_shelter: Int,
        @SerializedName("Shelter")
        val shelter: String,
        @SerializedName("Sex")
        val sex: String,
        @SerializedName("IdRole")
        val id_role: Int,
        @SerializedName("Status")
        val role: String,
        @SerializedName("Photo")
        val photo_url: String,
        @SerializedName("Active")
        val active: String,
        @SerializedName("Size")
        val size: Double,
        @SerializedName("Wool")
        val wool: String,
        @SerializedName("Hypo")
        val hypo: String,
        @SerializedName("Dressir")
        val dressir: String,
        @SerializedName("Dogs")
        val dogs: String,
        @SerializedName("Animal")
        val animal: String,
        @SerializedName("Child")
        val child: String,
        @SerializedName("Children")
        val children: String,
        @SerializedName("Teens")
        val teens: String,
        @SerializedName("Ills")
        val ills: String,
        @SerializedName("Sounds")
        val sounds: String,
        @SerializedName("Talkative")
        val talkative: String,
        @SerializedName("Breed")
        val breed: String
) : Parcelable