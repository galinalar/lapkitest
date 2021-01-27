package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.activity_start.*
import ru.kotlin.lapki.api.LoginRepository
import ru.kotlin.lapki.api.SessionRepository
import ru.kotlin.lapki.api.entities.UserID

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //val user = intent.getParcelableExtra<Parcelable>("user") as? UserID
        //if (user != null) {
       //     println(user.id)
      //      println(user.role)
      //  }



        sh.setOnClickListener{
            //session.StartP("shelter")
            startActivity(Intent(this, ShelterListActivity::class.java))
        }
        pet.setOnClickListener{
           // session.StartP("pet")
            startActivity(Intent(this, PetListActivity::class.java))
        }
        fpet.setOnClickListener{

           // session.StartP("fpet")
            startActivity(Intent(this, PetLoseListActivity::class.java))
        }
    }
   // private fun getsession(){
    //    Thread {
     //       try {
     //           var session = SessionManager(applicationContext)
        //        val sessionResponse = SessionRepository.get(
        //                session.getSessionrDetails().toString()

         //       )
         //       if (sessionResponse.isError) throw IllegalAccessError()
           //     println(session.getSessionrDetails())
           // } catch (exception: Throwable) {
          //      runOnUiThread {
           //         val error = when (exception) {
           //             is IllegalAccessError -> "Что-то пошло не так"
           //             else -> "Все наебнулось2"
           //         }
           //         println(error)
           //     }
          //  }
      //  }.start()

  //  }
}