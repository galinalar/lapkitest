package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ErrorResponse
import ru.kotlin.lapki.api.responses.RequestVolResponse
import java.lang.Error
import java.lang.IllegalStateException

object RequestVolRepository {
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun VolAccept(id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_VOL_ANSWER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "accept")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ErrorResponse ::class.java
            )
    fun VolReject(id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_VOL_ANSWER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "reject")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ErrorResponse ::class.java
            )
}