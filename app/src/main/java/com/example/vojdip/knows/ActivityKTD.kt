package com.example.vojdip.knows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.vojdip.databinding.ActivityKtdBinding


class ActivityKTD : AppCompatActivity() {
    lateinit var bindKTD : ActivityKtdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindKTD = ActivityKtdBinding.inflate(layoutInflater)
        setContentView(bindKTD.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "КТД"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}