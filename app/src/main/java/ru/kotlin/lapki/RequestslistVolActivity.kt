package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_request_list.*
import okhttp3.Response
import ru.kotlin.lapki.adapters.PetCustomRecyclerAdapter
import ru.kotlin.lapki.adapters.VolCustomRecyclerAdapter
import ru.kotlin.lapki.api.PetListRepository
import ru.kotlin.lapki.api.RequestRepository
import ru.kotlin.lapki.api.entities.RequestVol
import ru.kotlin.lapki.api.responses.RequestVolResponse
import java.text.SimpleDateFormat

class RequestslistVolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_list)
        activity_request_list_recyclerView.layoutManager = LinearLayoutManager(this)
        activity_request_list_return.setOnClickListener {
          //  startActivity(Intent(this, StartActivity::class.java))
        }
        activity_request_list_add.setOnClickListener {
           // session.Mod("add")
            //startActivity(Intent(this, PetAddActivity::class.java))
        }
        //if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") activity_list_add.visibility = View.VISIBLE
        activity_request_list_head.text = "Заявки волонтеров"

    }

    override fun onResume() {
        super.onResume()
        val key = intent.getStringExtra("mod")
        val id = intent.getIntExtra("id", 0)
        Thread {
            try {
                val volResponse: RequestVolResponse
                if (key == "shelter") volResponse = RequestRepository.VolShelter(id.toString()) else volResponse = RequestRepository.VolUser(id.toString())
                if (volResponse.isError) {
                    throw IllegalAccessError()
                } else {
                    runOnUiThread {
                        activity_request_list_recyclerView.adapter = VolCustomRecyclerAdapter(volResponse.request, this)
                    }
                }
            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Мудила, мразь, говно, пидор, сука, блядина"
                        else -> "Все наебнулосьReqVol"
                    }
                }
            }
        }.start()
    }
}