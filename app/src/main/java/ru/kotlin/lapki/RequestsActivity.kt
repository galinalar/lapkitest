package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_request.*

class RequestsActivity: AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        session = SessionManager(this)
        val key = intent.getStringExtra("mod")
        val ID = intent.getIntExtra("id", 0)
        activity_request_volonteer.setOnClickListener{
            session.Requests("vol", key)
            startActivity(Intent(this, RequestslistVolActivity::class.java).apply {
                putExtra("mod", key)
                putExtra("id", ID)
            })
        }
        activity_request_parents.setOnClickListener{
            session.Requests("par",  key)
            startActivity(Intent(this, RequestslistOwnerActivity::class.java).apply {
                putExtra("mod", key)
                putExtra("id", ID)
            })
        }
    }
}