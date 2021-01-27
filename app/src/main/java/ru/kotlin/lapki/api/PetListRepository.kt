package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.PetResponse
import ru.kotlin.lapki.api.responses.ShelterResponse
import java.lang.IllegalStateException

object PetListRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun allpets(): PetResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_LIST_URL)
                                    .post(
                                            FormBody.Builder().add("table", "pet").build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    PetResponse::class.java
            )
    fun losepets(): PetResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_LIST_URL)
                                    .post(
                                            FormBody.Builder().add("table", "fpet").build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    PetResponse::class.java
            )
    fun petshelter(idshelter: String): PetResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_LIST_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("table", "petshelter")
                                                add("id", idshelter)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    PetResponse::class.java
            )
}