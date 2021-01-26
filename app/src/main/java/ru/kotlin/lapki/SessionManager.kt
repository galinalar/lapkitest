package ru.kotlin.lapki

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson

class SessionManager(
        val context: Context
) {
    val pref: SharedPreferences
    val editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object{
        val PREF_NAME: String = "lapki"
        val IS_LOGIN: String = "isLoggedIn"
        val KEY_SESSION_ID:String = "0"
        val KEY_ID: String = "0"
        val KEY_ROLE: String = "1"
        val KEY_START: String = "shelter"
        val KEY_IDSHELTER: String = "-1"
        val KEY_IDPET: String = "3"
        val KEY_MOD: String = "change"
        val KEY_TYPE_OBJECT: String = "shelter"
        val KEY_REQUEST: String = "vol"
        val KEY_REQUEST_START: String = "req"
        val KEY_ID_FS: String = ""
        val KEY_CHANGE_ROLE: String = "4"
        val KEY_CHANGE_SHELTER: String = "5"
        val KEY_CHANGE_IDSHELTER: String = "6"
        val KEY_CHANGE_IDROLE: String = "-3"
    }
    fun CreateSession(id: Long){
        editor.putBoolean(IS_LOGIN, true)
        editor.putLong(KEY_SESSION_ID, id)
        editor.commit()
    }

    fun checkLogin()
    {
        if (!this.isLoggedIn())
        {
            var i: Intent = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
    fun getSessionrDetails(): String= pref.getLong(KEY_SESSION_ID,0).toString()

    fun LogoutUser()
    {
        editor.clear()
        editor.commit()
        var i: Intent = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
    fun CreateLoginSession(id: String, role: String){
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_ID, id)
        editor.putString(KEY_ROLE, role)
        editor.commit()
    }
    fun StartP (g: String){
        editor.putString(KEY_START, g)
        editor.commit()
    }
    fun Shelter (g: String){
        editor.putString(KEY_IDSHELTER, g)
        editor.commit()
    }
    fun Pet (g: String){
        editor.putString(KEY_IDPET, g)
        editor.commit()
    }
    fun Mod (g: String){
        editor.putString(KEY_MOD, g)
        editor.commit()
    }
    fun TYPE_OBJ (g: String){
        editor.putString(KEY_TYPE_OBJECT, g)
        editor.commit()
    }
    fun Requests (g: String, o: String?){
        editor.putString(KEY_REQUEST, g)
        editor.putString(KEY_REQUEST_START, o)
        editor.commit()
    }
    fun CHPET (id:String, g: String, o: String, ir: String, ish:String){
        editor.putString(KEY_IDPET, id)
        editor.putString(KEY_CHANGE_ROLE, g)
        editor.putString(KEY_CHANGE_SHELTER, o)
        editor.putString(KEY_CHANGE_IDROLE, ir)
        editor.putString(KEY_CHANGE_IDSHELTER, ish)
        editor.commit()
    }

    fun getUserDetails(): HashMap<String,String>
    {
        var user: Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_ID, pref.getString(KEY_ID,null).toString())
        (user as HashMap).put(KEY_ROLE, pref.getString(KEY_ROLE,null).toString())
        return user
    }
    fun getS(): String{
        return pref.getString(KEY_START,null).toString()
    }
    fun getShelter(): String= pref.getString(KEY_IDSHELTER,null).toString()
    fun getPet(): String= pref.getString(KEY_IDPET,null).toString()
    fun getMod(): String= pref.getString(KEY_MOD,null).toString()
    fun getObject(): String= pref.getString(KEY_TYPE_OBJECT,null).toString()
    fun getReq(): HashMap<String,String>
    {
        var req: Map<String,String> = HashMap<String,String>()
        (req as HashMap).put(KEY_REQUEST, pref.getString(KEY_REQUEST,null).toString())
        (req as HashMap).put(KEY_REQUEST_START, pref.getString(KEY_REQUEST_START,null).toString())
        return req
    }
    fun getChPet(): HashMap<String,String>
    {
        var ch: Map<String,String> = HashMap<String,String>()
        (ch as HashMap).put(KEY_IDPET, pref.getString(KEY_IDPET,null).toString())
        (ch as HashMap).put(KEY_CHANGE_ROLE, pref.getString(KEY_CHANGE_ROLE,null).toString())
        (ch as HashMap).put(KEY_CHANGE_SHELTER, pref.getString(KEY_CHANGE_SHELTER,null).toString())
        (ch as HashMap).put(KEY_CHANGE_IDROLE, pref.getString(KEY_CHANGE_IDROLE,null).toString())
        (ch as HashMap).put(KEY_CHANGE_IDSHELTER, pref.getString(KEY_CHANGE_IDSHELTER,null).toString())
        return ch
    }


    fun isLoggedIn():Boolean=pref.getBoolean(IS_LOGIN,false)
}