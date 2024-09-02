package com.example.vojdip.knows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.vojdip.databinding.ActivityGameBinding
import com.example.vojdip.games.ActivityAt
import com.example.vojdip.games.ActivityLid
import com.example.vojdip.games.ActivityPod
import com.example.vojdip.games.ActivitySploch
import com.example.vojdip.games.ActivityZal
import com.example.vojdip.games.ActivityZnak
import com.example.vojdip.main.ActivityKnows

class ActivityGame : AppCompatActivity() {
    lateinit var bindGame: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindGame = ActivityGameBinding.inflate(layoutInflater)
        setContentView(bindGame.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Игры"

        bindGame.bnAt.setOnClickListener {
            val intent = Intent(this, ActivityAt::class.java)
            startActivity(intent)
        }

        bindGame.bnLid.setOnClickListener {
            val intent = Intent(this, ActivityLid::class.java)
            startActivity(intent)
        }

        bindGame.bnPod.setOnClickListener {
            val intent = Intent(this, ActivityPod::class.java)
            startActivity(intent)
        }

        bindGame.bnSploch.setOnClickListener {
            val intent = Intent(this, ActivitySploch::class.java)
            startActivity(intent)
        }

        bindGame.bnZal.setOnClickListener {
            val intent = Intent(this, ActivityZal::class.java)
            startActivity(intent)
        }

        bindGame.bnZnak.setOnClickListener {
            val intent = Intent(this, ActivityZnak::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}