package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_addshelter.*
import kotlinx.android.synthetic.main.activity_registr.*
import kotlinx.android.synthetic.main.activity_shelterac.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.adapters.SpinTypeAdapter
import ru.kotlin.lapki.api.*
import ru.kotlin.lapki.api.entities.ShelterAccount
import ru.kotlin.lapki.api.entities.ShelterType
import java.io.IOException
import java.text.SimpleDateFormat

class ShelterAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addshelter)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        activity_addshelter_day.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        activity_addshelter_month.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        //if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") {activity_addshelter_role.visibility = View.VISIBLE
        //   activity_addshelter_rolename.visibility = View.VISIBLE}

        Thread {
            try {
                println("Hello")
                val shelterTypeResponse = ShelterTypeRepository.get()
                println(shelterTypeResponse.type)
                println(shelterTypeResponse.isError)
                if (shelterTypeResponse.isError) throw IllegalAccessError() else {
                    println(shelterTypeResponse.type)
                    runOnUiThread {
                        val ad = SpinTypeAdapter(this, shelterTypeResponse.type)
                        val sp: Spinner = activity_addshelter_type
                        sp.adapter = ad
                    }

                }
                val shelterRoleResponse = ShelterRoleRepository.get()
                if (shelterRoleResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        activity_addshelter_role.adapter =
                            SpinRoleAdapter(this, shelterRoleResponse.role)
                    }
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьShelterAdd"
                    }
                }
            }
        }.start()

        activity_addshelter_mod.setText("Добавить")
        activity_addshelter_head.setText("Добавление приюта")

        activity_addshelter_mod.setOnClickListener {
            addShelter()
        }
        activity_addshelter_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
   fun addShelter(){
       Thread {
           try {
               val ych = setOf('.','/','-')
               println(activity_addshelter_name.text.toString())
               println(DateSpinner.dateString(activity_addshelter_day.selectedItem.toString(),
                       activity_addshelter_month.selectedItem.toString(), activity_addshelter_year.text.toString()))
               println( activity_addshelter_city.text.toString())
               println(activity_addshelter_describe.text.toString())
               println((activity_addshelter_type.selectedItem as ShelterType).id_type.toString())
               if (activity_addshelter_year.text.toString().any(ych::contains)==true) throw IllegalAccessError() else{
                   val shelterResponse = ShelterModifyRepository.add(
                           activity_addshelter_name.text.toString(),
                           DateSpinner.dateString(activity_addshelter_day.selectedItem.toString(),
                           activity_addshelter_month.selectedItem.toString(), activity_addshelter_year.text.toString()),
                           activity_addshelter_city.text.toString(),
                           activity_addshelter_describe.text.toString(),
                           (activity_addshelter_type.selectedItem as ShelterType).id_type.toString()

                   )
                   println(shelterResponse.id)
                   if (shelterResponse.isError) throw IllegalAccessError() else {
                       runOnUiThread {
                           startActivity(Intent(this, ShelterAccountActivity::class.java).apply {
                               putExtra("id", shelterResponse.id)
                           })
                           finish()
                       }
                   }
               }
           } catch (exception: Throwable) {
               runOnUiThread {
                   val errors = when (exception) {
                       is IllegalAccessError -> "Заполните все поля"
                       else -> exception.message
                   }
                   println(errors)
               }
           }
       }.start()
    }
}