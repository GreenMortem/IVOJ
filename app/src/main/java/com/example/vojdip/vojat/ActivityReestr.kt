package com.example.vojdip.vojat

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.R
import com.example.vojdip.admin.Reestr
import com.example.vojdip.admin.ReestrAdapter
import com.example.vojdip.admin.VojReestr
import com.example.vojdip.admin.VojReestrAdapter
import com.example.vojdip.databinding.ActivityReestrBinding
import com.example.vojdip.main.ActivityVojat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityReestr : AppCompatActivity(), VojReestrAdapter.List {

    private var db = Firebase.firestore
    private  val adapter = VojReestrAdapter(this, this)
    lateinit var bindReestr: ActivityReestrBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindReestr = ActivityReestrBinding.inflate(layoutInflater)
        setContentView(bindReestr.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Реестр"

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        init()


        bindReestr.searchView.setIconifiedByDefault(false)
        bindReestr.searchView.queryHint = "Поиск"
        bindReestr.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

    fun searchList(text: String){

        val searchList = ArrayList<VojReestr>()
        for (dataClass in adapter.searchL){
            if (dataClass.fioChild?.lowercase()?.contains(text.lowercase()) == true ||
                dataClass.putevka?.lowercase()?.contains(text.lowercase()) == true){
                searchList.add(dataClass)
                Log.d(ContentValues.TAG, "типтутdataClass: ${dataClass}")
            }
        }
        adapter.searchDataList(searchList)
    }

    private fun init() = with(bindReestr) {
        rvRCh.layoutManager = LinearLayoutManager(this@ActivityReestr)
        rvRCh.adapter = adapter

        val ref = db.collection("Reestr")

        ref.get()
            .addOnSuccessListener { documents ->
                adapter.clear()
                for (document in documents) {
                    val index = document.getString("index")
                    val numb = document.getString("numb")
                    val fioChild = document.getString("fioChild")
                    val sex = document.getString("sex")
                    val date = document.getString("date")
                    val placeLern = document.getString("placeLern")
                    val clas = document.getString("clas")
                    val kolect = document.getString("kolect")
                    val putevka = document.getString("putevka")
                    val fioParent = document.getString("fioParent")
                    val placeJob = document.getString("placeJob")
                    val phone = document.getString("phone")
                    val adres = document.getString("adres")
                    val osob = document.getString("osob")
                    val medpokaz = document.getString("medpokaz")
                    val otrid = document.getString("otrid")
                    val reestr = VojReestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob, medpokaz, otrid)
                    adapter.addVojReestr(reestr)
                }
            }.addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error", e)
            }
        ref.addSnapshotListener { snapshots, e ->
            adapter.clear()
            if (snapshots != null && !snapshots.isEmpty) {
                for (snapshot in snapshots.documents) {
                    val index = snapshot.getString("index")
                    val numb = snapshot.getString("numb")
                    val fioChild = snapshot.getString("fioChild")
                    val sex = snapshot.getString("sex")
                    val date = snapshot.getString("date")
                    val placeLern = snapshot.getString("placeLern")
                    val clas = snapshot.getString("clas")
                    val kolect = snapshot.getString("kolect")
                    val putevka = snapshot.getString("putevka")
                    val fioParent = snapshot.getString("fioParent")
                    val placeJob = snapshot.getString("placeJob")
                    val phone = snapshot.getString("phone")
                    val adres = snapshot.getString("adres")
                    val osob = snapshot.getString("osob")
                    val medpokaz = snapshot.getString("medpokaz")
                    val otrid = snapshot.getString("otrid")
                    val reestr = VojReestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob, medpokaz, otrid)
                    adapter.addVojReestr(reestr)
                }
            }
        }
    }

    override fun onClick(vojreestr: VojReestr) {
    }
}