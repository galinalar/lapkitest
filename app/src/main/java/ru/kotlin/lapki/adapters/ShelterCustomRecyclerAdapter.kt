package ru.kotlin.lapki.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.kotlin.lapki.*

class ShelterCustomRecyclerAdapter (private val valueDescription: List<String>, private val valueID: List<Int>, val context: Activity) :
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
            context.startActivity( Intent(context, ShelterAccountActivity::class.java).apply {
                putExtra("id", valueID[position])
            })
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