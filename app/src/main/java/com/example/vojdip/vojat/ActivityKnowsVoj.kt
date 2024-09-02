package com.example.vojdip.vojat

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.R
import com.example.vojdip.databinding.ActivityKnowsVojBinding
import com.example.vojdip.main.ActivityKnows
import com.example.vojdip.main.ActivityVojat

class ActivityKnowsVoj : AppCompatActivity() {
    lateinit var bindingKnowsVoj : ActivityKnowsVojBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingKnowsVoj = ActivityKnowsVojBinding.inflate(layoutInflater)
        setContentView(bindingKnowsVoj.root)
        supportActionBar?.title = "IVOJ"

        bindingKnowsVoj.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId){
                R.id.navknows -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeF, FragmentKnows.newInstance())
                        .commit()
                }
                R.id.navvoj ->{
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.placeF, FragmentVoj.newInstance())
                        .commit()
                }
            }
        }

    }
}