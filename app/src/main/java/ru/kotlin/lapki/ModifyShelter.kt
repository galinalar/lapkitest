package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_addshelter.*
import kotlinx.android.synthetic.main.activity_addshelter.city
import kotlinx.android.synthetic.main.activity_addshelter.des
import kotlinx.android.synthetic.main.activity_addshelter.name

import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.entities.ShelterAccount
import java.io.IOException


data class Type(val IDType: String, val Type: String)
data class ParseType(val error: String, val type:List<Type>)
data class Role(val IDRole: String,  override val Status: String):  CommonSpinnerElementSt
data class ParseRole(val error: String, val role:List<Role>)
class ModifyShelter : AppCompatActivity() {
    lateinit var session: SessionManager
    val URL = "https://192.168.0.76/lapki/v1/?op=addshelter"
    val URL2 = "https://192.168.0.76/lapki/v1/?op=gettype"
    val URL3 = "https://192.168.0.76/lapki/v1/?op=getrole"
    val URL4 = "https://192.168.0.76/lapki/v1/?op=getshelter"
    val URL5 = "https://192.168.0.76/lapki/v1/?op=chshelter"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addshelter)
        session = SessionManager(applicationContext)
        val dayss: Array<Int> = Array(31, {i->i+1})
        val months: Array<Int> = Array(12, {i->i+1})
        val ad = ArrayAdapter(this, android.R.layout.simple_spinner_item,dayss)
        val ad2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,months)
        val sp: Spinner = spinnerS
        val sp2: Spinner = spinner2S
        sp.adapter = ad
        sp2.adapter= ad2
        if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") {spinner4.visibility = View.VISIBLE
            st.visibility = View.VISIBLE}

        val request: Request = Request.Builder().url(URL2).build()
        println(request)
        var ty:Array<String> = arrayOf("")
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                println(json)

                if ((JSONObject(json).get("error")).toString()=="false") {
                    var gson = Gson()
                    val listtype = gson?.fromJson(json, ParseType::class.java)
                                        this@ModifyShelter.runOnUiThread {
                        ty = Array(listtype.type.size, {i->listtype.type[i].Type})
                                            println(ty[0])
                                            val ad3 = ArrayAdapter(this@ModifyShelter, android.R.layout.simple_spinner_item,ty)
                                            val sp3: Spinner = spinner3
                                            sp3.adapter = ad3
                   }


                    }
                                else println("ошибка")
            }


            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
        var ro:Array<String> = arrayOf("")
        val request2: Request = Request.Builder().url(URL3).build()
        okHttpClient.newCall(request2).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                println(json)

                if ((JSONObject(json).get("error")).toString()=="false") {
                    var gson = Gson()
                    val listtype = gson?.fromJson(json, ParseRole::class.java)
                                        this@ModifyShelter.runOnUiThread {
                                            ro = Array(listtype.role.size, {i->listtype.role[i].Status})
                                            println(ty[0])
                                            val ad4 = ArrayAdapter(this@ModifyShelter, android.R.layout.simple_spinner_item,ro)
                                            val sp4: Spinner = spinner4
                                            sp4.adapter = ad4
                   }


                    }
                                else println("ошибка")
            }


            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
        if (session.getMod()=="change"){
            mod.setText("Сохранить")
            vh.setText("Изменение данных приюта")
            var form = FormBody.Builder().add("ids", session.getShelter())
            println(session.getShelter())
            val request: Request = Request.Builder().url(URL4).post(form.build()).build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        val u = (JSONObject(json).getJSONArray("shelter").getJSONObject(0)).toString()
                        println(u)
                        var gson = Gson()
                        val uu = gson?.fromJson(u, ShelterAccount::class.java)
                        println( uu.shelter_name)
                        name.setText(uu.shelter_name)
                       // val stringArray: Array<String> = uu.birth_date.split("-".toRegex()).toTypedArray()
                      //  bdate.setText(stringArray[0])
                       // val day = stringArray[2].toInt()-1
                       // spinnerS.setSelection(stringArray[2].toInt()-1)
                       //spinner2S.setSelection((stringArray[1].toInt()-1).toInt())
                       // println("String Arra $day ${stringArray[1].toInt()}")
                        spinner3.setSelection(uu.type_id)
                        city.setText(uu.city)
                        println("Discribe ${uu.describe}")
                        if (uu.describe!=null) {this@ModifyShelter.runOnUiThread {des.setText(uu.describe)}}
                        spinner4.setSelection(uu.role)
                    }
                    else mist.setText("Что-то пошло не так")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })

        }else {mod.setText("Добавить")
            vh.setText("Добавление приюта")
        }
        mod.setOnClickListener{
            if (session.getMod()=="add"){
            addS()}
            else modif()
        }
        ret.setOnClickListener {  if (session.getMod()=="add"){
            startActivity(Intent(this, MainActivity::class.java))}
        else startActivity(Intent(this, ShelterAccountActivity::class.java))}


    }
    val LOG_TAG = "myLogs"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("MOD", vh.getText().toString())
        outState.putString("NAME", name.getText().toString())
        outState.putString("YEAR", bdate.getText().toString())
        outState.putString("CITY", city.getText().toString())
        outState.putString("DES", des.getText().toString())
        outState.putString("BUT", mod.getText().toString())
        outState.putInt("DAY", spinnerS.selectedItemId.toInt())
        outState.putInt("MON", spinner2S.selectedItemId.toInt())
        outState.putInt("TYPE", spinner3.selectedItemId.toInt())
        outState.putInt("ROLE", spinner4.selectedItemId.toInt())
        Log.d(LOG_TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        vh.setText(savedInstanceState.getString("MOD"))
        name.setText(savedInstanceState.getString("NAME"))
        bdate.setText(savedInstanceState.getString("YEAR"))
        city.setText(savedInstanceState.getString("CITY"))
        des.setText(savedInstanceState.getString("DES"))
        mod.setText(savedInstanceState.getString("BUT"))
        spinnerS.setSelection(savedInstanceState.getInt("DAY"))
        spinner2S.setSelection(savedInstanceState.getInt("MON"))
        spinner3.setSelection(savedInstanceState.getInt("TYPE"))
        spinner4.setSelection(savedInstanceState.getInt("RoOLE"))
        Log.d(LOG_TAG, "onRestoreInstanceState")
    }
