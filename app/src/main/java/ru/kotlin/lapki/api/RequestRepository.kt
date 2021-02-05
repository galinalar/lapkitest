package ru.kotlin.lapki.api

import android.content.Context
import com.google.gson.Gson
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.LoginResponse
import ru.kotlin.lapki.api.responses.RequestOwnerResponse
import ru.kotlin.lapki.api.responses.RequestVolResponse
import java.io.IOException
import java.lang.IllegalStateException
import java.security.acl.Owner

object RequestRepository {
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun VolShelter(id: String): RequestVolResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_VOL_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "shelter")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestVolResponse ::class.java
            )
    fun VolUser(id: String): RequestVolResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_VOL_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "user")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestVolResponse ::class.java
            )
    fun VolReq(id: String): RequestVolResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_VOL_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "request")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestVolResponse ::class.java
            )
    fun OwnerShelter(id: String): RequestOwnerResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_OWNER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "shelter")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestOwnerResponse ::class.java
            )
    fun OwnerUser(id: String): RequestOwnerResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_OWNER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "user")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestOwnerResponse ::class.java
            )
    fun OwnerPet(id: String): RequestOwnerResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.REQUEST_OWNER_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("obj", "pet")
                                                add("id", id)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    RequestOwnerResponse ::class.java
            )
}