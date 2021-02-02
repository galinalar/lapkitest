package ru.kotlin.lapki.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.kotlin.lapki.api.entities.ShelterRole
import ru.kotlin.lapki.api.entities.ShelterType

class SpinRoleAdapter (val appcontext: Context, var items : List<ShelterRole>): ArrayAdapter<ShelterRole>(appcontext, android.R.layout.simple_spinner_item, items){

    override fun getItem(position: Int): ShelterRole? {
        return super.getItem(position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
            LayoutInflater.from(appcontext).inflate(android.R.layout.simple_spinner_item, null)
                    ?.findViewById<TextView>(android.R.id.text1)?.apply {
                        println(items[position].role)
                        text = items[position].role
                    } as View

}