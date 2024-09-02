package com.example.vojdip.admin.knowsad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.vojdip.admin.ActivityAdminKnows
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminAt
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminLid
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminPod
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminSploch
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminZal
import com.example.vojdip.admin.knowsad.gamesad.ActivityAdminZnak
import com.example.vojdip.databinding.ActivityAdminGamesBinding


class ActivityAdminGames : AppCompatActivity() {
    lateinit var bindAdminGame: ActivityAdminGamesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdminGame = ActivityAdminGamesBinding.inflate(layoutInflater)
        setContentView(bindAdminGame.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Игры"

        bindAdminGame.bnAt.setOnClickListener {
            val intent = Intent(this, ActivityAdminAt::class.java)
            startActivity(intent)
        }
        bindAdminGame.bnLid.setOnClickListener {
            val intent = Intent(this, ActivityAdminLid::class.java)
            startActivity(intent)
        }
        bindAdminGame.bnPod.setOnClickListener {
            val intent = Intent(this, ActivityAdminPod::class.java)
            startActivity(intent)
        }
        bindAdminGame.bnSploch.setOnClickListener {
            val intent = Intent(this, ActivityAdminSploch::class.java)
            startActivity(intent)
        }
        bindAdminGame.bnZal.setOnClickListener {
            val intent = Intent(this, ActivityAdminZal::class.java)
            startActivity(intent)
        }
        bindAdminGame.bnZnak.setOnClickListener {
            val intent = Intent(this, ActivityAdminZnak::class.java)
            startActivity(intent)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}