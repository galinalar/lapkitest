package ru.kotlin.lapki.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.kotlin.lapki.api.entities.ShelterType

class SpinTypeAdapter(val appcontext: Context, var items : List<ShelterType>): ArrayAdapter<ShelterType>(appcontext, android.R.layout.simple_spinner_item, items){


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
            LayoutInflater.from(appcontext).inflate(android.R.layout.simple_spinner_item, null)
                    ?.findViewById<TextView>(android.R.id.text1)?.apply {
                        println(items[1].type)
                        println(position)
                        text = items[position].type
                    } as View

}