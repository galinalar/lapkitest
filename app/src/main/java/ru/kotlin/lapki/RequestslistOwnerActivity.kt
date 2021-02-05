package ru.kotlin.lapki

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_request_list.*
import ru.kotlin.lapki.adapters.OwnerCustomRecyclerAdapter
import ru.kotlin.lapki.adapters.VolCustomRecyclerAdapter
import ru.kotlin.lapki.api.RequestRepository
import ru.kotlin.lapki.api.responses.RequestOwnerResponse
import ru.kotlin.lapki.api.responses.RequestVolResponse

class RequestslistOwnerActivity : AppCompatActivity() {

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
        activity_request_list_head.text = "Заявки от родителей"
        println(key)
        println(id)
        Thread {
            try {
                val parResponse: RequestOwnerResponse
                if (key == "shelter") parResponse = RequestRepository.OwnerShelter(id.toString()) else {
                    if (key == "pet") parResponse = RequestRepository.OwnerPet(id.toString()) else parResponse = RequestRepository.OwnerUser(id.toString())}
                if (parResponse.isError) throw IllegalAccessError() else {
                    val dataID = mutableListOf<Int>()
                    (0..parResponse.request.size - 1).forEach { i -> dataID.add(parResponse.request[i].id_req) }
                    val dataRole = mutableListOf<String>()
                    (0..parResponse.request.size - 1).forEach { i -> dataRole.add(parResponse.request[i].role) }
                    val dataFIO = mutableListOf<String>()
                    (0..parResponse.request.size - 1).forEach { i -> dataFIO.add("${parResponse.request[i].user_name} ${parResponse.request[i].user_surname}") }
                    val dataType = mutableListOf<String>()
                    (0..parResponse.request.size - 1).forEach { i -> dataType.add(parResponse.request[i].type_name) }
                    val dataObj = mutableListOf<String>()
                    (0..parResponse.request.size - 1).forEach { i -> dataObj.add("${parResponse.request[i].pet_name} из ${parResponse.request[i].shelter}") }
                    println(dataID)
                    activity_request_list_recyclerView.adapter = OwnerCustomRecyclerAdapter(parResponse.request, applicationContext)
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