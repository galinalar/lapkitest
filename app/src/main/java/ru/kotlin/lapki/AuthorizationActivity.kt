package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authorization.*
import ru.kotlin.lapki.api.LoginRepository

class AuthorizationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        activity_authorization_entry.setOnClickListener {
            author()
        }
        activity_authorization_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        activity_authorization_restore.setOnClickListener {
            startActivity(Intent(this, RestoreActivity::class.java))
        }
    }

    private fun author() {
        Thread {
            try {
                val loginResponse = LoginRepository.login(
                        activity_authorization_login.text.toString(),
                        activity_authorization_password.text.toString()
                )
                if (loginResponse.isError) throw IllegalAccessError()
                runOnUiThread {
                    startActivity(Intent(this, StartActivity::class.java).apply {
                        putExtra("user", loginResponse.users.first() as Parcelable)
                    })
                    finish()
                }
            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_authorization_error.text = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулось"
                    }
                }
            }
        }.start()
    }

}
