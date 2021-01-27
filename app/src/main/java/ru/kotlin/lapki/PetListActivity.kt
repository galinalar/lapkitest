package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_authorization.*
import kotlinx.android.synthetic.main.activity_list.*
import ru.kotlin.lapki.adapters.PetCustomRecyclerAdapter
import ru.kotlin.lapki.adapters.ShelterCustomRecyclerAdapter
import ru.kotlin.lapki.api.PetListRepository
import ru.kotlin.lapki.api.ShelterListRepository
import java.text.SimpleDateFormat

class PetListActivity : AppCompatActivity() {

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
            startActivity(Intent(this, ModifyPet::class.java))
        }
        //if (session.getUserDetails().get(SessionManager.KEY_ROLE) == "1") activity_list_add.visibility = View.VISIBLE
        activity_list_head.text = "Подопечные"
        Thread {
        try {
            val petResponse = PetListRepository.allpets()
            if (petResponse.isError) throw IllegalAccessError() else
            {
                val dataDescribe = mutableListOf<String>()
                val format = SimpleDateFormat("dd/MM/yyy")
                (0..petResponse.list.size - 1).forEach { i -> dataDescribe.add("${petResponse.list[i].pet_name} Дата рождения: ${format.format(petResponse.list[i].birth_date)} Статус: ${petResponse.list[i].status} Пол: ${petResponse.list[i].sex} Приют: ${petResponse.list[i].shelter}") }
                val dataID = mutableListOf<Long>()
                (0..petResponse.list.size - 1).forEach { i -> dataID.add(petResponse.list[i].id_pet) }
                activity_list_recyclerView.adapter = PetCustomRecyclerAdapter(dataDescribe, dataID, applicationContext)
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