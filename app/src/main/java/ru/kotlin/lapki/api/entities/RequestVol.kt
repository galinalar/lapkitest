package ru.kotlin.lapki.api.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestVol (
        @SerializedName("IDReq")
        val id_req: Int,
        @SerializedName("IDUser")
        val id_user: Int,
        @SerializedName("IDShelter")
        val id_shelter: Int,
        @SerializedName("Comment")
        val comment: String,
        @SerializedName("IDStatus")
        val id_status: Int,
        @SerializedName("Status")
        val role: String,
        @SerializedName("Shelter")
        val shelter: String,
        @SerializedName("NameU")
        val user_name: String,
        @SerializedName("LastName")
        val user_surname: String
) : Parcelable