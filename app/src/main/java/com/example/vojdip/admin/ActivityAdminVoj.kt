package com.example.vojdip.admin

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.R
import com.example.vojdip.databinding.ActivityAdminVojBinding
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityAdminVoj : AppCompatActivity(), VojAdapter.List {

    private var db = Firebase.firestore
    private  val adapter = VojAdapter(this)

    lateinit var bindAdminChild: ActivityAdminVojBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdminChild = ActivityAdminVojBinding.inflate(layoutInflater)
        setContentView(bindAdminChild.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Вожатые"

        init()

        bindAdminChild.searchView.setIconifiedByDefault(false)
        bindAdminChild.searchView.queryHint = "Поиск"
        bindAdminChild.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
    fun searchList(text: String) {

        val searchList = ArrayList<Voj>()
        for (dataClass in adapter.searchL) {
            if (dataClass.sername?.lowercase()?.contains(text.lowercase()) == true ||
                dataClass.phone?.lowercase()?.contains(text.lowercase()) == true
            ) {
                searchList.add(dataClass)
                Log.d(ContentValues.TAG, "типтутdataClass: ${dataClass}")

            }

        }
        adapter.searchDataList(searchList)
    }

    private fun init() = with(bindAdminChild) {
        rvAdVoj.layoutManager = LinearLayoutManager(this@ActivityAdminVoj)
        rvAdVoj.adapter = adapter
        val n = db.collection("Admin").document("Voj").collection("Num")

        n.get(Source.CACHE).addOnSuccessListener { documents ->
            adapter.clear()
            for (document in documents) {
                Log.d(ContentValues.TAG, "яшма: ${document.id}")
                if (document.id != "0") {
                    val refed =
                        db.collection("Admin").document("Voj").collection("Num")
                            .document(document.id)
                    refed.get(Source.CACHE).addOnSuccessListener { documented ->
                        if (documented != null) {
                            val UserID = documented.getString("userID")
                            val ref = db.collection("VojatInfo").document(UserID.toString())
                            Log.d(ContentValues.TAG, "яшма2: ${UserID.toString()}")
                            ref.get(Source.CACHE).addOnSuccessListener { documenting ->
                                if (documenting != null) {
                                    val name = documenting.getString("name")
                                    val sername = documenting.getString("sername")
                                    val phone = documenting.getString("phone")
                                    val email = documenting.getString("email")
                                    val otrid = documenting.getString("otrid")
                                    val voj =
                                        Voj(sername, name, phone, email, otrid, UserID.toString())
                                    adapter.addVoj(voj)
                                    Log.d(ContentValues.TAG, "яшма3: ${voj}")
                                }
                            }.addOnFailureListener {

                            }
                        }
                    }.addOnFailureListener {

                    }
                }
            }
        }

        n.get(Source.SERVER).addOnSuccessListener { documents ->
            adapter.clear()
            for (document in documents) {
                Log.d(ContentValues.TAG, "яшма: ${document.id}")
                if (document.id != "0") {
                    val refed =
                        db.collection("Admin").document("Voj").collection("Num")
                            .document(document.id)
                    refed.get(Source.SERVER).addOnSuccessListener { documented ->
                        if (documented != null) {
                            val UserID = documented.getString("userID")
                            val ref = db.collection("VojatInfo").document(UserID.toString())
                            Log.d(ContentValues.TAG, "яшма2: ${UserID.toString()}")
                            ref.get(Source.SERVER).addOnSuccessListener { documenting ->
                                if (documenting != null) {
                                    val name = documenting.getString("name")
                                    val sername = documenting.getString("sername")
                                    val phone = documenting.getString("phone")
                                    val email = documenting.getString("email")
                                    val otrid = documenting.getString("otrid")
                                    val voj =
                                        Voj(sername, name, phone, email, otrid, UserID.toString())
                                    adapter.addVoj(voj)
                                    Log.d(ContentValues.TAG, "яшма3: ${voj}")
                                }
                            }.addOnFailureListener {

                            }
                        }
                    }.addOnFailureListener {

                    }
                }
            }
        }
    }


    override fun onClick(voj: Voj) {

    }
}