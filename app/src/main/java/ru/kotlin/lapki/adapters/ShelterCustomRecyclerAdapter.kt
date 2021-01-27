package ru.kotlin.lapki.adapters

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.kotlin.lapki.*

class ShelterCustomRecyclerAdapter (private val valueDescription: List<String>, private val valueID: List<Int>, val context: Context) :
        RecyclerView.Adapter<ShelterCustomRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount() = valueDescription.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ButtonShelter?.text = valueDescription[position]
        holder.ButtonShelter?.setOnClickListener {
          //  Intent.FLAG_ACTIVITY_NEW_TASK

            ContextCompat.startActivity(context, Intent(context, ShelterAccauntActivity::class.java).apply {
                putExtra("id", valueID[position])
            }.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)
                println(valueID[position])
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ButtonShelter: Button? = null

        init {
            ButtonShelter = itemView?.findViewById(R.id.list_item_button)
        }
    }
}