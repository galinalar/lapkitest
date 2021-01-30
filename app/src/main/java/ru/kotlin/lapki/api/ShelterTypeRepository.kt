package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ShelterResponse
import ru.kotlin.lapki.api.responses.ShelterTypeResponse
import java.lang.IllegalStateException

object ShelterTypeRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(): ShelterTypeResponse = Gson().fromJson(
            okHttpClient.newCall(
                    Request.Builder()
                            .url(ApiScheme.GET_SHELTER_TYPE_URL)
                            .build()
            ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
            ShelterTypeResponse::class.java
    )
}