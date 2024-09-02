package com.example.vojdip.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.admin.knowsad.ActivityAdminGames
import com.example.vojdip.admin.knowsad.ActivityAdminKTD
import com.example.vojdip.admin.knowsad.ActivityAdminLights
import com.example.vojdip.admin.knowsad.ActivityAdminOD
import com.example.vojdip.admin.knowsad.ActivityAdminSongs
import com.example.vojdip.databinding.ActivityAdminKnowsBinding


class ActivityAdminKnows : AppCompatActivity() {
    lateinit var bindAdKnows : ActivityAdminKnowsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdKnows = ActivityAdminKnowsBinding.inflate(layoutInflater)
        setContentView(bindAdKnows.root)

        bindAdKnows.bnAdmin.setOnClickListener {
            val intent = Intent(this, ActivityAdmin::class.java)
            startActivity(intent)
            this.finish()
        }
        bindAdKnows.bnKTDAD.setOnClickListener {
            val intent = Intent(this, ActivityAdminKTD::class.java)
            startActivity(intent)
        }
        bindAdKnows.bnLightAD.setOnClickListener {
            val intent = Intent(this, ActivityAdminLights::class.java)
            startActivity(intent)
        }
        bindAdKnows.bnODAD.setOnClickListener {
            val intent = Intent(this, ActivityAdminOD::class.java)
            startActivity(intent)
        }
        bindAdKnows.bnSongsAD.setOnClickListener {
            val intent = Intent(this, ActivityAdminSongs::class.java)
            startActivity(intent)
        }
        bindAdKnows.bnGamesAD.setOnClickListener {
            val intent = Intent(this, ActivityAdminGames::class.java)
            startActivity(intent)
        }
    }
}