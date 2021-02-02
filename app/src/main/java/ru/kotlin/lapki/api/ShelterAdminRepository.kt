package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ErrorResponse
import ru.kotlin.lapki.api.responses.ShelterAccountResponse
import ru.kotlin.lapki.api.responses.ShelterAdminResponse
import java.lang.IllegalStateException

object ShelterAdminRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): ShelterAdminResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_SHELTER_ADMIN_URL)
                                    .post(
                                            FormBody.Builder().add("id", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ShelterAdminResponse::class.java
            )
    fun add(idu: String, ids: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.ADD_ADMIN_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("idU", idu)
                                                add("idS", ids)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ErrorResponse::class.java
            )
    fun change(idu: String, ids: String, id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CHANGE_ADMIN_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("idU", idu)
                                                add("idS", ids)
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ErrorResponse::class.java
            )
    fun del(id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.DELETE_ADMIN_URL)
                                    .post(
                                            FormBody.Builder().add("id", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ErrorResponse::class.java
            )
}