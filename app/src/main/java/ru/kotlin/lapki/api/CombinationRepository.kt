package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.entities.AnswerResult
import ru.kotlin.lapki.api.responses.AnswerResultResponse
import ru.kotlin.lapki.api.responses.ResultResponse
import ru.kotlin.lapki.api.responses.ValuesResponse
import java.lang.IllegalStateException

object CombinationRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun getUserAnswer(iduser: String): AnswerResultResponse= Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_USER_ANSWER_ID_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idu", iduser)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        AnswerResultResponse::class.java
    )
    fun getPetAnswer(idpet: String): AnswerResultResponse= Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_PET_ANSWER_ID_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idp", idpet)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        AnswerResultResponse::class.java
    )
    fun getValueComb(iduseranswer: String, idpetanswer: String): ValuesResponse= Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_VALUE_COMBINATION_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idu", iduseranswer)
                        add("idp", idpetanswer)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        ValuesResponse::class.java
    )
    fun getValueCombNeg(iduseranswer1: String, iduseranswer2: String): ValuesResponse= Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_VALUE_COMBINATION_NEG_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idu1", iduseranswer1)
                        add("idu2", iduseranswer2)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        ValuesResponse::class.java
    )
    fun setResult(iduser: String, idpet: String, opt: String, train: String, fam: String, env: String, ch: String, honest: String, result: String): ResultResponse= Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.SET_RESULT_URL)
                .post(
                    FormBody.Builder().apply {
                        add("iduser", iduser)
                        add("idpet", idpet)
                        add("opt", opt)
                        add("train", train)
                        add("fam", fam)
                        add("env", env)
                        add("ch", ch)
                        add("honest", honest)
                        add("result", result)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        ResultResponse::class.java
    )
}