package com.example.vojdip.admin.knowsad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.admin.ActivityAdminKnows
import com.example.vojdip.databinding.ActivityAdminKtdBinding


class ActivityAdminKTD : AppCompatActivity() {
    lateinit var bindAdminKTD : ActivityAdminKtdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdminKTD = ActivityAdminKtdBinding.inflate(layoutInflater)
        setContentView(bindAdminKTD.root)
        bindAdminKTD.bnBackktd.setOnClickListener {
            val intent = Intent(this, ActivityAdminKnows::class.java)
            startActivity(intent)
        }
    }
}