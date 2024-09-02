package com.example.vojdip.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.R
import com.example.vojdip.knows.ActivityGame
import com.example.vojdip.databinding.ActivityKnowsBinding
import com.example.vojdip.knows.ActivityKTD
import com.example.vojdip.knows.ActivityLights
import com.example.vojdip.knows.ActivityOD
import com.example.vojdip.knows.ActivitySongs

class ActivityKnows : AppCompatActivity() {
    lateinit var bindKnows: ActivityKnowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindKnows = ActivityKnowsBinding.inflate(layoutInflater)
        setContentView(bindKnows.root)
        supportActionBar?.title = "Общедоступная информация"

        bindKnows.buttonvoj.setOnClickListener {
            val intent = Intent(this, ActivityVojat::class.java)
            startActivity(intent)
        }
        bindKnows.bnKTD.setOnClickListener {
            val intent = Intent(this, ActivityKTD::class.java)
            startActivity(intent)
        }
        bindKnows.bnLight.setOnClickListener {
            val intent = Intent(this, ActivityLights::class.java)
            startActivity(intent)
        }
        bindKnows.bnOD.setOnClickListener {
            val intent = Intent(this, ActivityOD::class.java)
            startActivity(intent)
        }
        bindKnows.bnSongs.setOnClickListener {
            val intent = Intent(this, ActivitySongs::class.java)
            startActivity(intent)
        }
        bindKnows.bnGames.setOnClickListener {
            val intent = Intent(this, ActivityGame::class.java)
            startActivity(intent)
        }
    }
}