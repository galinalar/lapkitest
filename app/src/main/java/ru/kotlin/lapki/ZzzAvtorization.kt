package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authorization.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ZzzAvtorization : AppCompatActivity() {
    val URL = "https://192.168.0.76/lapki/v1/?op=getusers"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        vhod.setOnClickListener{
            avtor()
        }
        ret.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        fog.setOnClickListener{
            startActivity(Intent(this, RestoreActivity::class.java))
        }
    }
    val LOG_TAG = "myLogs"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("LOGIN", activity_authorization_login.getText().toString())
        outState.putString("PASS", pass.getText().toString())
        Log.d(LOG_TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activity_authorization_login.setText(savedInstanceState.getString("LOGIN"))
        pass.setText(savedInstanceState.getString("PASS"))
        Log.d(LOG_TAG, "onRestoreInstanceState")
    }
    private fun avtor(){
        val intent = Intent(this, StartActivity::class.java)
        val l = activity_authorization_login.text.toString()
        val p = pass.text.toString()
        if((p!="") and (l!="")) {
            val pe = Cryption()
            val pee = pe.encrypt(p).toString()
            var form = FormBody.Builder().add("login", l)
            form.add("pass", pee)
            val request: Request = Request.Builder().url(URL).post(form.build()).build()
            println(request)
            println(l)
            println(p)
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        val iduser = (JSONObject(json).getJSONArray("users").getJSONObject(0).get("IDUser")).toString()
                        val idrole = (JSONObject(json).getJSONArray("users").getJSONObject(0).get("IDRole")).toString()
                        println("iduser $iduser")
                        println("idrole $idrole")
                        val session = SessionManager(applicationContext)
                        session.CreateLoginSession(iduser,idrole)
                        startActivity(intent)


                    }
                    else mist.setText("Неверный логин или пароль")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })
        }else mist.setText("Введите значения")
        }
}