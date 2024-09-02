package com.example.vojdip.admin.knowsad

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.R
import com.example.vojdip.admin.ActivityAdminKnows
import com.example.vojdip.databinding.ActivityAdminLightsBinding
import com.example.vojdip.databinding.ActivityLightsBinding
import com.example.vojdip.games.Game
import com.example.vojdip.games.GameAdapter
import com.example.vojdip.main.ActivityKnows
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityAdminLights : AppCompatActivity(), GameAdapter.Listener {
    lateinit var bindAdminLight : ActivityAdminLightsBinding
    private var db = Firebase.firestore
    private  val adapter = GameAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        bindAdminLight = ActivityAdminLightsBinding.inflate(layoutInflater)
        setContentView(bindAdminLight.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Огоньки"

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    private fun init() = with(bindAdminLight){
        rvLight.layoutManager = LinearLayoutManager(this@ActivityAdminLights)
        rvLight.adapter = adapter
        val ref = db.collection("Knows").document("Lighter").collection("light")
        val n = ref.count()
        n.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                for (i in 1..snapshot.count) {
                    val refed = db.collection("Knows").document("Lighter").collection("light").document(i.toString())
                    refed.get().addOnSuccessListener {
                        if (i != null) {
                            val name = it.data?.get("name")?.toString()
                            val idea = it.data?.get("idea")?.toString()
                            val game = Game(name,idea)
                            adapter.addGame(game)
                            Log.d(ContentValues.TAG, "типтут: ${name} ${idea}")
                        }
                    }.addOnFailureListener {
                        // Toast.makeText(this, "faild!", Toast.LENGTH_SHORT).show()
                    }
                }

                Log.d(ContentValues.TAG, "true: ${snapshot.count}")
            } else {
                Log.d(ContentValues.TAG, "false ", task.getException())
            }
        }
    }

    override fun onClick(game: Game) {
    }
}