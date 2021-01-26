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
        val sp: Spinner = spinnerS
        val sp2: Spinner = spinner2S
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
        active.adapter = ad3
        wool.adapter = ad4
        hypo.adapter = ad3
        dressir.adapter = ad5
        dogs.adapter = ad6
        animal.adapter = ad6
        child.adapter = ad6
        children.adapter = ad6
        teens.adapter = ad6
        sounds.adapter = ad6
        talk.adapter = ad3
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
                    val listshel = gson?.fromJson(json, Parse::class.java)
                    var str: Set<String>
                    this@ModifyPet.runOnUiThread {
                       // ty = Array(listshel.lists.size, {i->listshel.lists[i].Name})
                        val tt = listshel.lists

                                                println(ty[0])
                        //val ad7 = ArrayAdapter(this@ModifyPet, android.R.layout.simple_spinner_item,ty)
                       // shelter.adapter = ad7
                        // shelter.adapter= SpinAdapter(this@ModifyPet, tt)
                        val ad7 = SpinAdapter(this@ModifyPet, tt)
                        shelter.adapter = ad7
                        if (session.getMod()=="change"){
                        var h = tt.indexOfFirst { it.IDShelter == session.getChPet().get(SessionManager.KEY_CHANGE_IDSHELTER)!!.toInt()}
                        shelter.setSelection(h)}
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
                        role.adapter = ad8
                        var h = ro.indexOfFirst { it.IDRole == session.getChPet().get(SessionManager.KEY_CHANGE_IDROLE)}
                        role.setSelection(h)
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
            mod.setText("Сохранить")
            vh.setText("Изменение данных питомца")
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
                        name.setText(uu.Name)
                        val stringArray: Array<String> = uu.BirthDate.split("-".toRegex()).toTypedArray()
                        bdate.setText(stringArray[0])
                        val day = stringArray[2].toInt()-1
                        spinnerS.setSelection(stringArray[2].toInt()-1)
                        spinner2S.setSelection((stringArray[1].toInt()-1).toInt())
                        println("String Arra $day ${stringArray[1].toInt()}")
                                //discribe.setText(uu.Discribe)

                        println("Discribe ${uu.Discribe}")
                        if (uu.Discribe!=null) {this@ModifyPet.runOnUiThread { discribe.setText(uu.Discribe)}}
                        //spinner4.setSelection(uu.Role)
                        poroda.setText(uu.Poroda)
                        var ss =-1
                        if (uu.Sex=="Ж"){
                            w.setChecked(true)
                        }  else men.setChecked(true)
                        var act = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Active){
                                act = i
                                break
                            }
                        }
                        active.setSelection(act)
                        size.setText(uu.Size)
                        var ww = -1
                        for (i in 0..woo.size-1){
                            if (woo[i]==uu.Wool){
                                ww = i
                                break
                            }
                        }
                        wool.setSelection(ww)
                        ww = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Hypo){
                                ww = i
                                break
                            }
                        }
                        hypo.setSelection(ww)
                        ww=-1
                        for (i in 0..dr.size){
                            if (dr[i]==uu.Dressir){
                                ww = i
                                break
                            }
                        }
                        dressir.setSelection(ww)
                        dogs.setSelection(check(uu.Dogs))
                        animal.setSelection(check(uu.Animal))
                        child.setSelection(check(uu.Child))
                        children.setSelection(check(uu.Children))
                        teens.setSelection(check(uu.Teens))
                        ills.setText(uu.Ills)
                        sounds.setSelection(check(uu.Sounds))
                        ww = -1
                        for (i in 0..acthypotalk.size-1){
                            if (acthypotalk[i]==uu.Talkative){
                                ww = i
                                break
                            }
                        }
                        talk.setSelection(ww)


                    }
                    else mist.setText("Что-то пошло не так")
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

        }else {mod.setText("Добавить")
            vh.setText("Добавление приюта")
        }
        mod.setOnClickListener {
           if (session.getMod()=="add"){
               addP()
           }else modP()
            println("${spinnerS.selectedItemId} ${spinner2S.selectedItemId} ${shelter.selectedItemId} ${role.selectedItemId} ${sex.checkedRadioButtonId} ${active.selectedItem.toString()} ${wool.selectedItem.toString()} ${hypo.selectedItem.toString()} ${dressir.selectedItem.toString()} ${dogs.selectedItem.toString()} ${animal.selectedItem.toString()}")
        }


    }

    private fun addP(){
        val n = name.text.toString()
        val year = bdate.text.toString()
        val day = spinnerS.selectedItem.toString()
        val mon = spinner2S.selectedItem.toString()
        val descr = discribe.text.toString()
        val shel = (shelter.selectedItem as Shelter).IDShelter
        val por = poroda.text.toString()
        val rol = (role.selectedItem as Role).IDRole
        val se = sex.indexOfChild(sex.findViewById(sex.checkedRadioButtonId)).toString()
        val act = active.selectedItem.toString()
        val si = size.text.toString()
        val wo = wool.selectedItem.toString()
        val hy = hypo.selectedItem.toString()
        val dres = dressir.selectedItem.toString()
        val dog = dogs.selectedItem.toString()
        val ani = animal.selectedItem.toString()
        val chi = child.selectedItem.toString()
        val chil = children.selectedItem.toString()
        val te = teens.selectedItem.toString()
        val il = ills.text.toString()
        val sou = sounds.selectedItem.toString()
        val tal = talk.selectedItem.toString()
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
                        else mist.setText("Что-то пошло не так")
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println(e.toString())
                    }

                })
                mist.setText("ok")}else  mist.setText("Введите верный год")
        } else  mist.setText("Неверно введены данные")
    }
    private fun modP(){
        val n = name.text.toString()
        val year = bdate.text.toString()
        val day = spinnerS.selectedItem.toString()
        val mon = spinner2S.selectedItem.toString()
        val descr = discribe.text.toString()
        val shel = (shelter.selectedItem as Shelter).IDShelter
        val por = poroda.text.toString()
        val rol = (role.selectedItem as Role).IDRole
        val se = sex.indexOfChild(sex.findViewById(sex.checkedRadioButtonId)).toString()
        val act = active.selectedItem.toString()
        val si = size.text.toString()
        val wo = wool.selectedItem.toString()
        val hy = hypo.selectedItem.toString()
        val dres = dressir.selectedItem.toString()
        val dog = dogs.selectedItem.toString()
        val ani = animal.selectedItem.toString()
        val chi = child.selectedItem.toString()
        val chil = children.selectedItem.toString()
        val te = teens.selectedItem.toString()
        val il = ills.text.toString()
        val sou = sounds.selectedItem.toString()
        val tal = talk.selectedItem.toString()
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
            text = items[position].Name
        } as View

}

interface CommonSpinnerElement {
    val Name: String
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