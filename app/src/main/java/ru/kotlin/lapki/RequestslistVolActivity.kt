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

        val session = SessionManager(applicationContext)
        val key = intent.getStringExtra("mod")
        val id = intent.getIntExtra("id", 0)
        activity_request_list_add.setOnClickListener {
           // session.Mod("add")
            //startActivity(Intent(this, PetAddActivity::class.java))
        }
        //if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") activity_list_add.visibility = View.VISIBLE
        activity_request_list_head.text = "Заявки волонтеров"
        Thread {
            try {
                val volResponse: RequestVolResponse
                if (key == "shelter") volResponse = RequestRepository.VolShelter(id.toString()) else volResponse = RequestRepository.VolUser(id.toString())
                if (volResponse.isError) throw IllegalAccessError() else
                {
                    val dataID = mutableListOf<Int>()
                    (0..volResponse.request.size - 1).forEach { i -> dataID.add(volResponse.request[i].id_req) }
                    val dataRole = mutableListOf<String>()
                    (0..volResponse.request.size - 1).forEach { i -> dataRole.add(volResponse.request[i].role) }
                    val dataFIO = mutableListOf<String>()
                    (0..volResponse.request.size - 1).forEach { i -> dataFIO.add("${volResponse.request[i].user_name} ${volResponse.request[i].user_surname}") }
                    val dataType = mutableListOf<String>()
                    (0..volResponse.request.size - 1).forEach { i -> dataType.add("Волонтер") }
                    val dataObj = mutableListOf<String>()
                    (0..volResponse.request.size - 1).forEach { i -> dataObj.add(volResponse.request[i].shelter) }

                    activity_request_list_recyclerView.adapter = VolCustomRecyclerAdapter(dataID, dataRole, dataFIO, dataType, dataObj, applicationContext)
                }


            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьReqVol"
                    }
                }
            }
        }.start()
    }
}