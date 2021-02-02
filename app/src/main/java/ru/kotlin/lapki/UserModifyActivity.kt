package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_chuser.*
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.adapters.SpinShelterAdapter
import ru.kotlin.lapki.api.*
import ru.kotlin.lapki.api.entities.Shelter
import ru.kotlin.lapki.api.entities.ShelterRole
import java.text.SimpleDateFormat

class UserModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chuser)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        val ad = ArrayAdapter(this, android.R.layout.simple_spinner_item,days)
        val ad2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,months)
        val sp: Spinner = activity_chuser_day
        val sp2: Spinner = activity_chuser_month
        sp.adapter = ad
        sp2.adapter= ad2
        val userID = intent.getIntExtra("id", 0)
        Thread {
            try {
                val userShelterResponse = ShelterListRepository.shelters()
                if (userShelterResponse.isError) throw IllegalAccessError() else {
                    val ad = SpinShelterAdapter(this, userShelterResponse.list)
                    val sp: Spinner = activity_chuser_shelter
                    sp.adapter = ad

                }
                val userRoleResponse = UserRoleRepository.get()
                println(userRoleResponse.role)
                if (userRoleResponse.isError) throw IllegalAccessError() else {
                    activity_chuser_role.adapter = SpinRoleAdapter(this, userRoleResponse.role)
                }
                println(userRoleResponse.role[2].role)
                val userResponse = UserAccountRepository.get(userID.toString())
                if (userResponse.isError) throw IllegalAccessError() else {

                    activity_chuser_name.setText(userResponse.user.first().user_name)
                    activity_chuser_surname.setText(userResponse.user.first().surname)
                    activity_chuser_login.setText(userResponse.user.first().login)
                    val format = SimpleDateFormat("yyyy")
                    activity_chuser_day.setSelection(userResponse.user.first().birth_date.date-1)
                    activity_chuser_month.setSelection(userResponse.user.first().birth_date.month)
                    activity_chuser_year.setText(format.format(userResponse.user.first().birth_date))
                    if (userResponse.user.first().sex=="Ж") activity_chuser_women.setChecked(true) else activity_chuser_men.setChecked(true)
                    activity_chuser_pass.setText(Cryption().decryptWithAES(userResponse.user.first().pass))
                    activity_chuser_city.setText(userResponse.user.first().city)
                    activity_chuser_telephone.setText(userResponse.user.first().telephone)
                    activity_chuser_email.setText(userResponse.user.first().email)
                    activity_chuser_role.setSelection(userRoleResponse.role.indexOfFirst { it.id_role == userResponse.user.first().id_role})
                    if (userResponse.user.first().id_role==2){
                        val userAdminResponse = ShelterAdminRepository.get(userID.toString())
                        if (userAdminResponse.isError) throw IllegalAccessError() else {
                            activity_chuser_shelter.setSelection(userShelterResponse.list.indexOfFirst { it.id_shelter == userAdminResponse.admin.first().id_shelter})
                        }
                    }
                    activity_chuser_describe.setText(userResponse.user.first().describe)
                    println(userResponse.user.first().describe)
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_chuser_error.text = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> exception.message
                    }
                }
            }
        }.start()



        activity_chuser_save.setOnClickListener {
            UserChange(userID)
        }
        activity_chuser_return.setOnClickListener {
            startActivity(Intent(this, UserAccountActivity::class.java).apply {
                putExtra("id", userID)
            })
        }


    }
    private fun UserChange(userID:Int ){
        Thread {
            try {
                var sex: String
                if (activity_chuser_sex.indexOfChild(activity_chuser_sex.findViewById(activity_chuser_sex.checkedRadioButtonId)).toString() =="0") {sex ="Ж"} else sex = "М"
                val ych = setOf('.','/','-')
                println()
                println(activity_chuser_pass!=activity_chuserr_repeat_pass)
                println(activity_chuser_year.text.toString().any(ych::contains))
                if ((activity_chuser_pass.text.toString()!=activity_chuserr_repeat_pass.text.toString())  or (activity_chuser_year.text.toString().any(ych::contains))) throw IllegalAccessError() else{
                    val userChResponse = UserModifyRepository.change(
                            userID.toString(),
                            activity_chuser_name.text.toString(),
                            activity_chuser_surname.text.toString(),
                            DateSpinner.dateString(activity_chuser_day.selectedItem.toString(),
                                    activity_chuser_month.selectedItem.toString(), activity_chuser_year.text.toString()),
                            sex,
                            activity_chuser_login.text.toString(),
                            activity_chuser_pass.text.toString(),
                            activity_chuser_city.text.toString(),
                            activity_chuser_email.text.toString(),
                            activity_chuser_telephone.text.toString(),
                            activity_chuser_describe.text.toString(),
                            (activity_chuser_role.selectedItem as ShelterRole).id_role.toString()
                    )
                    println(userChResponse.isError)
                        if (userChResponse.isError) throw IllegalAccessError() else {
                            val userAdminResponse = ShelterAdminRepository.get(userID.toString())
                            println(userAdminResponse.isError)
                            if (userAdminResponse.isError and ((activity_chuser_role.selectedItem as ShelterRole).id_role == 2)) {
                                val addResponse = ShelterAdminRepository.add(userID.toString(), (activity_chuser_shelter.selectedItem as Shelter).id_shelter.toString())
                                println("addResponse.isError")
                                println(addResponse.isError)
                                if (addResponse.isError) throw IllegalAccessError()
                            } else if (!userAdminResponse.isError and(userAdminResponse.admin.first().id_shelter != (activity_chuser_shelter.selectedItem as Shelter).id_shelter)) {
                                val chResponse = ShelterAdminRepository.change(userID.toString(), (activity_chuser_shelter.selectedItem as Shelter).id_shelter.toString(), userAdminResponse.admin.first().id.toString())
                                println("chResponse.isError")
                                println(userID.toString())
                                println((activity_chuser_shelter.selectedItem as Shelter).id_shelter.toString())
                                println(userAdminResponse.admin.first().id.toString())
                                println(chResponse.isError)
                                if (chResponse.isError) throw IllegalAccessError()
                            } else if (!userAdminResponse.isError and ((activity_chuser_role.selectedItem as ShelterRole).id_role != 2)){
                                val delResponse = ShelterAdminRepository.del(userAdminResponse.admin.first().id.toString())
                                println("delResponse.isError")
                                println(delResponse.isError)
                                if (delResponse.isError) throw IllegalAccessError()
                            }
                        runOnUiThread {
                            startActivity(Intent(this, UserAccountActivity::class.java).apply {
                                putExtra("id", userID)
                            })
                            finish()
                        }
                    }
                }
            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_chuser_error.text = when (exception) {
                        is IllegalAccessError -> "Заполните все поля"
                        else -> exception.message
                    }
                }
            }
        }.start()

    }

}