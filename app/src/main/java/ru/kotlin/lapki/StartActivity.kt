package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_start.*
import ru.kotlin.lapki.api.entities.UserID

class StartActivity : AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val user = intent.getParcelableExtra<Parcelable>("user") as? UserID
        if (user != null) {
            println(user.id)
            println(user.role)
        }
        session = SessionManager(applicationContext)
        sh.setOnClickListener{
            session.StartP("shelter")
            startActivity(Intent(this, Listt::class.java))
        }
        pet.setOnClickListener{
            session.StartP("pet")
            startActivity(Intent(this, Listt::class.java))
        }
        fpet.setOnClickListener{

            session.StartP("fpet")
            startActivity(Intent(this, Listt::class.java))
        }
    }
}