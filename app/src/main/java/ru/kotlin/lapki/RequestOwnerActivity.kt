package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request_owner_data.*
import ru.kotlin.lapki.api.*
import java.text.SimpleDateFormat

class RequestOwnerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_owner_data)
        val userID = intent.getIntExtra("id", 0)
        val requestID = intent.getIntExtra("id_req", 0)
        val petID = intent.getIntExtra("id_pet", 0)
        Thread {
            try {
                val userResponse = UserAccountRepository.get(userID.toString())
                if (userResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    activity_request_owner_data_user_name.setText(userResponse.user.first().user_name)
                    activity_request_owner_data_user_surname.setText(userResponse.user.first().surname)
                    activity_request_owner_data_user_birthday.setText(format.format(userResponse.user.first().birth_date))
                    activity_request_owner_data_user_sex.setText(userResponse.user.first().sex)
                    activity_request_owner_data_user_city.setText(userResponse.user.first().city)
                    activity_request_owner_data_user_tel.setText(userResponse.user.first().telephone)
                    activity_request_owner_data_user_email.setText(userResponse.user.first().email)
                    activity_request_owner_data_user_describe.setText(userResponse.user.first().describe)
                    println(userResponse.user.first().describe)
                    println(requestID)
                    val reqResponse = RequestRepository.VolReq(requestID.toString())
                    if (reqResponse.isError) throw IllegalAccessError() else {
                        activity_request_owner_data_user_comment.setText(reqResponse.request.first().comment)
                    }



                }
                val petResponse = PetAccountRepository.get(petID.toString())
                if (petResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    println(petResponse.pet.first().role)

                    activity_request_owner_data_pet_role.setText(petResponse.pet.first().role)
                    activity_request_owner_data_pet_bd.setText(format.format(petResponse.pet.first().birth_date))
                    activity_request_owner_data_pet_type.setText(petResponse.pet.first().type)
                    activity_request_owner_data_pet_shelter.setText(petResponse.pet.first().shelter)
                    activity_request_owner_data_pet_sex.setText(petResponse.pet.first().sex)
                    activity_request_owner_data_pet_breed.setText(petResponse.pet.first().breed)
                    activity_request_owner_data_pet_name.setText(petResponse.pet.first().pet_name)
                    activity_request_owner_data_pet_describe.setText(petResponse.pet.first().pet_describe)


                }
                finish()


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
        activity_request_owner_data_user_photo.setOnClickListener {
            startActivity(Intent(this, UserAccountActivity::class.java).apply {
                putExtra("id", userID)
            })
        }
        activity_request_owner_data_pet_photo.setOnClickListener {
            startActivity(Intent(this, PetAccountActivity::class.java).apply {
                putExtra("id", petID)
            })
        }
        activity_request_owner_data_accept.setOnClickListener {
            Thread {
                try {
                    val acceptResponse = RequestOwnerRepository.OwnerAccept(requestID.toString())
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
        activity_request_owner_data_reject.setOnClickListener {
            Thread {
                try {
                    val rejectResponse = RequestOwnerRepository.OwnerReject(requestID.toString())
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