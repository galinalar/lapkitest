package ru.kotlin.lapki.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_registr.*
import ru.kotlin.lapki.R
import ru.kotlin.lapki.RequestOwnerActivity
import ru.kotlin.lapki.api.entities.Answers
import ru.kotlin.lapki.api.entities.Question
import ru.kotlin.lapki.api.entities.RequestOwner

class TestingCustomRecyclerAdapter(
    private val valueQuestion: List<Question>,
    private val valueAnswer: List<Answers>,
    val valueMap: MutableMap<Int, MutableList<Int>>,
    val context: Activity
) :
    RecyclerView.Adapter<TestingCustomRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount() = valueQuestion.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.test_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.question?.text = valueQuestion[position].question
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(40, 0, 0, 0)
        if (valueQuestion[position].id_type == 1) {
            val radioGroup = RadioGroup(context)
            radioGroup.layoutParams = params
            for (i in 0..valueAnswer.size - 1) {
                if (valueAnswer[i].id_question == valueQuestion[position].id_question) {
                    val radioButton = RadioButton(context)
                    radioButton.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    radioButton.setText(valueAnswer[i].answer)
                    radioButton.id = valueAnswer[i].id_answer
                    radioGroup.addView(radioButton)
                }
            }
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val questionId = valueQuestion[position].id_question
                valueMap[questionId] = mutableListOf(checkedId)
            }
            holder.layout?.addView(radioGroup)

            /*holder.question?.setOnClickListener {
            println(radioGroup.findViewById<View>(radioGroup.checkedRadioButtonId).id)
            println(position)
        }*/


        } else {
            if (valueQuestion[position].id_type == 2) {
                val ans: List<Answers> =
                    valueAnswer.filter { it.id_question == valueQuestion[position].id_question }
                val checkans = mutableMapOf<Int, Boolean>()

                for (i in 0..ans.size - 1) {
                    val check = CheckBox(context)
                    check.layoutParams = params
                    check.id = ans[i].id_answer
                    check.setText(ans[i].answer)
                    holder.layout?.addView(check)
                    check.setOnCheckedChangeListener { buttonView, isChecked ->
                        checkans[check.id] = isChecked
                        val questionId = valueQuestion[position].id_question
                        valueMap[questionId] =
                            checkans.filter { it.value == true }.keys.toMutableList()
                    }

                }
/*holder.question?.setOnClickListener {
println("checkans")
   println(valueMap)
   println(checkans.filter { it.value == true }.keys)

} for (i in 0..valueAnswer.size-1) {
       val check = CheckBox(context)
       check.layoutParams = params
       check.id = valueAnswer[i].id_answer
       check.setText(valueAnswer[i].answer)
       holder.layout?.addView(check)
   }
holder.question?.setOnClickListener {



   }*/
            }

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout? = null
        var question: TextView? = null


        init {
            layout = itemView?.findViewById(R.id.test_item_layout)
            question = itemView?.findViewById(R.id.test_item_question)

        }
    }
}