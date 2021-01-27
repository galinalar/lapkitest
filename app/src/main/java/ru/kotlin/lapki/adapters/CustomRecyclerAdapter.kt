package ru.kotlin.lapki.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import ru.kotlin.lapki.PetAc
import ru.kotlin.lapki.R
import ru.kotlin.lapki.SessionManager
import ru.kotlin.lapki.ShelterAc

class CustomRecyclerAdapter(private val values: List<String>, private val valu: List<Int>, val context: Context) :
        RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    val intentB = Intent(context, ShelterAc::class.java)
    //val intentBp = Intent(context, PetAc()::class.java)


    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.largeTextView?.text = values[position]
        holder.Secret?.text = valu[position].toString()
        holder.largeTextView?.setOnClickListener {
            var ses = SessionManager(context)
            if (ses.getS() == "shelter") {
                ses.Shelter(holder.Secret?.text.toString())
                startActivity(context, intentB, null)
                println(values[position])
                println(valu[position])
            }else{
                ses.Pet(valu[position].toString())
                startActivity(context, Intent(context, PetAc::class.java), null)
                println(values[position])
                println("ID ${valu[position]}")
            }
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var largeTextView: Button? = null
        var Secret: TextView? = null

        init {
            largeTextView = itemView?.findViewById(R.id.shel)
            Secret = itemView?.findViewById(R.id.ii)
        }
    }
}