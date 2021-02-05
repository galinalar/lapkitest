package ru.kotlin.lapki.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.kotlin.lapki.R
import ru.kotlin.lapki.RequestOwnerActivity
import ru.kotlin.lapki.RequestVolActivity
import ru.kotlin.lapki.api.entities.RequestOwner

class OwnerCustomRecyclerAdapter (private val valueReq: List<RequestOwner>, val context: Context) :
        RecyclerView.Adapter<OwnerCustomRecyclerAdapter.MyViewHolder>() {


    override fun getItemCount() = valueReq.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.request_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id?.text = valueReq[position].id_req.toString()
        holder.role?.text = valueReq[position].role
        holder.name?.text = "${valueReq[position].user_name} ${valueReq[position].user_surname}"
        holder.type?.text = valueReq[position].type_name
        holder.obj?.text = "${valueReq[position].pet_name} Приют: ${valueReq[position].shelter}"
        holder.obj_head?.text = "Питомец: "

        holder.im?.setOnClickListener {
            println(valueReq[position].id_req)
            ContextCompat.startActivity(context, Intent(context, RequestOwnerActivity::class.java).apply {
            putExtra("id", valueReq[position].id_user)
            putExtra("id_req", valueReq[position].id_req)
            putExtra("id_pet", valueReq[position].id_pet)
        }.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), null)

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
        var im: ImageView? = null

        init {
            layout = itemView?.findViewById(R.id.request_list_item_layout)
            id = itemView?.findViewById(R.id.request_list_item_id)
            role = itemView?.findViewById(R.id.request_list_item_role)
            name = itemView?.findViewById(R.id.request_list_item_name)
            type = itemView?.findViewById(R.id.request_list_item_type)
            obj_head = itemView?.findViewById(R.id.request_list_item_obj_head)
            obj = itemView?.findViewById(R.id.request_list_item_obj)
            im = itemView?.findViewById(R.id.request_list_item_imageView)
        }
    }
}