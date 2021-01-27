package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.Cryption

import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.LoginResponse
import ru.kotlin.lapki.api.responses.ShelterAccauntResponse
import java.io.IOException
import java.lang.IllegalStateException

object ShelterAccauntRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): ShelterAccauntResponse =
            Gson().fromJson(
                    LoginRepository.okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_SHELTER_URL)
                                    .post(
                                            FormBody.Builder().add("ids", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ShelterAccauntResponse::class.java
            )
}