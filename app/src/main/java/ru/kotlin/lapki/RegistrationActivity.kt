package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.activity_registr.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.LoginRepository
import ru.kotlin.lapki.api.RegistrationRepository
import ru.kotlin.lapki.api.SessionRepository
import java.io.IOException


class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        val ad = ArrayAdapter(this, android.R.layout.simple_spinner_item,days)
        val ad2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,months)
        val sp: Spinner = activity_registr_day
        val sp2: Spinner = activity_registr_month
        sp.adapter = ad
        sp2.adapter= ad2

        activity_registr_registr.setOnClickListener {
            registration()
        }
        activity_registr_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }


    private fun registration(){
        Thread {
            try {
                var sex: String
                if (activity_registr_sex.indexOfChild(activity_registr_sex.findViewById(activity_registr_sex.checkedRadioButtonId)).toString() =="0") {sex ="Ж"} else sex = "М"
                val ych = setOf('.','/','-')
                if ((activity_registr_password.text.toString()!=activity_registr_repeat_password.text.toString()) or (activity_registr_year.text.toString().any(ych::contains)==true)) throw IllegalAccessError() else{
                    val registrationResponse = RegistrationRepository.registration(
                            activity_registr_name.text.toString(),
                            activity_registr_surname.text.toString(),
                            DateSpinner.dateString(activity_registr_day.selectedItem.toString(),
                                    activity_registr_month.selectedItem.toString(), activity_registr_year.text.toString()),
                            sex,
                            activity_registr_login.text.toString(),
                            activity_registr_password.text.toString(),
                            activity_registr_city.text.toString(),
                            activity_registr_email.text.toString(),
                            activity_registr_telephone.text.toString()
                    )
                    if (registrationResponse.isError) throw IllegalAccessError() else {
                        val sessionResponse = SessionRepository.create(
                                registrationResponse.id.toString()
                        )
                        if (sessionResponse.isError) throw IllegalAccessError()
                        val session = SessionManager(applicationContext)
                        session.CreateSession(sessionResponse.id)
                        val s = session.getSessionrDetails()
                        runOnUiThread {
                            startActivity(Intent(this, UserAccountActivity::class.java))
                            session.CreateLoginSession(registrationResponse.id.toString(), "3")
                            finish()
                        }
                    }
                }
            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_registr_error.text = when (exception) {
                        is IllegalAccessError -> "Заполните все поля и проверьте равенство паролей"
                        else -> exception.message
                    }
                }
            }
        }.start()
    }
}