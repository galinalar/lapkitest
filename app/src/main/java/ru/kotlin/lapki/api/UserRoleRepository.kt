package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ShelterRoleResponse
import java.lang.IllegalStateException

object UserRoleRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun get(): ShelterRoleResponse = Gson().fromJson(
            okHttpClient.newCall(
                    Request.Builder()
                            .url(ApiScheme.GET_USER_ROLE_URL)
                            .build()
            ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
            ShelterRoleResponse::class.java
    )
}