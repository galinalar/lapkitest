package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addrequest_vol.*
import kotlinx.android.synthetic.main.activity_request_data.*
import ru.kotlin.lapki.api.*
import java.text.SimpleDateFormat

class RequestVolAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addrequest_vol)
        val userID = intent.getIntExtra("id", 0)
        val shelterID = intent.getIntExtra("id_shelter", 0)
        activity_addrequest_vol_role.visibility = View.INVISIBLE
        activity_addrequest_vol_role_head.visibility = View.INVISIBLE
        Thread {
            try {
                val userResponse = UserAccountRepository.get(userID.toString())
                if (userResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    activity_addrequest_vol_name.setText(userResponse.user.first().user_name)
                    activity_addrequest_vol_surname.setText(userResponse.user.first().surname)
                    activity_addrequest_vol_birthday.setText(format.format(userResponse.user.first().birth_date))
                    activity_addrequest_vol_sex.setText(userResponse.user.first().sex)
                    activity_addrequest_vol_city.setText(userResponse.user.first().city)
                    activity_addrequest_vol_tel.setText(userResponse.user.first().telephone)
                    activity_addrequest_vol_email.setText(userResponse.user.first().email)
                    activity_addrequest_vol_describe.setText(userResponse.user.first().describe)
                    println(userResponse.user.first().describe)
                    val shelResponse = ShelterAccountRepository.get(shelterID.toString())
                    if (shelResponse.isError) throw IllegalAccessError() else {
                        activity_addrequest_vol_shelter.setText(shelResponse.shelter.first().shelter_name)
                    }
                    finish()

                }


            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Что-то пошло не так"
                        else -> exception.message
                    }
                    println(err)
                }
            }
        }.start()

        activity_addrequest_vol_save.setOnClickListener {
            addReqVol(userID.toString(), shelterID.toString())
        }
        //реагирует на сохранение
        activity_addrequest_vol_return.setOnClickListener {
           startActivity(Intent(this, ShelterAccountActivity::class.java).apply {
                putExtra("id", shelterID)
            })
       }


    }
    fun addReqVol(iduser:String,idshelter:String){
        Thread {
            try {
                  val reqResponse = RequestVolModifyRepository.add(
                          iduser,
                          idshelter,
                          activity_addrequest_vol_comment.text.toString()
                    )
                    if (reqResponse.isError) throw IllegalAccessError() else {
                        runOnUiThread {
                            println("halo")
                            startActivity(Intent(this, RequestVolActivity::class.java).apply {
                                putExtra("id", iduser)
                                putExtra("id_req", reqResponse.id)
                            })
                            finish()
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