package ru.kotlin.lapki

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shelterac.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.ApiScheme
import java.io.IOException

object Delete {
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun Del( obj: String, id: String){


        var form = FormBody.Builder().add("obj", obj)
        form.add("ids", id)
            val request: Request = Request.Builder().url(ApiScheme.DELETE_OBJECT_URL).post(form.build()).build()
            println(request)
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        println("ok")
                    }
                    else println("Ошибка")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })

    }
}