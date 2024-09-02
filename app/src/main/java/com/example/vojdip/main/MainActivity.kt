package com.example.vojdip.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.databinding.ActivityMainBinding
import com.example.vojdip.log.ActivityAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


class MainActivity : AppCompatActivity() {
    lateinit var bindMain : ActivityMainBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindMain.root)

        db = FirebaseFirestore.getInstance()

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings


        bindMain.ivLogo.alpha = 0f
        bindMain.ivLogo.animate().setDuration(2000).alpha(1f).withEndAction{
            val intent = Intent(this, ActivityAuth::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}
