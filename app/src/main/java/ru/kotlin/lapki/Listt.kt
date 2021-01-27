package ru.kotlin.lapki

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

data class Shelter(val IDShelter: Int, override val Name: String, val BirthDate: String, val City: String, val Photo: String): CommonSpinnerElement

data class Pet(val IDPet: Int, val Name: String, val BirthDate: String, val Photo: String, val IDType: String, val Shelter: String, val IDRole: String, val Sex: String, val Status: String)
data class Parse(val error: String, val lists: List<Shelter>)
data class ParsePet(val error: String, val lists: List<Pet>)

class Listt : AppCompatActivity() {
    val URL = "https://192.168.0.76/lapki/v1/?op=getlist"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        session = SessionManager(applicationContext)
        println(session.getS())
        ret.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
        }
        if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") add.visibility = View.VISIBLE
        println("РОЛЬ")
        println(session.getUserDetails().get(SessionManager.KEY_ROLE))

        val form = FormBody.Builder().add("table", session.getS())
        form.add("id", session.getShelter())
        val request: Request = Request.Builder().url(URL).post(form.build()).build()
        println(request)
        var di: List<String> = mutableListOf<String>()
        var idd: List<Int> = mutableListOf<Int>()


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                println(json)

                if ((JSONObject(json).get("error")).toString() == "false") {
                    var gson = Gson()

                    if (session.getS() == "shelter") {
                        val listshel = gson?.fromJson(json, Parse::class.java)
                        di = Discr(listshel)
                        idd = idList(listshel)
                        mname.setText("Приюты")
                    } else {
                        val listshel = gson?.fromJson(json, ParsePet::class.java)
                        di = Discr2(listshel)
                        idd = idList2(listshel)
                        if (session.getS() == "pet") mname.setText("Подопечные") else if (session.getS() == "fpet") mname.setText("Потеряшки") else mname.setText(listshel.lists[0].Shelter)
                        println(session.getShelter())
                    }

                    //println(listshel.lists[0].Name)

                    println(di[0])
                    this@Listt.runOnUiThread {
                        recyclerView.adapter = CustomRecyclerAdapter(di, idd, this@Listt)
                    }
                } else println("ошибка")
            }


            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })


        println(di)
        add.setOnClickListener {
            if (session.getS() =="shelter"){
                session.Mod("add")
            startActivity(Intent(this, ModifyShelter::class.java))}
        }

    }

    private fun Discr(li: Parse): List<String> {
        val size = li.lists.size
        val data = mutableListOf<String>()
        (0..size - 1).forEach { i -> data.add("${li.lists[i].Name} Город: ${li.lists[i].City} Дата рождения: ${li.lists[i].BirthDate}") }

        return data
    }

    private fun idList(li: Parse): List<Int> {
        val size = li.lists.size
        val data = mutableListOf<Int>()
        (0..size - 1).forEach { i -> data.add(li.lists[i].IDShelter) }
        return data
    }

    private fun Discr2(li: ParsePet): List<String> {
        val size = li.lists.size
        val data = mutableListOf<String>()
        (0..size - 1).forEach { i -> data.add("${li.lists[i].Name} Дата рождения: ${li.lists[i].BirthDate} Статус: ${li.lists[i].Status} Пол: ${li.lists[i].Sex} Приют: ${li.lists[i].Shelter}") }

        return data
    }

    private fun idList2(li: ParsePet): List<Int> {
        val size = li.lists.size
        val data = mutableListOf<Int>()
        (0..size - 1).forEach { i -> data.add(li.lists[i].IDPet) }
        return data
    }


}



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: Button? = null
        var Secret: TextView? = null

        init {
            largeTextView = itemView?.findViewById(R.id.shel)
            Secret = itemView?.findViewById(R.id.ii)
        }
    }
}
