package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_petac.*
import kotlinx.android.synthetic.main.activity_shelterac.*
import okhttp3.*
import org.json.JSONObject
import ru.kotlin.lapki.api.PetAccountRepository
import ru.kotlin.lapki.api.ShelterAccountRepository
import ru.kotlin.lapki.api.entities.RequestOwner
import ru.kotlin.lapki.api.entities.ShelterAccount
import java.io.IOException
import java.text.SimpleDateFormat

class PetAccountActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petac)
        val session = SessionManager(this)
        val petID = intent.getIntExtra("id",0)


        activity_petac_del.setOnClickListener{
            Delete.Del( "pet", petID.toString())
        }
        activity_petac_req.setOnClickListener { session.Requests("par", "pet")
            startActivity(Intent(this, RequestslistOwnerActivity::class.java).apply {
                putExtra("mod", "pet")
                putExtra("id", petID)
            })
        }
        activity_petac_change.setOnClickListener {
            //session.Pet(session.getPet())
            //   session.CHPET(idpet, statuss.text.toString(), she.text.toString(), rolid.text.toString(), shelid.text.toString())
            session.Mod("change")
            println(activity_petac_rolid.text.toString())
            //session.Shelter(shelid.text.toString())
            println("SSSS ${session.getChPet().get(SessionManager.KEY_IDPET)} ${session.getChPet().get(SessionManager.KEY_CHANGE_IDROLE)} ${session.getChPet().get(SessionManager.KEY_CHANGE_IDSHELTER)} ")
            startActivity(Intent(this, PetChangeActivity::class.java).apply {
                putExtra("id", petID)
            })
        }
        activity_petac_return_list.setOnClickListener {
            startActivity(Intent(this,PetListActivity::class.java))
        }
        activity_petac_return_shelter.setOnClickListener {
            startActivity(Intent(this,ShelterAccount::class.java))
        }
        Thread {
            try {
                val petResponse = PetAccountRepository.get(petID.toString())
                if (petResponse.isError) throw IllegalAccessError() else {
                    val format = SimpleDateFormat("dd/MM/yyy")
                    runOnUiThread {
                        activity_petac_name.setText(petResponse.pet.first().pet_name)
                        activity_petac_status.setText(petResponse.pet.first().role)
                        activity_petac_bd.setText(format.format(petResponse.pet.first().birth_date))
                        activity_petac_describe.setText(petResponse.pet.first().pet_describe)
                        activity_petac_type.setText(petResponse.pet.first().type)
                        activity_petac_shelter.setText(petResponse.pet.first().shelter)
                        activity_petac_sex.setText(petResponse.pet.first().sex)
                        activity_petac_breed.setText(petResponse.pet.first().breed)
                        activity_petac_active.setText(petResponse.pet.first().active)
                        activity_petac_size.setText(petResponse.pet.first().size.toString())
                        activity_petac_wool.setText(petResponse.pet.first().wool)
                        activity_petac_hypo.setText(petResponse.pet.first().hypo)
                        activity_petac_dressir.setText(petResponse.pet.first().dressir)
                        activity_petac_dogs.setText(petResponse.pet.first().dogs)
                        activity_petac_animal.setText(petResponse.pet.first().animal)
                        activity_petac_child.setText(petResponse.pet.first().child)
                        activity_petac_children.setText(petResponse.pet.first().children)
                        activity_petac_teens.setText(petResponse.pet.first().teens)
                        activity_petac_ills.setText(petResponse.pet.first().ills)
                        activity_petac_sounds.setText(petResponse.pet.first().sounds)
                        activity_petac_talk.setText(petResponse.pet.first().talkative)

                    }
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