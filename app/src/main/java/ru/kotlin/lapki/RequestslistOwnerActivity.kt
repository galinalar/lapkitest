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

        val session = SessionManager(this)
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


    }
    override fun onResume() {
        super.onResume()
        val key = intent.getStringExtra("mod")
        val id = intent.getIntExtra("id", 0)
        Thread {
            try {
                val parResponse: RequestOwnerResponse
                if (key == "shelter") parResponse = RequestRepository.OwnerShelter(id.toString()) else {
                    if (key == "pet") parResponse = RequestRepository.OwnerPet(id.toString()) else parResponse = RequestRepository.OwnerUser(id.toString())}
                if (parResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        activity_request_list_recyclerView.adapter =
                            OwnerCustomRecyclerAdapter(parResponse.request, this)
                    }
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