package ru.kotlin.lapki

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_addpet.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.entities.Shelter
import ru.kotlin.lapki.api.responses.ShelterResponse
import java.io.IOException


class ModifyPet : AppCompatActivity() {
    lateinit var session: SessionManager
    val URL = "https://192.168.0.76/lapki/v1/?op=addpet"
    val URL2 = "https://192.168.0.76/lapki/v1/?op=getlist"
   val URL3 = "https://192.168.0.76/lapki/v1/?op=getrolep"
   // val URL4 = "https://192.168.0.76/lapki/v1/?op=foundid"
    val URL5 = "https://192.168.0.76/lapki/v1/?op=getpet"
     val URL4 = "https://192.168.0.76/lapki/v1/?op=chpet"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    var client = UnsafeOkHttpClient.getUnsafeOkHttpClient()

    @Throws(IOException::class)
    fun run(url: String?): String? {
        var form = FormBody.Builder().add("table", "shelter")
        form.add("id", "")
        val request: Request = Request.Builder().url(URL2).post(form.build()).build()
        client.newCall(request).execute().use { response -> return response.body()!!.string() }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpet)
        session = SessionManager(applicationContext)
        val dayss: Array<Int> = Array(31, { i -> i + 1 })
        val months: Array<Int> = Array(12, { i -> i + 1 })
        val ad = ArrayAdapter(this, android.R.layout.simple_spinner_item, dayss)
        val ad2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        val sp: Spinner = activity_addpet_day
        val sp2: Spinner = activity_addpet_month
        sp.adapter = ad
        sp2.adapter = ad2
        val acthypotalk:Array<String> = arrayOf("Да", "Нет")
        val woo:Array<String> = arrayOf("Без шерсти","Короткая","Средняя","Длинная","Средняя, требующая тримминга","Длинная, требующая тримминга","Очень длинная")
        val dr:Array<String> = arrayOf("Хорошо","Тяжело","Знает базовые команды","Неизвестно")
        val relat:Array<String> = arrayOf("Хорошо", "Плохо", "Нейтрально", "Неизвестно")
        val ad3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, acthypotalk)
        val ad4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, woo)
        val ad5 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dr)
        val ad6 = ArrayAdapter(this, android.R.layout.simple_spinner_item, relat)
        activity_addpet_active.adapter = ad3
        activity_addpet_wool.adapter = ad4
        activity_addpet_hypo.adapter = ad3
        activity_addpet_dressir.adapter = ad5
        activity_addpet_dogs.adapter = ad6
        activity_addpet_animal.adapter = ad6
        activity_addpet_child.adapter = ad6
        activity_addpet_children.adapter = ad6
        activity_addpet_teens.adapter = ad6
        activity_addpet_sounds.adapter = ad6
        activity_addpet_talk.adapter = ad3
        var ty:Array<String> = arrayOf("")
