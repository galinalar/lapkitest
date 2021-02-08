package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ModifyResponse
import java.lang.IllegalStateException

object RequestVolModifyRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun add(id_user: String, id_shelter: String, comment: String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.ADD_REQUEST_VOL_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("idu", id_user)
                                                add("ids", id_shelter)
                                                add("comment", comment)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )
}