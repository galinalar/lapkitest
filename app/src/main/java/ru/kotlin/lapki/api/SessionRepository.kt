package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.LoginResponse
import ru.kotlin.lapki.api.responses.SessionResponse
import java.lang.IllegalStateException

object SessionRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun create(id: String): SessionResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CREATE_SESSION_URL)
                                    .post(
                                            FormBody.Builder().add("id", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    SessionResponse::class.java
            )
    fun get(idsession: String): SessionResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_SESSION_URL)
                                    .post(
                                            FormBody.Builder().add("id", idsession).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    SessionResponse::class.java
            )

}