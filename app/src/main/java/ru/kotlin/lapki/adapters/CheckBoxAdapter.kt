package ru.kotlin.lapki.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kotlin.lapki.R
import ru.kotlin.lapki.RequestOwnerActivity
import ru.kotlin.lapki.api.entities.Answers
import ru.kotlin.lapki.api.entities.RequestOwner

class CheckBoxAdapter (private val value: List<Answers>, val valueList: MutableList<Int>, val context: Activity) :
    RecyclerView.Adapter< CheckBoxAdapter.MyViewHolder>() {


    override fun getItemCount() = value.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent?.context).inflate(R.layout.test_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(40, 0, 0, 0)
        val check = CheckBox(context)
        check.layoutParams = params
        check.id = value[position].id_answer
        check.setText(value[position].answer)
        holder.layout?.addView(check)
        check.setOnCheckedChangeListener { buttonView, isChecked ->  valueList.add(value[position].id_answer)}
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