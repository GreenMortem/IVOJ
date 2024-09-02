package com.example.vojdip.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.databinding.ActivityVojatBinding
import com.example.vojdip.log.ActivityAuth
import com.example.vojdip.vojat.ActivityInfo
import com.example.vojdip.vojat.ActivityReestr
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityVojat : AppCompatActivity() {
    lateinit var bindVoj : ActivityVojatBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindVoj = ActivityVojatBinding.inflate(layoutInflater)
        setContentView(bindVoj.root)
        supportActionBar?.title = "Страница вожатого"

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)

        ref.get().addOnSuccessListener { document ->
            if (document != null) {
                val name = document.data?.get("name")?.toString()
                val sername = document.data?.get("sername")?.toString()
                val phone = document.data?.get("phone")?.toString()
                val email = document.data?.get("email")?.toString()
                val otrid = document.data?.get("otrid")?.toString()
                bindVoj.tvNameProf.text = "Имя: $name"
                bindVoj.tvSernameProf.text = "Фамилия: $sername"
                bindVoj.tvPhoneProf.text = "Телефон: $phone"
                bindVoj.tvEmailProf.text = "Почта: $email"
                bindVoj.tvOtrid.text = "Отряд: $otrid"
            }
        }
        ref.addSnapshotListener {snapshot, e ->
            if (snapshot != null) {
                val name = snapshot.data?.get("name")?.toString()
                val sername = snapshot.data?.get("sername")?.toString()
                val phone = snapshot.data?.get("phone")?.toString()
                val email = snapshot.data?.get("email")?.toString()
                val otrid = snapshot.data?.get("otrid")?.toString()
                bindVoj.tvNameProf.text = "Имя: $name"
                bindVoj.tvSernameProf.text = "Фамилия: $sername"
                bindVoj.tvPhoneProf.text = "Телефон: $phone"
                bindVoj.tvEmailProf.text = "Почта: $email"
                bindVoj.tvOtrid.text = "Отряд: $otrid"
            }
        }

        bindVoj.bnPlan.setOnClickListener {
            val intent = Intent(this, ActivityInfo::class.java)
            startActivity(intent)
        }

        bindVoj.bnInfo.setOnClickListener{
            val intent = Intent(this, ActivityReestr::class.java)
            startActivity(intent)
        }
        
        bindVoj.bnKnows.setOnClickListener {
            val intent = Intent(this, ActivityKnows::class.java)
            startActivity(intent)
            this.finish()
        }
        
        bindVoj.bnExit.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this, ActivityAuth::class.java)
            startActivity(intent)
            this.finish()
        }
    }


}