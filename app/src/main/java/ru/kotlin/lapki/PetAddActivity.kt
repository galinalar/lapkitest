package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addpet.*
import kotlinx.android.synthetic.main.activity_registr.*
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.adapters.SpinShelterAdapter
import ru.kotlin.lapki.adapters.SpinTypeAdapter
import ru.kotlin.lapki.api.*
import ru.kotlin.lapki.api.entities.Shelter
import ru.kotlin.lapki.api.entities.ShelterRole
import ru.kotlin.lapki.api.entities.ShelterType

class PetAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpet)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        activity_addpet_day.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        activity_addpet_month.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        val acthypotalk:Array<String> = arrayOf("Да", "Нет")
        val woo:Array<String> = arrayOf("Без шерсти","Короткая","Средняя","Длинная","Средняя, требующая тримминга","Длинная, требующая тримминга","Очень длинная")
        val dr:Array<String> = arrayOf("Хорошо","Тяжело","Знает базовые команды","Неизвестно")
        val relat:Array<String> = arrayOf("Хорошо", "Плохо", "Нейтрально", "Неизвестно")
        val ad3 = ArrayAdapter(this, android.R.layout.simple_spinner_item, acthypotalk)
        val ad4 = ArrayAdapter(this, android.R.layout.simple_spinner_item, woo)
        val ad5 = ArrayAdapter(this, android.R.layout.simple_spinner_item, dr)
        val ad6 = ArrayAdapter(this, android.R.layout.simple_spinner_item, relat)
        activity_addpet_active.adapter = ad3
        activity_addpet_wool.adapter = ad4
        activity_addpet_hypo.adapter = ad3
        activity_addpet_dressir.adapter = ad5
        activity_addpet_dogs.adapter = ad6
        activity_addpet_animal.adapter = ad6
        activity_addpet_child.adapter = ad6
        activity_addpet_children.adapter = ad6
        activity_addpet_teens.adapter = ad6
        activity_addpet_sounds.adapter = ad6
        activity_addpet_talk.adapter = ad3

        Thread {
            try {
                println("Hello")
                val petShelterResponse = ShelterListRepository.shelters()
                if (petShelterResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        val ad = SpinShelterAdapter(this, petShelterResponse.list)
                        val sp: Spinner = activity_addpet_shelter
                        sp.adapter = ad
                    }

                }
                val petRoleResponse = PetRoleRepository.get()
                if (petRoleResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        activity_addpet_role.adapter = SpinRoleAdapter(this, petRoleResponse.role)
                    }
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_addpet_error.text = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьPetAdd"
                    }
                }
            }
        }.start()

        activity_addpet_mod.setText("Добавить")
        activity_addpet_head.setText("Добавление питомца")

        activity_addpet_mod.setOnClickListener {
            addPet()
        }
        activity_addpet_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
    fun addPet(){
        Thread {
            try {
                var sex: String
                if (activity_addpet_sex.indexOfChild(activity_addpet_sex.findViewById(activity_addpet_sex.checkedRadioButtonId)).toString() =="0") {sex ="Ж"} else sex = "М"
                val ych = setOf('.','/','-')
                if (activity_addpet_year.text.toString().any(ych::contains)) throw IllegalAccessError() else{
                    val petResponse = PetModifyRepository.add(
                            (activity_addpet_shelter.selectedItem as Shelter).id_shelter.toString(),
                            (activity_addpet_role.selectedItem as ShelterRole).id_role.toString(),
                            activity_addpet_name.text.toString(),
                            DateSpinner.dateString(activity_addpet_day.selectedItem.toString(),
                                    activity_addpet_month.selectedItem.toString(), activity_addpet_year.text.toString()),
                            activity_addpet_describe.text.toString(),
                            activity_addpet_breed.text.toString(),
                            sex,
                            activity_addpet_active.selectedItem.toString(),
                            activity_addpet_size.text.toString(),
                            activity_addpet_wool.selectedItem.toString(),
                            activity_addpet_hypo.selectedItem.toString(),
                            activity_addpet_dressir.selectedItem.toString(),
                            activity_addpet_dogs.selectedItem.toString(),
                            activity_addpet_animal.selectedItem.toString(),
                            activity_addpet_child.selectedItem.toString(),
                            activity_addpet_children.selectedItem.toString(),
                            activity_addpet_teens.selectedItem.toString(),
                            activity_addpet_ills.text.toString(),
                            activity_addpet_sounds.selectedItem.toString(),
                            activity_addpet_talk.selectedItem.toString()
                                        )
                    println(petResponse.id)
                    if (petResponse.isError) throw IllegalAccessError() else {
                        runOnUiThread {
                            startActivity(Intent(this, PetAccountActivity::class.java).apply {
                                putExtra("id", petResponse.id)
                            })
                            finish()
                        }
                    }
                }
            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_addpet_error.text = when (exception) {
                        is IllegalAccessError -> "Заполните все поля"
                        else -> exception.message
                    }
                }
            }
        }.start()
    }
}