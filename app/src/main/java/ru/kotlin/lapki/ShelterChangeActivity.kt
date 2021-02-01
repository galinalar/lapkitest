package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addshelter.*
import kotlinx.android.synthetic.main.activity_shelterac.*
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.adapters.SpinTypeAdapter
import ru.kotlin.lapki.api.ShelterAccountRepository
import ru.kotlin.lapki.api.ShelterModifyRepository
import ru.kotlin.lapki.api.ShelterRoleRepository
import ru.kotlin.lapki.api.ShelterTypeRepository
import ru.kotlin.lapki.api.entities.ShelterRole
import ru.kotlin.lapki.api.entities.ShelterType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class ShelterChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addshelter)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        activity_addshelter_day.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        activity_addshelter_month.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        //if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") {
        activity_addshelter_role.visibility = View.VISIBLE
         activity_addshelter_rolename.visibility = View.VISIBLE
    //}
        val shelterID = intent.getIntExtra("id", 0)

        Thread {
            try {
                val shelterTypeResponse = ShelterTypeRepository.get()
                println(shelterTypeResponse.type)
                println(shelterTypeResponse.isError)
                if (shelterTypeResponse.isError) throw IllegalAccessError() else {
                    println(shelterTypeResponse.type)
                    val ad = SpinTypeAdapter(this, shelterTypeResponse.type)
                    val sp: Spinner = activity_addshelter_type
                    sp.adapter = ad

                }
                val shelterRoleResponse = ShelterRoleRepository.get()
                if (shelterTypeResponse.isError) throw IllegalAccessError() else {
                    activity_addshelter_role.adapter = SpinRoleAdapter(this, shelterRoleResponse.role)
                }
                val shelterResponse = ShelterAccountRepository.get(shelterID.toString())
                println(shelterResponse.shelter.first().shelter_name)
                if (shelterResponse.isError) throw IllegalAccessError() else {
                    activity_addshelter_name.setText(shelterResponse.shelter.first().shelter_name)
                    val stringArray: Array<String> = shelterResponse.shelter.first().birth_date.toString().split("-".toRegex()).toTypedArray()
                    println(shelterResponse.shelter.first().city)
                    shelterResponse.shelter.first().birth_date

                    println(shelterResponse.shelter.first().birth_date.month)
                    println(shelterResponse.shelter.first().birth_date.date)
                    val format = SimpleDateFormat("yyyy")
                    println(format.format(shelterResponse.shelter.first().birth_date))

                    println(shelterTypeResponse.type.indexOfFirst { it.id_type == shelterResponse.shelter.first().type_id})
                    activity_addshelter_day.setSelection(shelterResponse.shelter.first().birth_date.date-1)
                    activity_addshelter_month.setSelection(shelterResponse.shelter.first().birth_date.month)
                    activity_addshelter_year.setText(format.format(shelterResponse.shelter.first().birth_date))
                    activity_addshelter_city.setText(shelterResponse.shelter.first().city)
                    activity_addshelter_describe.setText(shelterResponse.shelter.first().describe)
                    println(shelterResponse.shelter.first().describe)
                    activity_addshelter_type.setSelection(shelterTypeResponse.type.indexOfFirst { it.id_type == shelterResponse.shelter.first().type_id})
                    activity_addshelter_role.setSelection(shelterRoleResponse.role.indexOfFirst { it.id_role == shelterResponse.shelter.first().role})
                                   }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьShelterChange"
                    }
                }
            }
        }.start()


        activity_addshelter_mod.setText("Изменить")
        activity_addshelter_head.setText("Изменение приюта")

        activity_addshelter_mod.setOnClickListener {
            changeShelter(shelterID)
        }
        activity_addshelter_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
    fun changeShelter(shelterID:Int){
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
                    val shelterResponse = ShelterModifyRepository.change(
                            activity_addshelter_name.text.toString(),
                            DateSpinner.dateString(activity_addshelter_day.selectedItem.toString(),
                                    activity_addshelter_month.selectedItem.toString(), activity_addshelter_year.text.toString()),
                            activity_addshelter_city.text.toString(),
                            activity_addshelter_describe.text.toString(),
                            (activity_addshelter_type.selectedItem as ShelterType).id_type.toString(),
                            (activity_addshelter_role.selectedItem as ShelterRole).id_role.toString(),
                            shelterID.toString()
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