private fun addS(){
    val n = name.text.toString()
    val year = bdate.text.toString()
    val c = city.text.toString()
    val day = spinnerS.selectedItem.toString()
    val mon = spinner2S.selectedItem.toString()
    val descr = des.text.toString()
    val tt = spinner3.selectedItemId.toInt() +1
    val ych = setOf('.','/','-')

    if((n!="") and (n!="Введите свое имя") and (year!="") and (c!="") and (c!="Введите свой город") and (year.any(ych::contains)==false)) {
        if ((year.toInt()<=2020) and (year.toInt()>=1920)){
            val dd:String
            val mm:String
            if (day.length == 1) {dd = "0"+day} else dd = day
            if (mon.length == 1) {mm = "0"+mon} else mm = mon
            val bd: String = year+"-"+mm+"-"+dd
            var form = FormBody.Builder().add("name", n)
            form.add("bd", bd)
            form.add("city", c)
            form.add("desc", descr)
            form.add("type", tt.toString())
            println("$name, $bd, $c, $descr, ${tt.toString()}")
            val inten = Intent(this, ShelterAccount::class.java)
            val request: Request = Request.Builder().url(URL).post(form.build()).build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        val idsh = (JSONObject(json).get("message").toString())
                        session.Shelter(idsh)
                        startActivity(inten)
                    }
                    else mist.setText("Что-то пошло не так")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })
            mist.setText("ok")}else  mist.setText("Введите верный год")
    } else  mist.setText("Неверно введены данные")
}
    private fun modif(){
        val n = name.text.toString()
        val year = bdate.text.toString()
        val c = city.text.toString()
        val day = spinnerS.selectedItem.toString()
        val mon = spinner2S.selectedItem.toString()
        val descr = des.text.toString()
        val tt = spinner3.selectedItemId.toInt() +1
        val rol = spinner4.selectedItemId.toInt() +1
        val ych = setOf('.','/','-')

        if((n!="") and (year!="") and (c!="") and (year.any(ych::contains)==false)) {
            if ((year.toInt()<=2020) and (year.toInt()>=1920)){
                val dd:String
                val mm:String
                if (day.length == 1) {dd = "0"+day} else dd = day
                if (mon.length == 1) {mm = "0"+mon} else mm = mon
                val bd: String = year+"-"+mm+"-"+dd
                var form = FormBody.Builder().add("name", n)
                form.add("bd", bd)
                form.add("city", c)
                form.add("desc", descr)
                form.add("type", tt.toString())
                form.add("role", rol.toString())
                form.add("id", session.getShelter())
                println("$name, $bd, $c, $descr, $tt $rol")
                val request: Request = Request.Builder().url(URL5).post(form.build()).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        val json = response?.body()?.string()
                        println(json)
                        if ((JSONObject(json).get("error")).toString()=="false") {

                        }
                        else mist.setText("Что-то пошло не так")
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println(e.toString())
                    }

                })
                mist.setText("ok")}else  mist.setText("Введите верный год")
        } else  mist.setText("Неверно введены данные")
    }
}