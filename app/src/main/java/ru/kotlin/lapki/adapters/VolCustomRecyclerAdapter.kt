package ru.kotlin.lapki.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import ru.kotlin.lapki.PetAccountActivity
import ru.kotlin.lapki.R

class VolCustomRecyclerAdapter(private val valueID: List<Int>, private val valueRole: List<String>, private val valueFIO: List<String>, private val valueType: List<String>, private val valueObj: List<String>, val context: Context) :
        RecyclerView.Adapter<VolCustomRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount() = valueID.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.request_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id?.text = valueID[position].toString()
        holder.role?.text = valueRole[position]
        holder.name?.text = valueFIO[position]
        holder.type?.text = valueType[position]
        holder.obj?.text = valueObj[position]
        holder.obj_head?.text = "Приют: "

        holder.layout?.setOnClickListener {
            println(valueID[position])
            //ContextCompat.startActivity(context, Intent(context, PetAccountActivity::class.java).apply {
                //putExtra("id", valueID[position])
            //}.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)

        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: LinearLayout? = null
        var id: TextView? = null
        var role: TextView? = null
        var name: TextView? = null
        var type: TextView? = null
        var obj_head: TextView? = null
        var obj: TextView? = null

        init {
            layout = itemView?.findViewById(R.id.request_list_item_layout)
            id = itemView?.findViewById(R.id.request_list_item_id)
            role = itemView?.findViewById(R.id.request_list_item_role)
            name = itemView?.findViewById(R.id.request_list_item_name)
            type = itemView?.findViewById(R.id.request_list_item_type)
            obj_head = itemView?.findViewById(R.id.request_list_item_obj_head)
            obj = itemView?.findViewById(R.id.request_list_item_obj)
        }
    }
}