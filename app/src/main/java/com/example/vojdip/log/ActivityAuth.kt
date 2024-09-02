package com.example.vojdip.log

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import com.example.vojdip.admin.ActivityAdmin
import com.example.vojdip.main.ActivityKnows
import com.example.vojdip.databinding.ActivityAuthBinding
import com.example.vojdip.vojat.ActivityKnowsVoj
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var aEmail:String
private lateinit var aPassword:String

class ActivityAuth : AppCompatActivity() {
    lateinit var bindAuth : ActivityAuthBinding
    private var db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAuth = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(bindAuth.root)

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        auth = Firebase.auth

        bindAuth.bnAuth.setOnClickListener {
            aEmail = bindAuth.etEmailAuth.text.toString().trim()
            aPassword = bindAuth.etPas.text.toString().trim()
            if (bindAuth.checkBoxAdmin.isChecked ) {
                loginAdmin(aEmail, aPassword)
            }else {

                auth.signInWithEmailAndPassword(aEmail, aPassword)
                    .addOnCompleteListener(this) { task ->
                        if (it != null) {
                            updateUI()
                        }

                    }

            }
        }
        bindAuth.bnReg.setOnClickListener {
            val intent = Intent(this, ActivityReg::class.java)
            startActivity(intent)
        }

    }

    private fun loginAdmin(aEmail: String, aPassword: String){
        auth.signInWithEmailAndPassword(aEmail, aPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userID = FirebaseAuth.getInstance().currentUser!!.uid
                    val adminBool = db.collection("Admin").document(userID)
                    adminBool.get().addOnSuccessListener { document ->
                        if (document != null) {
                            val bool = document.getBoolean("admin")
                            Log.d(ContentValues.TAG, "типтутзапуск1: ${bool}")
                            if (bool == true) {
                                val intent = Intent(this, ActivityAdmin::class.java)
                                startActivity(intent)
                                this.finish()
                            }else{
                                Toast.makeText(
                                    baseContext,
                                    "Вы не администратор!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                    }
                    adminBool.addSnapshotListener { snapshot, e ->
                        if (snapshot != null) {
                            val bool = snapshot.getBoolean("admin")
                            Log.d(ContentValues.TAG, "типтутзапуск1: ${bool}")
                            if (bool == true) {
                                val intent = Intent(this, ActivityAdmin::class.java)
                                startActivity(intent)
                                this.finish()
                            }else{
                                Toast.makeText(
                                    baseContext,
                                    "Вы не администратор!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(
                        baseContext,
                        "Ошибка!",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


    private fun updateUI() {
        val intent = Intent(this, ActivityKnows::class.java)
        startActivity(intent)
        this.finish()
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        val prov = currentUser.toString()

        Log.d(ContentValues.TAG, "типтутзапуск: ${prov}")
        if (currentUser != null) {
            val userID = FirebaseAuth.getInstance().currentUser!!.uid
            val adminBool = db.collection("Admin").document(userID)
            /*adminBool.get().addOnSuccessListener {
                if (it != null) {
                    val bool = it.getBoolean("admin")
                    Log.d(ContentValues.TAG, "типтутзапуск2: ${bool}")
                    if (bool == true) {
                        val intent = Intent(this, ActivityAdmin::class.java)
                        startActivity(intent)
                        this.finish()
                    }else{
                        updateUI()
                    }
                }
            }*/
            adminBool.addSnapshotListener {snapshot, e ->
                if (snapshot != null) {
                    val bool = snapshot.getBoolean("admin")
                    Log.d(ContentValues.TAG, "типтутзапуск2: ${bool}")
                    if (bool == true) {
                        val intent = Intent(this, ActivityAdmin::class.java)
                        startActivity(intent)
                        this.finish()
                    }else{
                        updateUI()
                    }
                }
            }
        }
    }
}