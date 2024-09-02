package com.example.vojdip.admin.knowsad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.vojdip.R
import com.example.vojdip.admin.ActivityAdminKnows
import com.example.vojdip.databinding.ActivityAdminOdBinding
import com.example.vojdip.databinding.ActivityOdBinding
import com.example.vojdip.main.ActivityKnows

class ActivityAdminOD : AppCompatActivity() {
    lateinit var bindAdminOD : ActivityAdminOdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdminOD = ActivityAdminOdBinding.inflate(layoutInflater)
        setContentView(bindAdminOD.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ОД"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}