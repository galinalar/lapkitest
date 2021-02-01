package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.PetAccountResponse
import ru.kotlin.lapki.api.responses.UserAccountResponse
import java.lang.IllegalStateException

object UserAccountRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(id: String): UserAccountResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.GET_USER_URL)
                                    .post(
                                            FormBody.Builder().add("id", id).build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    UserAccountResponse::class.java
            )
}