package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.*
import java.lang.IllegalStateException

object TimeRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): TimeResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_TIME_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idu", id)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        TimeResponse::class.java
    )
    fun set(id: String, time:String): TestResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.SET_TIME_URL)
                .post(
                    FormBody.Builder().apply {
                        add("iduser", id)
                        add("time", time)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        TestResponse::class.java
    )

}