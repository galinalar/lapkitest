package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_user.*
import ru.kotlin.lapki.api.UserAccountRepository
import java.text.SimpleDateFormat

class UserAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        val session = SessionManager(this)
        session.getUserDetails().get(SessionManager.KEY_ID)
        val userID = intent.getIntExtra("id", 0)
        Thread {
            try {
                val userResponse = UserAccountRepository.get(userID.toString())
                if (userResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        val format = SimpleDateFormat("dd/MM/yyy")
                        activity_user_name.setText(userResponse.user.first().user_name)
                        activity_user_surname.setText(userResponse.user.first().surname)
                        activity_user_login.setText("(${userResponse.user.first().login})")
                        activity_user_birthday.setText(format.format(userResponse.user.first().birth_date))
                        activity_user_sex.setText(userResponse.user.first().sex)
                        activity_user_city.setText(userResponse.user.first().city)
                        activity_user_telephone.setText(userResponse.user.first().telephone)
                        activity_user_email.setText(userResponse.user.first().email)
                        activity_user_describe.setText(userResponse.user.first().describe)
                        println(userResponse.user.first().describe)
                    }
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьUserAc"
                    }
                    println(err)
                }
            }
        }.start()
        activity_user_change.setOnClickListener {
            startActivity(Intent(this, UserModifyActivity::class.java).apply {
                putExtra("id", userID)
            })
        }
        activity_user_delete.setOnClickListener{
            Delete.Del("users",session.getUserDetails().get(SessionManager.KEY_ID)!!)
        }
        activity_user_request.setOnClickListener {
            session.Requests("req", "users")
            startActivity(Intent(this, RequestsActivity::class.java).apply {
                putExtra("mod", "users")
                putExtra("id", userID)
            })
        }
    }

}