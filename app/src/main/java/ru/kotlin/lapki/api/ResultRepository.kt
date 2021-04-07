package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.AnswerResultResponse
import ru.kotlin.lapki.api.responses.ResultDataResponse
import java.lang.IllegalStateException

object ResultRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    fun getWithPet(iduser: String, idpet: String): ResultDataResponse = Gson().fromJson(
        TestingRepository.okHttpClient.newCall(
            Request.Builder()
                .url(ApiScheme.GET_RESULT_PET_URL)
                .post(
                    FormBody.Builder().apply {
                        add("idu", iduser)
                        add("idp", idpet)
                    }.build()
                )
                .build()
        ).execute().body()?.string() ?: throw IllegalStateException("Ошибка"),
        ResultDataResponse::class.java
    )
}