package ru.kotlin.lapki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class Navigation: AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        session = SessionManager(this)
        var user = session.getUserDetails()
        var id = user.get(SessionManager.KEY_ID)
        var role = user.get(SessionManager.KEY_ROLE)
        textView.setText(id)
        textView2.setText(role)
        val pa = "Aishawhite1"
        val secretKey = "662ede816988e58fb6d057d9d85605e0"
        val pe = Cryption()
        val pee = pe.encrypt(pa).toString()
        textView3.setText(pe.encrypt(pa))
        val pd = Cryption()
        textView4.setText(pd.decryptWithAES(pee))
        println("pd $pd")
    }
}