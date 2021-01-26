package ru.kotlin.lapki.api

import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_authorization.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.Cryption
import ru.kotlin.lapki.SessionManager
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.LoginResponse
import java.io.IOException
import java.lang.IllegalStateException

object LoginRepository {

    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun login(login: String, password: String): LoginResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_USERS_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("login", login)
                                                add("pass", Cryption().encrypt(password) ?: "")
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    LoginResponse::class.java
            )

}