println("PPPPPPP ${session.getPet()}")
        var form = FormBody.Builder().add("table", "shelter")
        form.add("id", "")
        val request: Request = Request.Builder().url(URL2).post(form.build()).build()
                //        val reqproba: Response = okHttpClient.newCall(request).execute()
       // val js = reqproba.body()?.string()
        //println("fuhdidhg ${run(URL2)}")
        val req = okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                println(json)
                if ((JSONObject(json).get("error")).toString()=="false") {
                    var gson = Gson()
                    val listshel = gson?.fromJson(json, ShelterResponse::class.java)
                    var str: Set<String>
                    this@ModifyPet.runOnUiThread {
                       // ty = Array(listshel.lists.size, {i->listshel.lists[i].Name})
                        val tt = listshel.list

                                                println(ty[0])
                        //val ad7 = ArrayAdapter(this@ModifyPet, android.R.layout.simple_spinner_item,ty)
                       // shelter.adapter = ad7
                        // shelter.adapter= SpinAdapter(this@ModifyPet, tt)
                        val ad7 = SpinAdapter(this@ModifyPet, tt)
                        activity_addpet_shelter.adapter = ad7
                        if (session.getMod()=="change"){
                        var h = tt.indexOfFirst { it.id_shelter == session.getChPet().get(SessionManager.KEY_CHANGE_IDSHELTER)!!.toInt()}
                            activity_addpet_shelter.setSelection(h)}
                                //var pet = session.getChPet()
                       // var ids = 0
                       //   ids = pet.get(SessionManager.KEY_CHANGE_SHELTER)!!.toInt()
                      //  if (session.getMod()=="change"){
                       //     role.setSelection(ids)
                      //  }
                    }

                }
                else println("ошибка")

            }

            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }
        })
        println("jkb $req")
        var ro:Array<String> = arrayOf("")
        val request2: Request = Request.Builder().url(URL3).build()
        okHttpClient.newCall(request2).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()

                println(json)

                if ((JSONObject(json).get("error")).toString()=="false") {
                    var gson = Gson()
                    val listtype = gson?.fromJson(json, ParseRole::class.java)
                    this@ModifyPet.runOnUiThread {
                       // ro = Array(listtype.role.size, {i->listtype.role[i].Status})
                        val ro = listtype.role
                       // println(ro[0])
                       // val ad8 = ArrayAdapter(this@ModifyPet, android.R.layout.simple_spinner_item,ro)
                        val ad8 = SpinAdapterSt(this@ModifyPet, ro)
                        activity_addpet_role.adapter = ad8
                        var h = ro.indexOfFirst { it.IDRole == session.getChPet().get(SessionManager.KEY_CHANGE_IDROLE)}
                        activity_addpet_role.setSelection(h)
                      //  var pet = session.getChPet()
                        //var idr = pet.get(SessionManager.KEY_CHANGE_ROLE)!!.toInt()
                        //if (session.getMod()=="change"){
                          //  role.setSelection(idr)
                        //}

                    }


                }
                else println("ошибка")
            }


            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
        if (session.getMod()=="change"){
            activity_addpet_mod.setText("Сохранить")
            activity_addpet_head.setText("Изменение данных питомца")
            var form = FormBody.Builder().add("id", session.getPet())
            println(session.getPet())
            val pp = session.getPet()
            println("PPPP ${session.getPet()}")
            val request: Request = Request.Builder().url(URL5).post(form.build()).build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call?, response: Response?) {
                    val json = response?.body()?.string()
                    println(json)
                    if ((JSONObject(json).get("error")).toString()=="false") {
                        val u = (JSONObject(json).getJSONArray("pet").getJSONObject(0)).toString()
                        println(u)
                        var gson = Gson()
                        val uu = gson?.fromJson(u, PetAcInfo::class.java)
                        println( uu.Name)
                        activity_addpet_name.setText(uu.Name)
                        val stringArray: Array<String> = uu.BirthDate.split("-".toRegex()).toTypedArray()
                        activity_addpet_year.setText(stringArray[0])
                        val day = stringArray[2].toInt()-1
                        activity_addpet_day.setSelection(stringArray[2].toInt()-1)
                        activity_addpet_month.setSelection((stringArray[1].toInt()-1).toInt())
                        println("String Arra $day ${stringArray[1].toInt()}")
                                //discribe.setText(uu.Discribe)

                        println("Discribe ${uu.Discribe}")
                        if (uu.Discribe!=null) {this@ModifyPet.runOnUiThread { activity_addpet_describe.setText(uu.Discribe)}}
                        //spinner4.setSelection(uu.Role)
                        activity_addpet_breed.setText(uu.Poroda)
                        var ss =-1
                        if (uu.Sex=="Ж"){
                            activity_addpet_women.setChecked(true)
                        }  else activity_addpet_men.setChecked(true)
                        var act = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Active){
                                act = i
                                break
                            }
                        }
                        activity_addpet_active.setSelection(act)
                        activity_addpet_size.setText(uu.Size)
                        var ww = -1
                        for (i in 0..woo.size-1){
                            if (woo[i]==uu.Wool){
                                ww = i
                                break
                            }
                        }
                        activity_addpet_wool.setSelection(ww)
                        ww = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Hypo){
                                ww = i
                                break
                            }
                        }
                        activity_addpet_hypo.setSelection(ww)
                        ww=-1
                        for (i in 0..dr.size){
                            if (dr[i]==uu.Dressir){
                                ww = i
                                break
                            }
                        }
                        activity_addpet_dressir.setSelection(ww)
                        activity_addpet_dogs.setSelection(check(uu.Dogs))
                        activity_addpet_animal.setSelection(check(uu.Animal))
                        activity_addpet_child.setSelection(check(uu.Child))
                        activity_addpet_children.setSelection(check(uu.Children))
                        activity_addpet_teens.setSelection(check(uu.Teens))
                        activity_addpet_ills.setText(uu.Ills)
                        activity_addpet_sounds.setSelection(check(uu.Sounds))
                        ww = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Talkative){
                                ww = i
                                break
                            }
                        }
                        activity_addpet_talk.setSelection(ww)


                    }
                    else activity_addpet_error.setText("Что-то пошло не так")
                }
                fun check(g: String):Int{
                    var ww = -1
                    var r = ""
                    for (i in 0..relat.size-1) {
                        r = relat[i]
                        if (r == g) {
                            ww = i
                        }
                    }
                    return ww
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    println(e.toString())
                }

            })

        }else {activity_addpet_mod.setText("Добавить")
            activity_addpet_head.setText("Добавление приюта")
        }
        activity_addpet_mod.setOnClickListener {
           if (session.getMod()=="add"){
               addP()
           }else modP()
            println("${activity_addpet_day.selectedItemId} ${activity_addpet_month.selectedItemId} ${activity_addpet_shelter.selectedItemId} ${activity_addpet_role.selectedItemId} ${activity_addpet_sex.checkedRadioButtonId} ${activity_addpet_active.selectedItem.toString()} ${activity_addpet_wool.selectedItem.toString()} ${activity_addpet_hypo.selectedItem.toString()} ${activity_addpet_dressir.selectedItem.toString()} ${activity_addpet_dogs.selectedItem.toString()} ${activity_addpet_animal.selectedItem.toString()}")
        }


    }

    private fun addP(){
        val n = activity_addpet_name.text.toString()
        val year = activity_addpet_year.text.toString()
        val day = activity_addpet_day.selectedItem.toString()
        val mon = activity_addpet_month.selectedItem.toString()
        val descr = activity_addpet_describe.text.toString()
        val shel = (activity_addpet_shelter.selectedItem as Shelter).id_shelter
        val por = activity_addpet_breed.text.toString()
        val rol = (activity_addpet_role.selectedItem as Role).IDRole
        val se = activity_addpet_sex.indexOfChild(activity_addpet_sex.findViewById(activity_addpet_sex.checkedRadioButtonId)).toString()
        val act = activity_addpet_active.selectedItem.toString()
        val si = activity_addpet_size.text.toString()
        val wo = activity_addpet_wool.selectedItem.toString()
        val hy = activity_addpet_hypo.selectedItem.toString()
        val dres = activity_addpet_dressir.selectedItem.toString()
        val dog = activity_addpet_dogs.selectedItem.toString()
        val ani = activity_addpet_animal.selectedItem.toString()
        val chi = activity_addpet_child.selectedItem.toString()
        val chil = activity_addpet_children.selectedItem.toString()
        val te = activity_addpet_teens.selectedItem.toString()
        val il = activity_addpet_ills.text.toString()
        val sou = activity_addpet_sounds.selectedItem.toString()
        val tal = activity_addpet_talk.selectedItem.toString()
        val ych = setOf('.','/','-')
        println("$n, $year, $descr, $rol")
        var form = FormBody.Builder().add("idshel", shel.toString())

        form.add("idrole", rol.toString())
        if((n!="") and (n!="Введите свое имя") and (year!="") and (por!="") and (se!="-1") and (si!="")and (year.any(ych::contains)==false)) {
            if ((year.toInt()<=2020) and (year.toInt()>=1920)){
                val dd:String
                val mm:String
                if (day.length == 1) {dd = "0"+day} else dd = day
                if (mon.length == 1) {mm = "0"+mon} else mm = mon
                val bd: String = year+"-"+mm+"-"+dd
                val x: String
                if (se=="0") {x ="Ж"} else x = "М"


                form.add("name", n)
                form.add("bd", bd)
                form.add("desc", descr)
                form.add("por", por)
                form.add("sex", x)
                form.add("act", act)
                form.add("si", si)
                form.add("wo", wo)
                form.add("hy", hy)
                form.add("dres", dres)
                form.add("dog", dog)
                form.add("ani", ani)
                form.add("chi", chi)
                form.add("chil", chil)
                form.add("te", te)
                form.add("il", il)
                form.add("sou", sou)
                form.add("tal", tal)


                println("$n, $year, $descr")
                println("$n, $bd, $descr, $x")
                val request: Request = Request.Builder().url(URL).post(form.build()).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        val json = response?.body()?.string()
                        println(json)
                        if ((JSONObject(json).get("error")).toString()=="false") {

                        }
                        else activity_addpet_error.setText("Что-то пошло не так")
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println(e.toString())
                    }

                })
                activity_addpet_error.setText("ok")}else  activity_addpet_error.setText("Введите верный год")
        } else  activity_addpet_error.setText("Неверно введены данные")
    }
    private fun modP(){
        val n = activity_addpet_name.text.toString()
        val year = activity_addpet_year.text.toString()
        val day = activity_addpet_day.selectedItem.toString()
        val mon = activity_addpet_month.selectedItem.toString()
        val descr = activity_addpet_describe.text.toString()
        val shel = (activity_addpet_shelter.selectedItem as Shelter).id_shelter
        val por = activity_addpet_breed.text.toString()
        val rol = (activity_addpet_role.selectedItem as Role).IDRole
        val se = activity_addpet_sex.indexOfChild(activity_addpet_sex.findViewById(activity_addpet_sex.checkedRadioButtonId)).toString()
        val act = activity_addpet_active.selectedItem.toString()
        val si = activity_addpet_size.text.toString()
        val wo = activity_addpet_wool.selectedItem.toString()
        val hy = activity_addpet_hypo.selectedItem.toString()
        val dres = activity_addpet_dressir.selectedItem.toString()
        val dog = activity_addpet_dogs.selectedItem.toString()
        val ani = activity_addpet_animal.selectedItem.toString()
        val chi = activity_addpet_child.selectedItem.toString()
        val chil = activity_addpet_children.selectedItem.toString()
        val te = activity_addpet_teens.selectedItem.toString()
        val il = activity_addpet_ills.text.toString()
        val sou = activity_addpet_sounds.selectedItem.toString()
        val tal = activity_addpet_talk.selectedItem.toString()
        val ych = setOf('.','/','-')
        println("$n, $year, $descr, $rol")
        var form = FormBody.Builder().add("idshel", shel.toString())

        form.add("idrole", rol.toString())
        if((n!="") and (n!="Введите свое имя") and (year!="") and (por!="") and (se!="-1") and (si!="")and (year.any(ych::contains)==false)) {
            if ((year.toInt()<=2020) and (year.toInt()>=1920)){
                val dd:String
                val mm:String
                if (day.length == 1) {dd = "0"+day} else dd = day
                if (mon.length == 1) {mm = "0"+mon} else mm = mon
                val bd: String = year+"-"+mm+"-"+dd
                val x: String
                if (se=="0") {x ="Ж"} else x = "М"


                form.add("name", n)
                form.add("bd", bd)
                form.add("desc", descr)
                form.add("por", por)
                form.add("sex", x)
                form.add("act", act)
                form.add("si", si)
                form.add("wo", wo)
                form.add("hy", hy)
                form.add("dres", dres)
                form.add("dog", dog)
                form.add("ani", ani)
                form.add("chi", chi)
                form.add("chil", chil)
                form.add("te", te)
                form.add("il", il)
                form.add("sou", sou)
                form.add("tal", tal)
                form.add("id", session.getPet())
                println("$n, $year, $descr")
                println("$n, $bd, $descr, $x")
                val request: Request = Request.Builder().url(URL4).post(form.build()).build()
                okHttpClient.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        val json = response?.body()?.string()
                        println(json)
                        if ((JSONObject(json).get("error")).toString()=="false") {

                        }
                        else activity_addpet_error.setText("Что-то пошло не так")
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println(e.toString())
                    }

                })
                activity_addpet_error.setText("ok")}else  activity_addpet_error.setText("Введите верный год")
        } else  activity_addpet_error.setText("Неверно введены данные")
    }

}
class SpinAdapter<T : CommonSpinnerElement>(val appcontext: Context, var items : List<T>):ArrayAdapter<T>(appcontext, android.R.layout.simple_spinner_item, items){
   // val context = appcontext


    override fun getCount(): Int {
        return super.getCount()
    }

   // override final fun getContext(): Context {
    //return context
   //}

    override fun getItem(position: Int): T? {
        return super.getItem(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        LayoutInflater.from(appcontext).inflate(android.R.layout.simple_spinner_item, null)
                ?.findViewById<TextView>(android.R.id.text1)?.apply {
            text = items[position].shelter_name
        } as View

}

interface CommonSpinnerElement {
    val shelter_name: String
}
class SpinAdapterSt<T : CommonSpinnerElementSt>(val appcontext: Context, var items : List<T>):ArrayAdapter<T>(appcontext, android.R.layout.simple_spinner_item, items){
    // val context = appcontext


    override fun getCount(): Int {
        return super.getCount()
    }

    // override final fun getContext(): Context {
    //return context
    //}

    override fun getItem(position: Int): T? {
        return super.getItem(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
            LayoutInflater.from(appcontext).inflate(android.R.layout.simple_spinner_item, null)
                    ?.findViewById<TextView>(android.R.id.text1)?.apply {
                        text = items[position].Status
                    } as View

}
interface CommonSpinnerElementSt {
    val Status: String
}