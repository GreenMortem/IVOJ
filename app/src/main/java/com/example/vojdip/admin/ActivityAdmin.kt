package com.example.vojdip.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.vojdip.R
import com.example.vojdip.databinding.ActivityAdminBinding
import com.example.vojdip.log.ActivityAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityAdmin : AppCompatActivity() {
    lateinit var bindAdmin : ActivityAdminBinding

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdmin = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(bindAdmin.root)

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        val ref = db.collection("VojatInfo").document(userID)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val name = it.data?.get("name")?.toString()
                val sername = it.data?.get("sername")?.toString()
                val phone = it.data?.get("phone")?.toString()
                val email = it.data?.get("email")?.toString()
                bindAdmin.tvNameProfAdmin.text = "Имя: $name"
                bindAdmin.tvSernameProfAdmin.text = "Фамилия: $sername"
                bindAdmin.tvPhoneProfAdmin.text = "Телефон: $phone"
                bindAdmin.tvEmailProfAdmin.text = "Почта: $email"
            }
        }.addOnFailureListener{
            Toast.makeText(this,it.toString(), Toast.LENGTH_SHORT).show()
        }
        ref.addSnapshotListener { snapshots, e->
            if (snapshots != null) {
                val name = snapshots.data?.get("name")?.toString()
                val sername = snapshots.data?.get("sername")?.toString()
                val phone = snapshots.data?.get("phone")?.toString()
                val email = snapshots.data?.get("email")?.toString()
                bindAdmin.tvNameProfAdmin.text = "Имя: $name"
                bindAdmin.tvSernameProfAdmin.text = "Фамилия: $sername"
                bindAdmin.tvPhoneProfAdmin.text = "Телефон: $phone"
                bindAdmin.tvEmailProfAdmin.text = "Почта: $email"
            }
        }

        bindAdmin.bnExitAdmin.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(this, ActivityAuth::class.java)
            startActivity(intent)
            this.finish()
        }

        bindAdmin.bnKnowsAdmin.setOnClickListener{
            val intent = Intent(this, ActivityAdminKnows::class.java)
            startActivity(intent)
            this.finish()
        }

        bindAdmin.bnChildAdmin.setOnClickListener {
            val intent = Intent(this, ActivityAdminChild::class.java)
            startActivity(intent)
        }
        bindAdmin.bnInfoAdmin.setOnClickListener {
            val intent = Intent(this, ActivityAdminVoj::class.java)
            startActivity(intent)
        }

    }

}