package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ErrorResponse
import java.lang.IllegalStateException

object RequestOwnerRepository {
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun OwnerAccept(id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_OWNER_ANSWER_URL)
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
    fun OwnerReject(id: String): ErrorResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_OWNER_ANSWER_URL)
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