package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestOwner (
        @SerializedName("IDReq")
        val id_req: Int,
        @SerializedName("IDUser")
        val id_user: Int,
        @SerializedName("IDPet")
        val id_pet: Int,
        @SerializedName("Comment")
        val comment: String,
        @SerializedName("IDStatus")
        val id_status: Int,
        @SerializedName("IDType")
        val id_type: Int,
        @SerializedName("Status")
        val role: String,
        @SerializedName("IDShelter")
        val id_shelter: Int,
        @SerializedName("NameP")
        val pet_name: String,
        @SerializedName("NameU")
        val user_name: String,
        @SerializedName("LastName")
        val user_surname: String,
        @SerializedName("TypeName")
        val type_name: String,
        @SerializedName("Shelter")
        val shelter: String
) : Parcelable