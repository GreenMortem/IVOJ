package com.example.vojdip.knows
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.games.Game
import com.example.vojdip.games.GameAdapter
import com.example.vojdip.databinding.ActivitySongsBinding
import com.example.vojdip.main.ActivityKnows
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivitySongs : AppCompatActivity(), GameAdapter.Listener{
    lateinit var bindSong : ActivitySongsBinding
    private var db = Firebase.firestore
    private  val adapter = GameAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        bindSong = ActivitySongsBinding.inflate(layoutInflater)
        setContentView(bindSong.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Песни"

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

    private fun init() = with(bindSong) {
        rvSong.layoutManager = LinearLayoutManager(this@ActivitySongs)
        rvSong.adapter = adapter


        val ref = db.collection("Knows").document("Songs").collection("song")
        //эт в офлайне
        ref.get()
            .addOnSuccessListener { documents ->
                adapter.clear()
                for (document in documents) {
                    val name = document.getString("name")
                    val rule = document.getString("text")
                    val game = Game(name, rule)
                    adapter.addGame(game)
                }
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error", e)
            }

        ref.addSnapshotListener { snapshots, e ->
            if (e != null) {
                Log.w("ActivityGames", "Ошибочка $e")
                return@addSnapshotListener
            }
            //эт в реальном времени
            if (snapshots != null && !snapshots.isEmpty) {
                adapter.clear()
                for (document in snapshots.documents) {
                    val name = document.getString("name")
                    val rule = document.getString("text")
                    val game = Game(name, rule)
                    adapter.addGame(game)
                }
            } else {
                Log.d("ActivityGames", "Нет данных")
            }
        }
    }

    override fun onClick(game: Game) {
    }
}