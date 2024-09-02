package com.example.vojdip.knows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.vojdip.databinding.ActivityOdBinding
import com.example.vojdip.main.ActivityKnows

class ActivityOD : AppCompatActivity() {
    lateinit var bindOD : ActivityOdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindOD = ActivityOdBinding.inflate(layoutInflater)
        setContentView(bindOD.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "ОД"

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}