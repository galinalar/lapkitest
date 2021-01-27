package ru.kotlin.lapki

object DateSpinner {
    val dayArray = Array(31, {i->i+1})
    val monthArray = Array(12, {i->i+1})
    fun dateString (day: String, month: String, year: String): String{
        var d:String
        var m:String
        if (day.length == 1) {d = "0"+day} else d = day
        if (month.length == 1) {m = "0"+month} else m = month
        return year+"-"+m+"-"+d
    }
}