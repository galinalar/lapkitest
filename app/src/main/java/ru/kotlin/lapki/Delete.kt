package ru.kotlin.lapki

import android.content.Context
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shelterac.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object Delete {
    val URL = "https://192.168.0.76/lapki/v1/?op=deleteobject"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    fun Del(context: Context){
        val session = SessionManager(context)

        var form = FormBody.Builder().add("obj", session.getObject())
        if (session.getObject()=="shelter") {
            form.add("ids", session.getShelter())
        }else if (session.getObject()=="pet") {
            form.add("ids", session.getPet())
        }else form.add("ids", session.getUserDetails().get(SessionManager.KEY_ID))
            val request: Request = Request.Builder().url(URL).post(form.build()).build()
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