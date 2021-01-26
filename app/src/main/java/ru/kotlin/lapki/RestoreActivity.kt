package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_restore.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.LoginRepository
import ru.kotlin.lapki.api.RestoreRepository
import java.io.IOException

class RestoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore)
        activity_restore_restore.setOnClickListener{
            restore()
        }
        activity_restore_return.setOnClickListener{
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }
    }
       private fun restore(){
           Thread {
               try {
                   if (activity_restore_password.text.toString() != activity_restore_repeat_password.text.toString()) throw IllegalAccessError() else
                   {
                   val loginResponse = RestoreRepository.check(
                           activity_restore_login.text.toString(),
                           activity_restore_email.text.toString()
                   )
                   if (loginResponse.isError) throw IllegalAccessError() else
                   {
                       val restoreResponse = RestoreRepository.restore(
                               loginResponse.users.first().id.toString(),
                               activity_restore_password.text.toString()
                       )
                       if (restoreResponse.isError) throw Error() else {
                           runOnUiThread {
                               startActivity(Intent(this, AuthorizationActivity::class.java))
                               finish()
                           }
                       }
                   }
                   }
               } catch (exception: Throwable) {
                   runOnUiThread {
                       activity_restore_error.text = when (exception) {
                           is IllegalAccessError -> "Неверный логин или пароль, мудила"
                           is Error -> "Ошибка обновления"
                           else -> "Все наебнулось"
                       }
                   }
               }
           }.start()
    }
}