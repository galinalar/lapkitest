package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ModifyResponse
import java.lang.IllegalStateException

object UserModifyRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun change(id: String, name: String, lname: String, bd: String, sex: String, login: String, pass: String, city: String, email: String,tel: String, desc: String, idrole: String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CHANGE_USER_URL )
                                    .post(
                                            FormBody.Builder().apply {
                                                add("id", id)
                                                add("idrole", idrole)
                                                add("name", name)
                                                add("lname", lname)
                                                add("bd", bd)
                                                add("sex", sex)
                                                add("login", login)
                                                add("pass", Cryption().encrypt(pass) ?: "")
                                                add("city", city)
                                                add("email", email)
                                                add("tel", tel)
                                                add("desc", desc)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )
}