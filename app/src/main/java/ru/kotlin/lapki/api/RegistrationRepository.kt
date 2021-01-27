package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.RestoreResponse
import ru.kotlin.lapki.api.responses.SessionResponse
import java.lang.IllegalStateException

object RegistrationRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun registration(name: String, surname: String, birthday: String,
                     sex: String, login: String, password: String, city: String, email: String, telephone: String): SessionResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.ADD_USER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("name", name)
                                                add("lname", surname)
                                                add("bd", birthday)
                                                add("sex", sex)
                                                add("login", login)
                                                add("pass", Cryption().encrypt(password) ?: "")
                                                add("city", city)
                                                add("email", email)
                                                add("tel", telephone)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    SessionResponse::class.java
            )
}