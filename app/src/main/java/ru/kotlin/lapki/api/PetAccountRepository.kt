package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.PetAccountResponse
import ru.kotlin.lapki.api.responses.ShelterAccountResponse
import java.lang.IllegalStateException

object PetAccountRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): PetAccountResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_PET_URL)
                                    .post(
                                            FormBody.Builder().add("id", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    PetAccountResponse::class.java
            )
}