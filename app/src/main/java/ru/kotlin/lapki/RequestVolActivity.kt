package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request_data.*
import ru.kotlin.lapki.api.RequestRepository
import ru.kotlin.lapki.api.RequestVolRepository
import ru.kotlin.lapki.api.UserAccountRepository
import java.text.SimpleDateFormat

class RequestVolActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_data)
        val userID = intent.getIntExtra("id", 0)
        val requestID = intent.getIntExtra("id_req", 0)
        Thread {
            try {
                val userResponse = UserAccountRepository.get(userID.toString())
                if (userResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    activity_request_data_name.setText(userResponse.user.first().user_name)
                    activity_request_data_surname.setText(userResponse.user.first().surname)
                    activity_request_data_birthday.setText(format.format(userResponse.user.first().birth_date))
                    activity_request_data_sex.setText(userResponse.user.first().sex)
                    activity_request_data_city.setText(userResponse.user.first().city)
                    activity_request_data_tel.setText(userResponse.user.first().telephone)
                    activity_request_data_email.setText(userResponse.user.first().email)
                    activity_request_data_describe.setText(userResponse.user.first().describe)
                    println(userResponse.user.first().describe)
                    println(requestID)
                    val reqResponse = RequestRepository.VolReq(requestID.toString())
                    if (reqResponse.isError) throw IllegalAccessError() else {
                        activity_request_data_comment.setText(reqResponse.request.first().comment)
                    }
                    finish()

                }


            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> exception.message
                    }
                    println(err)
                }
            }
        }.start()
        activity_request_data_photo.setOnClickListener {
            startActivity(Intent(this, UserAccountActivity::class.java).apply {
                putExtra("id", userID)
            })
        }
        activity_request_data_accept.setOnClickListener {
            Thread {
                try {
                    val acceptResponse = RequestVolRepository.VolAccept(requestID.toString())
                    if (acceptResponse.isError) throw IllegalAccessError() else {
                        println("accept")
                        finish()
                    }

                } catch (exception: Throwable) {
                    runOnUiThread {
                        val err = when (exception) {
                            is IllegalAccessError -> "Неверный логин или пароль, мудила"
                            else -> "Все наебнулосьRVA"
                        }
                        println(err)
                    }
                }
            }.start()
        }
        activity_request_data_reject.setOnClickListener {
            Thread {
                try {
                    val rejectResponse = RequestVolRepository.VolReject(requestID.toString())
                    if (rejectResponse.isError) throw IllegalAccessError() else {
                        println("reject")
                        finish()
                    }

                } catch (exception: Throwable) {
                    runOnUiThread {
                        val err = when (exception) {
                            is IllegalAccessError -> "Неверный логин или пароль, мудила"
                            else -> exception.message
                        }
                        println(err)
                    }
                }
            }.start()

        }


    }

}