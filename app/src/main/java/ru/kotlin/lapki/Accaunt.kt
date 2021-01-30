package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.SessionRepository
import java.io.IOException

data class User(val Name: String, val LastName: String, val BirthDate: String, val Sex: String, val Login: String, val City: String, val Telephone: String, val Discribe: String, val Photo: String, val Email: String)
class Accaunt : AppCompatActivity() {
    lateinit var session: SessionManager
    val URL = "https://192.168.0.76/lapki/v1/?op=users"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        session = SessionManager(applicationContext)
        println(session.getSessionrDetails())
        //не работает
        try {
            var session = SessionManager(applicationContext)
            val sessionResponse = SessionRepository.get(
                    session.getSessionrDetails().toString()

            )
            if (sessionResponse.isError) throw IllegalAccessError()

            val ses = sessionResponse.id.toString()


        var form = FormBody.Builder().add("id", ses)
        val request: Request = Request.Builder().url(URL).post(form.build()).build()
        println(request)
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                println(json)
                    val u = (JSONObject(json).getJSONArray("users").getJSONObject(0)).toString()
                println(u)
                    var gson = Gson()
                    val uu = gson?.fromJson(u, User::class.java)
                    println( uu.Name)
                activity_user_name.setText(uu.Name)
                activity_user_surname.setText(uu.LastName)
                activity_user_login.setText(uu.Login)
                val bdd = activity_user_birthday.text.toString()+uu.BirthDate
                activity_user_birthday.setText(bdd)
                val ss = activity_user_sex.text.toString()+uu.Sex
                activity_user_sex.setText(ss)
                val c = activity_user_city.text.toString()+uu.City
                activity_user_city.setText(c)
                val tt = activity_user_telephone.text.toString()+uu.Telephone
                activity_user_telephone.setText(tt)
                val e = activity_user_email.text.toString()+uu.Email
                activity_user_email.setText(e)
                val diss = activity_user_describe.text.toString()+uu.Discribe
                activity_user_describe.setText(diss)

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
            activity_user_delete.setOnClickListener{
            session.TYPE_OBJ("users")
            Delete.Del(applicationContext)
        }
            activity_user_request.setOnClickListener {
            session.Requests("req", "users")
           startActivity(Intent(this, Requests::class.java)) }
        } catch (exception: Throwable) {
            runOnUiThread {
                val error = when (exception) {
                    is IllegalAccessError -> "Что-то пошло не так"
                    else -> "Все наебнулось2"
                }
                println(error)
            }
        }

    }

    }