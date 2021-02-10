package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addpet.*
import kotlinx.android.synthetic.main.activity_addrequest_vol.*
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.api.*
import ru.kotlin.lapki.api.entities.ShelterRole
import java.text.SimpleDateFormat

class RequestVolChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addrequest_vol)
        val reqID = intent.getIntExtra("id_req", 0)
        val userID = intent.getIntExtra("id",0)
        activity_addrequest_vol_role_head.visibility = View.VISIBLE
        activity_addrequest_vol_role.visibility = View.VISIBLE
        activity_addrequest_vol_head.text = "Изменить данные"
        Thread {
            try {
                val reqResponse = RequestRepository.VolReq(reqID.toString())
                if (reqResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        activity_addrequest_vol_shelter.setText(reqResponse.request.first().shelter)
                        activity_addrequest_vol_comment.setText(reqResponse.request.first().comment)
                    }
                    val roleResponse = RequestRoleRepository.get()
                    println(roleResponse.role)
                    if (roleResponse.isError) throw IllegalAccessError() else {
                        runOnUiThread {
                            activity_addrequest_vol_role.adapter =
                                SpinRoleAdapter(this, roleResponse.role)
                            activity_addrequest_vol_role.setSelection(roleResponse.role.indexOfFirst { it.id_role == reqResponse.request.first().id_status})
                        }
                    }
                    val userResponse = UserAccountRepository.get(reqResponse.request.first().id_user.toString())
                    if (userResponse.isError) throw IllegalAccessError() else {
                        val format = SimpleDateFormat("dd/MM/yyy")
                        runOnUiThread {
                            activity_addrequest_vol_name.setText(userResponse.user.first().user_name)
                            activity_addrequest_vol_surname.setText(userResponse.user.first().surname)
                            activity_addrequest_vol_birthday.setText(format.format(userResponse.user.first().birth_date))
                            activity_addrequest_vol_sex.setText(userResponse.user.first().sex)
                            activity_addrequest_vol_city.setText(userResponse.user.first().city)
                            activity_addrequest_vol_tel.setText(userResponse.user.first().telephone)
                            activity_addrequest_vol_email.setText(userResponse.user.first().email)
                            activity_addrequest_vol_describe.setText(userResponse.user.first().describe)

                            println(userResponse.user.first().describe)
                        }


                    }


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
            changeReqVol(reqID.toString(), userID.toString())
        }
        //реагирует на сохранение
        activity_addrequest_vol_return.setOnClickListener {
            startActivity(Intent(this, RequestVolActivity::class.java).apply {
                putExtra("id", reqID)
            })
        }

    }
    fun changeReqVol(reqID:String, userID:String){
        Thread {
            try {
                val reqResponse = RequestVolModifyRepository.change(
                        reqID,
                        (activity_addrequest_vol_role.selectedItem as ShelterRole).id_role.toString(),
                        activity_addrequest_vol_comment.text.toString()
                )
                if (reqResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        println("halo")
                        startActivity(Intent(this, RequestVolActivity::class.java).apply {
                            putExtra("id", userID.toInt())
                            putExtra("id_req", reqID.toInt())
                        })
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