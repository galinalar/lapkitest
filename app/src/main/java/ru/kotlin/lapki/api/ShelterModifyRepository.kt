package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.ModifyPet
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ModifyResponse
import ru.kotlin.lapki.api.responses.SessionResponse
import java.lang.IllegalStateException

object ShelterModifyRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun add(name: String, birthday: String, city: String, desc: String, type: String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.ADD_SHELTER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("name", name)
                                                add("bd", birthday)
                                                add("city", city)
                                                add("desc", desc)
                                                add("type", type)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )

    fun change(name: String, birthday: String, city: String, desc: String, type: String, role: String, id: String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CHANGE_SHELTER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("name", name)
                                                add("bd", birthday)
                                                add("city", city)
                                                add("desc", desc)
                                                add("type", type)
                                                add("role", role)
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )
}