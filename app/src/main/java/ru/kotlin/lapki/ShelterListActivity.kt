package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.activity_list.*
import ru.kotlin.lapki.adapters.ShelterCustomRecyclerAdapter
import ru.kotlin.lapki.api.ShelterListRepository
import java.text.SimpleDateFormat

class ShelterListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        activity_list_recyclerView.layoutManager = LinearLayoutManager(this)
        activity_list_return.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
        }

        val session = SessionManager(applicationContext)
        activity_list_add.setOnClickListener {
            session.Mod("add")
            startActivity(Intent(this, ModifyShelter::class.java))
        }
        //  if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") activity_list_add.visibility = View.VISIBLE
        activity_list_head.text = "Приюты"
        Thread {
            try {
                val shelterResponse = ShelterListRepository.shelters()
                if (shelterResponse.isError) throw IllegalAccessError() else {
                    val dataDescribe = mutableListOf<String>()
                    val format = SimpleDateFormat("dd/MM/yyy")
                    (0..shelterResponse.list.size - 1).forEach { i -> dataDescribe.add("${shelterResponse.list[i].shelter_name} Город: ${shelterResponse.list[i].city} Дата рождения: ${format.format(shelterResponse.list[i].birth_date)}") }
                    val dataID = mutableListOf<Int>()
                    (0..shelterResponse.list.size - 1).forEach { i -> dataID.add(shelterResponse.list[i].id_shelter) }
                    runOnUiThread {
                        activity_list_recyclerView.adapter = ShelterCustomRecyclerAdapter(dataDescribe, dataID, this)
                    }
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулось1"
                    }
                }
            }
        }.start()
    }
}