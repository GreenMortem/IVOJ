package com.example.vojdip.vojat

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vojdip.databinding.DialogChildBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ActivityAddChild: AppCompatActivity() {
    lateinit var bindAddChild: DialogChildBinding
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAddChild = DialogChildBinding.inflate(layoutInflater)
        setContentView(bindAddChild.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ребенок"

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        val ind = intent.getStringExtra("key")
        if (ind == "Добавлен вручную!"){

        }else {
            val ref = db.collection("Reestr").document(ind.toString())
            ref.get()
                .addOnSuccessListener { document ->
                    if (document.exists()){
                        val index = document.getString("index")
                        Log.d(ContentValues.TAG, "Тим1 $index")
                        bindAddChild.etnumb.setText(document.getString("numb"))
                        bindAddChild.etfioChild.setText(document.getString("fioChild"))
                        bindAddChild.etsex.setText(document.getString("sex"))
                        bindAddChild.etdate.setText(document.getString("date"))
                        bindAddChild.etplaceLern.setText(document.getString("placeLern"))
                        bindAddChild.etclas.setText(document.getString("clas"))
                        bindAddChild.etkolect.setText(document.getString("kolect"))
                        bindAddChild.etputevka.setText(document.getString("putevka"))
                        bindAddChild.etfioParent.setText(document.getString("fioParent"))
                        bindAddChild.etplaceJob.setText(document.getString("placeJob"))
                        bindAddChild.etphone.setText(document.getString("phone"))
                        bindAddChild.etadres.setText(document.getString("adres"))
                        bindAddChild.etOsob.setText(document.getString("osob"))
                    }
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "типошибка", e)
                }
            ref
                .addSnapshotListener { snapshots, e  ->
                    if (snapshots != null){
                        val index = snapshots.getString("index")
                        Log.d(ContentValues.TAG, "Тим1 $index")
                        bindAddChild.etnumb.setText(snapshots.getString("numb"))
                        bindAddChild.etfioChild.setText(snapshots.getString("fioChild"))
                        bindAddChild.etsex.setText(snapshots.getString("sex"))
                        bindAddChild.etdate.setText(snapshots.getString("date"))
                        bindAddChild.etplaceLern.setText(snapshots.getString("placeLern"))
                        bindAddChild.etclas.setText(snapshots.getString("clas"))
                        bindAddChild.etkolect.setText(snapshots.getString("kolect"))
                        bindAddChild.etputevka.setText(snapshots.getString("putevka"))
                        bindAddChild.etfioParent.setText(snapshots.getString("fioParent"))
                        bindAddChild.etplaceJob.setText(snapshots.getString("placeJob"))
                        bindAddChild.etphone.setText(snapshots.getString("phone"))
                        bindAddChild.etadres.setText(snapshots.getString("adres"))
                        bindAddChild.etOsob.setText(snapshots.getString("osob"))
                    }
                }
        }



        bindAddChild.bnOk.setOnClickListener {
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = db.collection("VojatInfo").document(userID)
            Log.d(ContentValues.TAG, "Тим2 ${ind.toString()}")

            ref.get().addOnSuccessListener {document ->
                if (document.exists()) {
                    val otrid = document.getString("otrid")
                    Log.d(ContentValues.TAG, "Тим3 $otrid")
                    if (otrid.isNullOrEmpty()) {
                        Toast.makeText(this, "Вам не предоставлен отряд!", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(ContentValues.TAG, "Тимэткак ${otrid.isNullOrEmpty()}")
                    } else {
                        if (ind != "Добавлен вручную!"){
                            val refed = db.collection("Reestr").document(ind.toString())
                            refed.get().addOnSuccessListener {document->
                                if (document.exists()) {
                                    val otridCH = document.getString("otrid")
                                    if (!otridCH.isNullOrEmpty()) {
                                        showDialog(ind, otrid)
                                        Log.d(ContentValues.TAG, "Тимэткака вож${otrid.isNullOrEmpty()} ребенок${otridCH.isNullOrEmpty() }")
                                    } else {
                                        AddChild(ind, otrid)
                                        Log.d(ContentValues.TAG, "Тимэткак вож${otrid.isNullOrEmpty()} ребенок${otridCH.isNullOrEmpty() }")
                                    }
                                }

                            }.addOnFailureListener {
                                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            AddChild(ind,otrid)
                        }
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }
            ref.addSnapshotListener() { snapshots, e ->
                if (snapshots != null) {
                    val otrid = snapshots.getString("otrid")
                    Log.d(ContentValues.TAG, "Тим3 $otrid")
                    if (otrid.isNullOrEmpty()) {
                        Toast.makeText(this, "Вам не предоставлен отряд!", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(ContentValues.TAG, "Тимэткак ${otrid.isNullOrEmpty()}")
                    } else {
                        val refed = db.collection("Reestr").document(ind.toString())
                        refed.addSnapshotListener() { snapshots, e->
                            if (snapshots != null) {
                                val otridCH = snapshots.getString("otrid")
                                if (!otridCH.isNullOrEmpty()) {
                                    showDialog(ind, otrid)
                                    Log.d(ContentValues.TAG, "Тимэткака вож${otrid.isNullOrEmpty()} ребенок${otridCH.isNullOrEmpty() }")
                                } else {
                                    AddChild(ind, otrid)
                                    Log.d(ContentValues.TAG, "Тимэткак вож${otrid.isNullOrEmpty()} ребенок${otridCH.isNullOrEmpty() }")
                                }
                            }

                        }
                    }
                }
            }

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }

    private fun showDialog(ind: String?, otrid: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Вы уверены?")
        .setMessage("Этот ребенок уже зарегестрирован в другом отряде! Все равно хотите его забрать? Возможны ошибки!")
        .setPositiveButton("Продолжить") { dialog, _ ->
            AddChild(ind, otrid)
            finish()
        }
        .setNegativeButton("Отменить") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
    private fun AddChild(ind: String?, otrid: String?) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        Log.d(ContentValues.TAG, "Тимэткак зашел?")
        val r = db.collection("VojatInfo").document(userID).collection("Children")

        val Child = hashMapOf(
            "index" to ind,
            "numb" to bindAddChild.etnumb.text.toString().trim(),
            "fioChild" to bindAddChild.etfioChild.text.toString().trim(),
            "sex" to bindAddChild.etsex.text.toString().trim(),
            "date" to bindAddChild.etdate.text.toString().trim(),
            "placeLern" to bindAddChild.etplaceLern.text.toString().trim(),
            "clas" to bindAddChild.etclas.text.toString().trim(),
            "kolect" to bindAddChild.etkolect.text.toString().trim(),
            "putevka" to bindAddChild.etputevka.text.toString().trim(),
            "fioParent" to bindAddChild.etfioParent.text.toString().trim(),
            "placeJob" to bindAddChild.etplaceJob.text.toString().trim(),
            "phone" to bindAddChild.etphone.text.toString().trim(),
            "adres" to bindAddChild.etadres.text.toString().trim(),
            "osob" to bindAddChild.etOsob.text.toString().trim(),
            "medpokaz" to bindAddChild.etmedpokaz.text.toString().trim(),
            "otrid" to otrid
        )
        Log.d(ContentValues.TAG, "Тимэткак ${Child}")
        db.collection("VojatInfo").document(userID)
            .collection("Children")
            .document(bindAddChild.etfioChild.text.toString().trim()).set(Child)

        db.collection("Reestr").document(ind.toString()).set(Child)
        finish()

    }
}