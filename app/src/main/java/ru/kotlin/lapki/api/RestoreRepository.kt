package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.LoginResponse
import ru.kotlin.lapki.api.responses.RestoreResponse
import java.lang.IllegalStateException

object RestoreRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun check(login: String, email: String): LoginResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.RESTORE_PASSWORD_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("login", login)
                                                add("email", email)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    LoginResponse::class.java
            )
    fun restore(id: String, password: String): RestoreResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CHANGE_PASSWORD_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("id", id)
                                                add("pass", Cryption().encrypt(password) ?: "")
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RestoreResponse::class.java
            )
}