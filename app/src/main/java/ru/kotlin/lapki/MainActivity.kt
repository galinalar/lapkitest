package ru.kotlin.lapki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ac
import kotlinx.android.synthetic.main.activity_main.shelter
import ru.kotlin.lapki.api.entities.UserID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Picasso.with(this).load("https://rozetked.me/images/uploads/dwoilp3BVjlE.jpg").fit().centerCrop().into(imageView2)
        Picasso.get().load("https://rozetked.me/images/uploads/dwoilp3BVjlE.jpg").into(imageView2);

        activity_main_auto.setOnClickListener{
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }
        activity_main_guest.setOnClickListener{
            startActivity(Intent(this, StartActivity::class.java))
        }
        activity_main_registration.setOnClickListener{
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
        shelter.setOnClickListener{
            startActivity(Intent(this, ShelterAc::class.java).apply {
                putExtra("user", UserID(0, 0) as Parcelable)
            })
        }
        pet.setOnClickListener{
            startActivity(Intent(this, PetAc::class.java))
        }
       ac.setOnClickListener{
            startActivity(Intent(this, Accaunt::class.java))
        }
        addsh.setOnClickListener{
            startActivity(Intent(this, ModifyShelter::class.java))
        }
        addpet.setOnClickListener{
            startActivity(Intent(this, ModifyPet::class.java))
        }

    }
}
