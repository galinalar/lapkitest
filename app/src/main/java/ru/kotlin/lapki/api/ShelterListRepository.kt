package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ShelterResponse
import java.lang.IllegalStateException

object ShelterListRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun shelters(): ShelterResponse = Gson().fromJson(
                okHttpClient.newCall(
                        Request.Builder()
                                .url(ApiScheme.GET_LIST_URL)
                                .post(
                                        FormBody.Builder().apply {
                                            add("table", "shelter")
                                            add("id", "")
                                        }.build()
                                )
                                .build()
                ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                ShelterResponse::class.java
        )
    }
