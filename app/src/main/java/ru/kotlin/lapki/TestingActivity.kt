package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.internal.bind.TreeTypeAdapter
import kotlinx.android.synthetic.main.activity_test.*
import ru.kotlin.lapki.adapters.TestingCustomRecyclerAdapter
import ru.kotlin.lapki.api.TestingRepository
import ru.kotlin.lapki.api.TimeRepository
import java.text.SimpleDateFormat


class TestingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        activity_test_question.layoutManager = LinearLayoutManager(this)
        val map = mutableMapOf<Int,MutableList<Int>>()
        val listQ = mutableListOf<Int>()
        val idUser = 9
        Thread {
            try {
                val questionResponse = TestingRepository.getquestion()
                if (questionResponse.isError) throw IllegalAccessError() else
                {
                    val answerResponse = TestingRepository.getanswer()
                    if (answerResponse.isError) throw IllegalAccessError() else {

                        runOnUiThread {
                            activity_test_question.adapter = TestingCustomRecyclerAdapter(questionResponse.question,answerResponse.answer, map, this)
                            (questionResponse.question.indices).forEach { i -> listQ.add(questionResponse.question[i].id_question) }
                        }
                    }
                }

            } catch (exception: Throwable) {
                runOnUiThread {
                    val err = when (exception) {
                        is IllegalAccessError -> "Неверный логин или пароль, мудила"
                        else -> "Все наебнулосьTect"
                    }
                }
            }
        }.start()

        activity_test_save.setOnClickListener{
            println(map)
            println(map.values)
            val listQNA = mutableListOf<Int>()
            for(i in 0 until listQ.size){
                if(!map.contains(listQ[i])){
                    listQNA.add(listQ[i])
                }
            }
            if (listQNA.size!=0){
                println("Не все" + listQNA)
            }else{
                println(map.values.toList())
                val lista = mutableListOf<Int>()
                for (i in 0 until map.size){
                    if (map.values.toList()[i].size ==1){
                        lista.add(map.values.toList()[i][0])
                    }else{
                        for (j in 0 until map.values.toList()[i].size){
                            lista.add(map.values.toList()[i][j])
                        }
                    }
                }
                println(lista)
                Thread{
                    try {

                        val timeTestResponse = TimeRepository.get(idUser.toString())
                        var time = if (timeTestResponse.isError or (timeTestResponse.time == 0)) 1 else timeTestResponse.time+1
                        val testResponse = TimeRepository.set(idUser.toString(), time.toString())
                        if (testResponse.isError) throw IllegalAccessError() else {
                            (0 until lista.size).forEach { i ->
                                println(lista[i])
                                val testanswResponse = TestingRepository.setanswer(lista[i].toString(), testResponse.id_test.toString())
                            if(testanswResponse.isError) throw IllegalAccessError() else{
                                println(lista[i])
                            }}
                        }

                    } catch (exception: Throwable) {
                        runOnUiThread {
                            val err = when (exception) {
                                is IllegalAccessError -> "Неверный логин или пароль, мудила"
                                else -> "Все наебнулосьTect"
                            }
                            println(err)
                        }
                    }
                }.start()
            }

        }

    }


}