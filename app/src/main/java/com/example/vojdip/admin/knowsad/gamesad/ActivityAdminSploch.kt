package com.example.vojdip.admin.knowsad.gamesad

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.R
import com.example.vojdip.admin.knowsad.ActivityAdminGames
import com.example.vojdip.databinding.ActivityAdminSplochBinding
import com.example.vojdip.databinding.ActivitySplochBinding
import com.example.vojdip.games.Game
import com.example.vojdip.games.GameAdapter
import com.example.vojdip.knows.ActivityGame
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityAdminSploch : AppCompatActivity(), GameAdapter.Listener {
    lateinit var bindAdminSploch : ActivityAdminSplochBinding
    private var db = Firebase.firestore
    private  val adapter = GameAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        bindAdminSploch = ActivityAdminSplochBinding.inflate(layoutInflater)
        setContentView(bindAdminSploch.root)
        init()
        bindAdminSploch.bnBackSploch.setOnClickListener {
            val intent = Intent(this, ActivityAdminGames::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    private fun init() = with(bindAdminSploch){
        rvSploch.layoutManager = LinearLayoutManager(this@ActivityAdminSploch)
        rvSploch.adapter = adapter
        val ref = db.collection("Knows").document("Games").collection("Sploch")
        val n = ref.count()
        n.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                for (i in 1..snapshot.count) {
                    val refed = db.collection("Knows").document("Games").collection("Sploch").document(i.toString())
                    refed.get().addOnSuccessListener {
                        if (i != null) {
                            val name = it.data?.get("name")?.toString()
                            val rule = it.data?.get("rule")?.toString()
                            val game = Game(name,rule)
                            adapter.addGame(game)
                            Log.d(ContentValues.TAG, "типтут: ${name} ${rule}")
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