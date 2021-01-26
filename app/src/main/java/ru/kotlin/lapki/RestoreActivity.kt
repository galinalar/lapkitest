package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forget.*
import kotlinx.android.synthetic.main.activity_forget.activity_authorization_login
import kotlinx.android.synthetic.main.activity_forget.mist
import kotlinx.android.synthetic.main.activity_forget.pass
import kotlinx.android.synthetic.main.activity_forget.ret
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class RestoreActivity : AppCompatActivity() {
    val URL = "https://192.168.0.76/lapki/v1/?op=getusersemail"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)
        re.setOnClickListener{
            rec()
        }
        ret.setOnClickListener{
            startActivity(Intent(this, ZzzAvtorization::class.java))
        }
    }
       private fun rec(){
        val l = activity_authorization_login.text.toString()
        val p = pass.text.toString()
        val p2 = pass2.text.toString()
        val e = email.text.toString()
        if((p!="") and (l!="") and (p2!="") and (e!="") and (p==p2)) {
            var form = FormBody.Builder().add("login", l)
            form.add("email", e)
            val request: Request = Request.Builder().url(URL).post(form.build()).build()
            println(request)
            println(l)
            println(e)
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        val iduser = (JSONObject(json).getJSONArray("users").getJSONObject(0).get("IDUser")).toString()
                        сhange(iduser,p)
                        println(p)
                    }
                    else mist.setText("Вы ввели неверные данные")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })
        }else mist.setText("Введите значения")

    }
    private fun сhange(idu:String, p:String){
        val URL2 = "https://192.168.0.76/lapki/v1/?op=changepass"
        var okHttpClient2 = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        val intent = Intent(this, ZzzAvtorization::class.java)
        val pe = Cryption()
        val pee = pe.encrypt(p).toString()
        var form2 = FormBody.Builder().add("id", idu)
        form2.add("pass", pee)
        val request: Request = Request.Builder().url(URL2).post(form2.build()).build()
        okHttpClient2.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                println(json)
                if ((JSONObject(json).get("error")).toString()=="false") {
                    startActivity(intent)
                }
                else mist.setText("Что-то пошло не так")
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
    }
}