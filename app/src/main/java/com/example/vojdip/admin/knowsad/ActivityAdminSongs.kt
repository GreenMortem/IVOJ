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
import com.example.vojdip.databinding.ActivityAdminSongsBinding
import com.example.vojdip.databinding.ActivitySongsBinding
import com.example.vojdip.games.Game
import com.example.vojdip.games.GameAdapter
import com.example.vojdip.main.ActivityKnows
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityAdminSongs : AppCompatActivity() , GameAdapter.Listener{
    lateinit var bindAdminSong : ActivityAdminSongsBinding
    private var db = Firebase.firestore
    private  val adapter = GameAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        bindAdminSong = ActivityAdminSongsBinding.inflate(layoutInflater)
        setContentView(bindAdminSong.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Песни"
        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    private fun init() = with(bindAdminSong){
        rvSong.layoutManager = LinearLayoutManager(this@ActivityAdminSongs)
        rvSong.adapter = adapter
        val ref = db.collection("Knows").document("Songs").collection("song")
        val n = ref.count()
        n.get(AggregateSource.SERVER).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                for (i in 1..snapshot.count) {
                    val refed = db.collection("Knows").document("Songs").collection("song").document(i.toString())
                    refed.get().addOnSuccessListener {
                        if (i != null) {
                            val name = it.data?.get("name")?.toString()
                            val text = it.data?.get("text")?.toString()
                            val game = Game(name,text)
                            adapter.addGame(game)
                            Log.d(ContentValues.TAG, "типтут: ${name} ${text}")
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