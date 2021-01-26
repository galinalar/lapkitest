package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_shelterac.*
import kotlinx.android.synthetic.main.activity_shelterac.bd
import kotlinx.android.synthetic.main.activity_shelterac.city
import kotlinx.android.synthetic.main.activity_shelterac.name
import kotlinx.android.synthetic.main.activity_shelterac.req
import kotlinx.android.synthetic.main.activity_user.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
data class ShelterAcD(val IDShelter: Int, val Name: String, val BirthDate: String, val City: String, val Discribe: String, val Photo: String, val Type: String,  val IDType: Int, val Role: Int)

class ShelterAc : AppCompatActivity() {
    lateinit var session: SessionManager
    val URL = "https://192.168.0.76/lapki/v1/?op=getshelter"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelterac)
        session = SessionManager(applicationContext)

        val idshel = session.getShelter()
        var form = FormBody.Builder().add("ids", idshel)
        val request: Request = Request.Builder().url(URL).post(form.build()).build()
        println(request)
        okHttpClient.newCall(request).enqueue(object : Callback {
         override fun onResponse(call: Call?, response: Response?) {
             val json = response?.body()?.string()
             println(json)
             if ((JSONObject(json).get("error")).toString()=="false") {

                 val u = (JSONObject(json).getJSONArray("shelter").getJSONObject(0)).toString()
                 println(u)
                 var gson = Gson()
                 val uu = gson?.fromJson(u, ShelterAcD::class.java)
                 println( uu.Name)
                 name.setText(uu.Name)
                 val bdd = bd.text.toString()+uu.BirthDate
                 bd.setText(bdd)
                 val c = city.text.toString()+uu.City
                 city.setText(c)
                 val d = des.text.toString()+uu.Discribe
                 des.setText(d)
                 val t = type.text.toString()+uu.Type
                 type.setText(t)
                    }
                    else println("Ошибка")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })
        pet.setOnClickListener{
            session.StartP("petshelter")
            startActivity(Intent(this, Listt::class.java))
        }
        ret.setOnClickListener{
            startActivity(Intent(this, Registr::class.java))
        }
        ch.setOnClickListener{
            session.Mod("change")
            startActivity(Intent(this, ModifyShelter::class.java))
        }
       del.setOnClickListener{
            session.TYPE_OBJ("shelter")
            Delete.Del(applicationContext)
        }
        req.setOnClickListener {
            session.Requests("req", "shelter")
            startActivity(Intent(this, Requests::class.java)) }
            }
}