package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addshelter.*
import kotlinx.android.synthetic.main.activity_registr.*
import kotlinx.android.synthetic.main.activity_registr.bdate
import kotlinx.android.synthetic.main.activity_registr.city
import kotlinx.android.synthetic.main.activity_registr.email
import kotlinx.android.synthetic.main.activity_registr.activity_authorization_login
import kotlinx.android.synthetic.main.activity_registr.mist
import kotlinx.android.synthetic.main.activity_registr.name
import kotlinx.android.synthetic.main.activity_registr.pass
import kotlinx.android.synthetic.main.activity_registr.pass2
import kotlinx.android.synthetic.main.activity_registr.re
import kotlinx.android.synthetic.main.activity_registr.ret
import kotlinx.android.synthetic.main.activity_registr.spinner
import kotlinx.android.synthetic.main.activity_registr.spinner2
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class Registr : AppCompatActivity() {
    val URL = "https://192.168.0.76/lapki/v1/?op=addusers"
    var okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        val dayss: Array<Int> = Array(31, {i->i+1})
        val months: Array<Int> = Array(12, {i->i+1})
        val ad = ArrayAdapter(this, android.R.layout.simple_spinner_item,dayss)
        val ad2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,months)
        val sp: Spinner = spinner
        val sp2: Spinner = spinner2
        sp.adapter = ad
        sp2.adapter= ad2


        //val radioButton = RadioButton(this)
        //sex.addView(radioButton)
        re.setOnClickListener {
            reg()
        }
        ret.setOnClickListener {
            startActivity(Intent(this, Accaunt::class.java))
        }

    }
    val LOG_TAG = "myLogs"
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("LOGIN", activity_authorization_login.getText().toString())
        outState.putString("PASS", pass.getText().toString())
        outState.putString("EMAIL", email.getText().toString())
        outState.putString("PASS2", pass2.getText().toString())
        outState.putString("NAME", name.getText().toString())
        outState.putString("LASTNAME", lname.getText().toString())
        outState.putString("YEAR", bdate.getText().toString())
        outState.putString("CITY", city.getText().toString())
        outState.putString("TEL", tel.getText().toString())
        outState.putInt("DAY", spinner.selectedItemId.toInt())
        outState.putInt("MON", spinner2.selectedItemId.toInt())
        Log.d(LOG_TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        activity_authorization_login.setText(savedInstanceState.getString("LOGIN"))
        pass.setText(savedInstanceState.getString("PASS"))
        email.setText(savedInstanceState.getString("EMAIL"))
        pass2.setText(savedInstanceState.getString("PASS2"))
        name.setText(savedInstanceState.getString("NAME"))
        lname.setText(savedInstanceState.getString("LASTNAME"))
        bdate.setText(savedInstanceState.getString("YEAR"))
        city.setText(savedInstanceState.getString("CITY"))
        tel.setText(savedInstanceState.getString("TEL"))
        spinner.setSelection(savedInstanceState.getInt("DAY"))
        spinner3.setSelection(savedInstanceState.getInt("MON"))
        Log.d(LOG_TAG, "onRestoreInstanceState")
    }

    private fun reg(){
        val n = name.text.toString()
        val ln = lname.text.toString()
        val year = bdate.text.toString()
        val r = sex.indexOfChild(sex.findViewById(sex.checkedRadioButtonId)).toString()
        val l = activity_authorization_login.text.toString()
        val p = pass.text.toString()
        val p2 = pass2.text.toString()
        val c = city.text.toString()
        val e = email.text.toString()
        val day = spinner.selectedItem.toString()
        val mon = spinner2.selectedItem.toString()
        val tell: String
        if (tel.text.toString() == "Введите свой телефон") tell = "" else tell = tel.text.toString()
        val ych = setOf('.','/','-')
        println(r)
        println()

        if((p!="") and (l!="") and (l!="Введите свой логин") and (e!="") and (n!="") and (n!="Введите свое имя") and (ln!="") and (ln!="Введите свою фамилию")  and (year!="")and (day!="")  and (mon!="") and (r!="-1") and (c!="") and (c!="Введите свой город") and (p==p2) and (year.any(ych::contains)==false)) {
            if ((year.toInt()<2020) and (year.toInt()>1920)){
                val pe = Cryption()
                val pee = pe.encrypt(p).toString()
                val dd:String
                val mm:String
                if (day.length == 1) {dd = "0"+day} else dd = day
                if (mon.length == 1) {mm = "0"+mon} else mm = mon
                val bd: String = year+"-"+mm+"-"+dd
                val sex: String
                if (r=="0") {sex ="Ж"} else sex = "М"
                var form = FormBody.Builder().add("name", n)
                form.add("lname", ln)
                form.add("bd", bd)
                form.add("sex", sex)
                form.add("login", l)
                form.add("pass", pee)
                form.add("city", c)
                form.add("email", e)
                form.add("tel", tell)
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
}