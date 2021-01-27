package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_petac.*
import kotlinx.android.synthetic.main.activity_petac.bd
import kotlinx.android.synthetic.main.activity_petac.del
import kotlinx.android.synthetic.main.activity_petac.des
import kotlinx.android.synthetic.main.activity_petac.name
import kotlinx.android.synthetic.main.activity_petac.type
import kotlinx.android.synthetic.main.activity_petac.req

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
data class PetAcInfo(val IDPet: String, val Name: String, val BirthDate: String, val Discribe: String, val Type: String, val IdShelt: String, val Shelterr: String, val Photo: String, val IdRole: String, val Statu: String, val Sex: String, val Active: String, val Size: String, val Wool: String, val Hypo: String, val Dressir: String, val Dogs: String, val Animal: String, val Child: String, val Children: String, val Teens: String, val Ills: String, val Sounds: String, val Talkative: String, val Poroda: String)
class PetAc(): AppCompatActivity() {
    lateinit var session: SessionManager
    val URL = "https://192.168.0.76/lapki/v1/?op=getpet"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petac)
        session = SessionManager(applicationContext)
        val petID = intent.getLongExtra("id",0)
        if (petID != null) {
            println(petID)
        }
       // val idpet = session.getPet()
        var form = FormBody.Builder().add("id", petID.toString())
        val request: Request = Request.Builder().url(URL).post(form.build()).build()
        println(request)
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                println(json)
                if ((JSONObject(json).get("error")).toString()=="false") {

                    val u = (JSONObject(json).getJSONArray("pet").getJSONObject(0)).toString()
                    println(u)
                    var gson = Gson()
                    val uu = gson?.fromJson(u, PetAcInfo::class.java)
                    println( uu.Statu)
                    println( uu.Shelterr)
                    statuss.setText(uu.Statu)
                    name.setText(uu.Name)
                    val bdd = bd.text.toString()+uu.BirthDate
                    bd.setText(bdd)
                    val d = des.text.toString()+uu.Discribe
                    des.setText(d)
                    val t = type.text.toString()+uu.Type
                    type.setText(t)
                    val sh = uu.Shelterr
                    println(sh)
                    println(uu.Shelterr)
                    she.setText(sh)
                    val s = sex.text.toString()+uu.Sex
                    sex.setText(s)
                    val pp = por.text.toString()+uu.Poroda
                    por.setText(pp)
                    val a = act.text.toString()+uu.Active
                    act.setText(a)
                    val siz = size.text.toString()+uu.Size
                    size.setText(siz)
                    val hyp = hypo.text.toString()+uu.Hypo
                    hypo.setText(hyp)
                    val dressi = dressir.text.toString()+uu.Dressir
                    dressir.setText(dressi)
                    val dog = dogs.text.toString()+uu.Dogs
                    dogs.setText(dog)
                    val ani = animal.text.toString()+uu.Animal
                    animal.setText(ani)
                    val chil = child.text.toString()+uu.Child
                    child.setText(chil)
                    val childre = children.text.toString()+uu.Children
                    children.setText(childre)
                    val teen = teens.text.toString()+uu.Teens
                    teens.setText(teen)
                    val ill = ils.text.toString()+uu.Ills
                    ils.setText(ill)
                    val sound = sounds.text.toString()+uu.Sounds
                    sounds.setText(sound)
                    val talke = talk.text.toString()+uu.Talkative
                    talk.setText(talke)
                    rolid.setText(uu.IdRole)
                    shelid.setText(uu.IdShelt)
                }
                else println("Ошибка")
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println(e.toString())
            }

        })
      //  println(idpet)
       // session.Pet(idpet)
        del.setOnClickListener{
            session.TYPE_OBJ("pet")
            Delete.Del(applicationContext)
        }
        req.setOnClickListener { session.Requests("par", "pet")
            startActivity(Intent(this, Requestslist::class.java))
         }
        ch.setOnClickListener {
            //session.Pet(session.getPet())
         //   session.CHPET(idpet, statuss.text.toString(), she.text.toString(), rolid.text.toString(), shelid.text.toString())
            session.Mod("change")
            println(rolid.text.toString())
            //session.Shelter(shelid.text.toString())
            println("SSSS ${session.getChPet().get(SessionManager.KEY_IDPET)} ${session.getChPet().get(SessionManager.KEY_CHANGE_IDROLE)} ${session.getChPet().get(SessionManager.KEY_CHANGE_IDSHELTER)} ")
            startActivity(Intent(this, ModifyPet::class.java))
        }

    }
}