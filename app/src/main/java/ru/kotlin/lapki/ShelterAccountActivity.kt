package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_shelterac.*
import ru.kotlin.lapki.api.ShelterAccountRepository
import java.text.SimpleDateFormat

class ShelterAccountActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelterac)
        val session = SessionManager(applicationContext)
        val shelterID = intent.getIntExtra("id", 0)
        if (shelterID != null) {
            println(shelterID)
        }

        activity_shelterac_pet.setOnClickListener {
            session.StartP("petshelter")
            session.Shelter(shelterID.toString())
            startActivity(Intent(this, PetShelterListActivity::class.java))
        }
        activity_shelterac_return.setOnClickListener {
            startActivity(Intent(this, ShelterListActivity::class.java))
        }
        activity_shelterac_change.setOnClickListener {
            startActivity(Intent(this, ShelterChangeActivity::class.java).apply {
                putExtra("id", shelterID)
            })
        }
        activity_shelterac_delete.setOnClickListener {
            Delete.Del(applicationContext, "shelter", shelterID.toString())
        }
        activity_shelterac_req_proba.setOnClickListener {
            session.Requests("req", "shelter")
            val id = session.getUserDetails().get(SessionManager.KEY_ID)?.toInt()
            startActivity(Intent(this, RequestVolAddActivity::class.java).apply {
                putExtra("id", id)
                putExtra("id_shelter", shelterID.toInt())
            })
        }
        activity_shelterac_req.setOnClickListener {
            startActivity(Intent(this, RequestsActivity::class.java).apply {
                putExtra("mod", "shelter")
                putExtra("id", shelterID)
            })
        }
        Thread {
            try {
                val shelterResponse = ShelterAccountRepository.get(shelterID.toString())
                if (shelterResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    activity_shelterac_name.setText(shelterResponse.shelter.first().shelter_name)
                    activity_shelterac_bd.setText(format.format(shelterResponse.shelter.first().birth_date))
                    activity_shelterac_city.setText(shelterResponse.shelter.first().city)
                    activity_shelterac_describe.setText(shelterResponse.shelter.first().describe)
                    println(shelterResponse.shelter.first().describe)
                    activity_shelterac_type.setText(shelterResponse.shelter.first().type)
                                    }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьShelterAc"
                    }
                }
            }
        }.start()
    }
}