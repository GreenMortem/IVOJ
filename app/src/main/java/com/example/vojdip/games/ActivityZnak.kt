package com.example.vojdip.games

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.databinding.ActivityZnakBinding
import com.example.vojdip.knows.ActivityGame
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityZnak : AppCompatActivity(), GameAdapter.Listener {
    lateinit var bindZnak : ActivityZnakBinding
    private var db = Firebase.firestore
    private  val adapter = GameAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindZnak = ActivityZnakBinding.inflate(layoutInflater)
        setContentView(bindZnak.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Игры на знакомство"

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    //эт заполнение recyclewiew
    private fun init() = with(bindZnak) {
        rvZnak.layoutManager = LinearLayoutManager(this@ActivityZnak)
        rvZnak.adapter = adapter

        val ref = db.collection("Knows").document("Games").collection("Znak")

        ref.get()
            .addOnSuccessListener { documents ->
                adapter.clear()
                for (document in documents) {
                    val name = document.getString("name")
                    val rule = document.getString("rule")
                    val game = Game(name, rule)
                    adapter.addGame(game)
                }
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error", e)
            }
        ref.addSnapshotListener { snapshots, e ->
            if (snapshots != null && !snapshots.isEmpty) {
                adapter.clear()
                for (snapshot in snapshots.documents) {
                    val name = snapshot.getString("name")
                    val rule = snapshot.getString("rule")
                    val game = Game(name, rule)
                    adapter.addGame(game)
                }
            }
        }
    }

    override fun onClick(game: Game) {
    }
}