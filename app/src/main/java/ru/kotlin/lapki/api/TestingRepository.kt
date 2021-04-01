package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.AnswerResponse
import ru.kotlin.lapki.api.responses.ErrorResponse
import ru.kotlin.lapki.api.responses.QuestionResponse
import java.lang.Error
import java.lang.IllegalStateException

object TestingRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun getquestion(): QuestionResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_QUESTION_URL)
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        QuestionResponse::class.java
    )
    fun getanswer(): AnswerResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_ANSWER_URL)
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        AnswerResponse::class.java
    )
    fun setanswer(answ: String, idtest: String): ErrorResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.SET_ANSWER_URL)
                .post(
                    FormBody.Builder().apply {
                        add("arransw", answ)
                        add("idutest", idtest)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        ErrorResponse::class.java
    )
    fun getpetquestion(): QuestionResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_PET_QUESTION_URL)
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        QuestionResponse::class.java
    )
    fun getpetanswer(): AnswerResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_PET_ANSWER_URL)
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        AnswerResponse::class.java
    )
    fun setpetanswer(answ: String, idtest: String): ErrorResponse = Gson().fromJson(
        okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.SET_PET_ANSWER_URL)
                .post(
                    FormBody.Builder().apply {
                        add("arransw", answ)
                        add("idptest", idtest)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
        ErrorResponse::class.java
    )
}