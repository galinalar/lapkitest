package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request.*

class Requests: AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        session = SessionManager(applicationContext)
        val key = session.getReq().get(SessionManager.KEY_REQUEST_START)
        activity_request_volonteer.setOnClickListener{
            session.Requests("vol", key)
            startActivity(Intent(this, Requestslist::class.java))
        }
        activity_request_parents.setOnClickListener{
            session.Requests("par",  key)
            startActivity(Intent(this, Requestslist::class.java))
        }
    }
}