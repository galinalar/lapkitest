package ru.kotlin.lapki

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_petac.*

import okhttp3.*
import org.json.JSONObject
import java.io.IOException
data class PetAcInfo(val IDPet: String, val Name: String, val BirthDate: String, val Discribe: String, val Type: String, val IdShelt: String, val Shelterr: String, val Photo: String, val IdRole: String, val Statu: String, val Sex: String, val Active: String, val Size: String, val Wool: String, val Hypo: String, val Dressir: String, val Dogs: String, val Animal: String, val Child: String, val Children: String, val Teens: String, val Ills: String, val Sounds: String, val Talkative: String, val Poroda: String)
