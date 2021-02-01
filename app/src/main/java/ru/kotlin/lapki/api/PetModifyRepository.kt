package ru.kotlin.lapki.api

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Request
import ru.kotlin.lapki.UnsafeOkHttpClient
import ru.kotlin.lapki.api.responses.ModifyResponse
import java.lang.IllegalStateException

object PetModifyRepository {
    val okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun add( idshel: String, idrole: String, name: String, birthday: String, desc: String, breed: String, sex: String, act: String, si: String, wo: String, hy: String, dres: String, dog: String, ani: String, chi: String, chil: String, te: String,
              il: String, sou: String, tal: String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.ADD_PET_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("idshel", idshel)
                                                add("idrole", idrole)
                                                add("name", name)
                                                add("bd", birthday)
                                                add("desc", desc)
                                                add("por", breed)
                                                add("sex", sex)
                                                add("act", act)
                                                add("si", si)
                                                add("wo", wo)
                                                add("hy", hy)
                                                add("dres", dres)
                                                add("dog", dog)
                                                add("ani", ani)
                                                add("chi", chi)
                                                add("chil", chil)
                                                add("te", te)
                                                add("il", il)
                                                add("sou", sou)
                                                add("tal", tal)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )

    fun change(idshel: String, idrole: String, name: String, birthday: String, desc: String, breed: String, sex: String, act: String, si: String, wo: String, hy: String, dres: String, dog: String, ani: String, chi: String, chil: String, te: String,
               il: String, sou: String, tal: String, idpet:String): ModifyResponse =
            Gson().fromJson(
                    okHttpClient.newCall(
                            Request.Builder()
                                    .url(ApiScheme.CHANGE_PET_URL)
                                    .post(
                                            FormBody.Builder().apply {
                                                add("idshel", idshel)
                                                add("idrole", idrole)
                                                add("name", name)
                                                add("bd", birthday)
                                                add("desc", desc)
                                                add("por", breed)
                                                add("sex", sex)
                                                add("act", act)
                                                add("si", si)
                                                add("wo", wo)
                                                add("hy", hy)
                                                add("dres", dres)
                                                add("dog", dog)
                                                add("ani", ani)
                                                add("chi", chi)
                                                add("chil", chil)
                                                add("te", te)
                                                add("il", il)
                                                add("sou", sou)
                                                add("tal", tal)
                                                add("id", idpet)
                                            }.build()
                                    )
                                    .build()
                    ).execute().body()?.string() ?: throw IllegalStateException("Pizdec"),
                    ModifyResponse::class.java
            )
}