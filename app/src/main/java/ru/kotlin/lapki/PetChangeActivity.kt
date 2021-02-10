package ru.kotlin.lapki

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_addpet.*
import kotlinx.android.synthetic.main.activity_addshelter.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.kotlin.lapki.adapters.SpinRoleAdapter
import ru.kotlin.lapki.adapters.SpinShelterAdapter
import ru.kotlin.lapki.api.PetAccountRepository
import ru.kotlin.lapki.api.PetModifyRepository
import ru.kotlin.lapki.api.PetRoleRepository
import ru.kotlin.lapki.api.ShelterListRepository
import ru.kotlin.lapki.api.entities.Shelter
import ru.kotlin.lapki.api.entities.ShelterRole
import java.text.SimpleDateFormat

class PetChangeActivity: AppCompatActivity() {
    @SuppressLint("SimpleDateFormat")
    val acthypotalk:Array<String> = arrayOf("Да", "Нет")
    val woo:Array<String> = arrayOf("Без шерсти","Короткая","Средняя","Длинная","Средняя, требующая тримминга","Длинная, требующая тримминга","Очень длинная")
    val dr:Array<String> = arrayOf("Хорошо","Тяжело","Знает базовые команды","Неизвестно")
    val relat:Array<String> = arrayOf("Хорошо", "Плохо", "Нейтрально", "Неизвестно")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpet)
        val petID = intent.getIntExtra("id",0)
        val days: Array<Int> = DateSpinner.dayArray
        val months: Array<Int> = DateSpinner.monthArray
        activity_addpet_day.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)
        activity_addpet_month.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)

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
            println("Hello")
            try {
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
                val petResponse = PetAccountRepository.get(petID.toString())
                if (petResponse.isError) throw IllegalAccessError() else {
                    runOnUiThread {
                        activity_addpet_shelter.setSelection(petShelterResponse.list.indexOfFirst { it.id_shelter == petResponse.pet.first().id_shelter })
                        activity_addpet_name.setText(petResponse.pet.first().pet_name)
                        val format = SimpleDateFormat("yyyy")
                        activity_addpet_day.setSelection(petResponse.pet.first().birth_date.date - 1)
                        activity_addpet_month.setSelection(petResponse.pet.first().birth_date.month)
                        activity_addpet_year.setText(format.format(petResponse.pet.first().birth_date))

                        println(petResponse.pet.first().breed)
                        activity_addpet_breed.setText(petResponse.pet.first().breed)
                        activity_addpet_role.setSelection(petRoleResponse.role.indexOfFirst { it.id_role == petResponse.pet.first().id_role })
                        if (petResponse.pet.first().sex == "Ж") activity_addpet_women.setChecked(
                            true
                        ) else activity_addpet_men.setChecked(true)
                        activity_addpet_active.setSelection(checkActhypotalk(petResponse.pet.first().active))
                        activity_addpet_size.setText(petResponse.pet.first().size.toString())
                        var ww = -1
                        for (i in 0..woo.size - 1) {
                            if (woo[i] == petResponse.pet.first().wool) {
                                ww = i
                                break
                            }
                        }
                        activity_addpet_wool.setSelection(ww)
                        activity_addpet_hypo.setSelection(checkActhypotalk(petResponse.pet.first().hypo))
                        ww = -1
                        for (i in 0..dr.size - 1) {
                            if (dr[i] == petResponse.pet.first().dressir) {
                                ww = i
                                break
                            }
                        }
                        activity_addpet_dressir.setSelection(ww)
                        activity_addpet_dogs.setSelection(checkRel(petResponse.pet.first().dogs))
                        activity_addpet_animal.setSelection(checkRel(petResponse.pet.first().animal))
                        activity_addpet_child.setSelection(checkRel(petResponse.pet.first().child))
                        activity_addpet_children.setSelection(checkRel(petResponse.pet.first().children))
                        activity_addpet_teens.setSelection(checkRel(petResponse.pet.first().teens))
                        activity_addpet_ills.setText(petResponse.pet.first().ills.toString())
                        activity_addpet_sounds.setSelection(checkRel(petResponse.pet.first().sounds))
                        activity_addpet_talk.setSelection(checkActhypotalk(petResponse.pet.first().talkative))
                        activity_addpet_describe.setText(petResponse.pet.first().pet_describe)//строка все ломает
                    }

                }


            } catch (exception: Throwable) {
                runOnUiThread {
                    activity_addpet_error.text = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьPetChange"
                    }
                }
            }
        }.start()

        activity_addpet_mod.setText("Изменить")
        activity_addpet_head.setText("Изменение данных питомца")

        activity_addpet_mod.setOnClickListener {
            changePet(petID.toString())
        }
        activity_addpet_return.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }


    }
    fun checkActhypotalk(g: String):Int{
        var ww = -1
        for (i in 0..acthypotalk.size-1) {
            if (acthypotalk[i] == g) {
                ww = i
            }
        }
        return ww
    }
    fun checkRel(g: String):Int{
        var ww = -1
        for (i in 0..relat.size-1) {
            if (relat[i] == g) {
                ww = i
            }
        }
        return ww
    }
    fun changePet(petID: String){
        Thread {
            try {
                var sex: String
                if (activity_addpet_sex.indexOfChild(activity_addpet_sex.findViewById(activity_addpet_sex.checkedRadioButtonId)).toString() =="0") {sex ="Ж"} else sex = "М"
                val ych = setOf('.','/','-')
                if (activity_addpet_year.text.toString().any(ych::contains)) throw IllegalAccessError() else{
                    val petChResponse = PetModifyRepository.change(
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
                            activity_addpet_talk.selectedItem.toString(),
                            petID

                    )
                    println(petChResponse.id)
                    if (petChResponse.isError) throw IllegalAccessError() else {
                        runOnUiThread {
                            startActivity(Intent(this, PetAccountActivity::class.java).apply {
                                putExtra("id", petChResponse.id)
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