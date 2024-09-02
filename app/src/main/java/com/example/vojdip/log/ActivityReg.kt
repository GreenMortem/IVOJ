package com.example.vojdip.log

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.vojdip.databinding.ActivityRegBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityReg : AppCompatActivity() {
    private lateinit var eName:String
    private lateinit var eSername:String
    private lateinit var eEmail:String
    private lateinit var ePhone:String
    private lateinit var rPhone:String
    private lateinit var rPassword:String

    private var db = Firebase.firestore

    private lateinit var bindReg: ActivityRegBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindReg = ActivityRegBinding.inflate(layoutInflater)
        setContentView(bindReg.root)

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        auth = Firebase.auth

        bindReg.bnAuthentication.setOnClickListener {
            val intent = Intent(this, ActivityAuth::class.java)
            startActivity(intent)
            this.finish()
        }
        bindReg.bnRegistre.setOnClickListener {
            rPhone = bindReg.etemailReg.text.toString().trim()
            rPassword = bindReg.etPasReg.text.toString().trim()

            bindReg.progressBar.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(rPhone, rPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Регистрация успешна!")
                        saveDate()
                        updateUI()
                    } else {
                        Log.w(TAG, "типтут", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Ошибка!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun saveDate() {
        eName = bindReg.etNameReg.text.toString().trim()
        eSername = bindReg.etSernameReg.text.toString().trim()
        ePhone = bindReg.etPhoneReg.text.toString().trim()
        eEmail = bindReg.etemailReg.text.toString().trim()

        val user = hashMapOf(
            "name" to eName,
            "sername" to eSername,
            "phone" to ePhone,
            "email" to eEmail
        )

        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("VojatInfo").document(userID).set(user)

        val ref = db.collection("Admin").document("Voj").collection("Num")
        val n = ref.count()
        n.get(AggregateSource.SERVER).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                // Count fetched successfully
                val snapshot = task.result
                val uID = hashMapOf("userID" to userID,)
                db.collection("Admin").document("Voj").collection("Num").document(snapshot.count.toString()).set(uID)
                Log.d(ContentValues.TAG, "true: ${snapshot.count}")

            } else {
                Log.d(ContentValues.TAG, "false ", task.getException())
            }
        }
    }

    private fun updateUI() {
        val intent = Intent(this, ActivityAuth::class.java)
        startActivity(intent)
        this.finish()
    }
}