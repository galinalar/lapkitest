package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.*

import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ShelterAccountResponse
import java.lang.IllegalStateException

object ShelterAccountRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): ShelterAccountResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_SHELTER_URL)
                                    .post(
                                            FormBody.Builder().add("ids", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ShelterAccountResponse::class.java
            )
}