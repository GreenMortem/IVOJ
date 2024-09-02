package com.example.vojdip.vojat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vojdip.databinding.ActivityInfoBinding
import com.example.vojdip.main.ActivityVojat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.ContentValues
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vojdip.R
import com.example.vojdip.admin.Reestr
import com.example.vojdip.admin.ReestrAdapter
import com.example.vojdip.admin.VojReestr
import com.example.vojdip.games.Game
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Source
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

import android.net.Uri

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import java.io.OutputStream


class ActivityInfo : AppCompatActivity(), ReestrAdapter.List {

    lateinit var bindInfo: ActivityInfoBinding
    private var db = Firebase.firestore
    private val adapter = ReestrAdapter(this)

    private lateinit var createDocumentLauncherFire: ActivityResultLauncher<Intent>
    private lateinit var createDocumentLauncherMed: ActivityResultLauncher<Intent>
    private lateinit var createDocumentLauncherReg: ActivityResultLauncher<Intent>
    //private lateinit var createDocumentLauncherRoj: ActivityResultLauncher<Intent>
    private val REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindInfo = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(bindInfo.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Отряд"

        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        init()

        bindInfo.searchViewChildVoj.setIconifiedByDefault(false)
        bindInfo.searchViewChildVoj.queryHint = "Поиск"
        bindInfo.searchViewChildVoj.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }

        })

        bindInfo.bnReestred.setOnClickListener {
            val intent = Intent(this, ActivityReestr::class.java)
            startActivity(intent)
        }

        bindInfo.buttonAdd.setOnClickListener {
            val index = "Добавлен вручную!"
            val intent = Intent(this, ActivityAddChild::class.java).apply {
                putExtra("key", index)
            }
            this.startActivity(intent)
        }

        bindInfo.rvInfo.adapter = adapter
        adapter.notifyDataSetChanged()
        Log.d(ContentValues.TAG, "Типтут")

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_WRITE_EXTERNAL_STORAGE
            )
        }

        createDocumentLauncherFire = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(ContentValues.TAG, "полилисюдаFire ${result.resultCode} = ${RESULT_OK}")
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    createExcelFileFire(uri)
                }
            }
        }
        createDocumentLauncherMed = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(ContentValues.TAG, "полилисюдаMed ${result.resultCode} = ${RESULT_OK}")
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    createExcelFileMed(uri)
                }
            }
        }
        createDocumentLauncherReg = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(ContentValues.TAG, "полилисюдаReg ${result.resultCode} = ${RESULT_OK}")
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    createExcelFileReg(uri)
                }
            }
        }
        /*createDocumentLauncherRoj = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d(ContentValues.TAG, "полилисюдаRoj ${result.resultCode} = ${RESULT_OK}")
            if (result.resultCode == RESULT_OK) {
                result.data?.data?.let { uri ->
                    createExcelFileRoj(uri)
                }
            }
        }*/

    }

    fun searchList(text: String){

        val searchList = ArrayList<Reestr>()
        for (dataClass in adapter.searchL){
            if (dataClass.fioChild?.lowercase()?.contains(text.lowercase()) == true ||
                dataClass.putevka?.lowercase()?.contains(text.lowercase()) == true){
                searchList.add(dataClass)
                Log.d(ContentValues.TAG, "типтутdataClass: ${dataClass}")
            }
        }
        adapter.searchDataList(searchList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.Fire -> {
                Log.d(ContentValues.TAG, "пол")
                showFileChooserFire()
            }
            R.id.Med -> {
                showFileChooserMed()
            }
            R.id.Reg -> {
                showFileChooserReg()
            }
            /*R.id.Roj -> {
                showFileChooserRoj()
            }*/
        }
        return true
    }

    private fun showFileChooserFire() {
        Log.d(ContentValues.TAG, "поласюда")
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_TITLE, "Пожарный список.xlsx")
        }
        createDocumentLauncherFire.launch(intent)
    }
    private fun showFileChooserMed() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_TITLE, "Медецинский список.xlsx")
        }
        createDocumentLauncherMed.launch(intent)
    }
    private fun showFileChooserReg() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_TITLE, "Книга регистрации.xlsx")
        }
        createDocumentLauncherReg.launch(intent)
    }
    /*private fun showFileChooserRoj() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            putExtra(Intent.EXTRA_TITLE, "Именинники.xlsx")
        }
        createDocumentLauncherRoj.launch(intent)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun init() = with(bindInfo) {
        rvInfo.layoutManager = LinearLayoutManager(this@ActivityInfo)
        rvInfo.adapter = adapter

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)

        ref.get().addOnSuccessListener { document ->
            if (document.exists()) {
                adapter.clear()
                tvNomerOtrid.text = "Номер отряда: ${document.getString("otrid")}"
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error", e)
        }
        ref.addSnapshotListener { snapshots, e ->
            if (snapshots != null) {
                adapter.clear()
                tvNomerOtrid.text = "Номер отряда: ${snapshots.getString("otrid")}"
            }


            val refed = db.collection("VojatInfo").document(userID).collection("Children")
            refed.get().addOnSuccessListener { documents ->
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
                    val reestr = Reestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob, medpokaz, otrid)
                    adapter.addReestr(reestr)
                }
            }.addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error", e)
            }
            refed.addSnapshotListener { snapshots, e ->
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
                        val reestr = Reestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob,medpokaz, otrid)
                        adapter.addReestr(reestr)
                    }
                }
            }
        }
    }


    private fun createExcelFileFire(uri: Uri) {

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)
        Log.d(ContentValues.TAG, "ползашел ij")
        ref.get().addOnSuccessListener { document ->
            Log.d(ContentValues.TAG, "ползашел ${document.exists()}")
            if (document.exists()) {
                val ot = document.getString("otrid")
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("отряд $ot")
                Log.d(ContentValues.TAG, "ползашел")
                val header = sheet.createRow(0)
                header.createCell(0).setCellValue("Номер")
                header.createCell(1).setCellValue("ФИО ребенка")
                var i = 1

                val refed = db.collection("VojatInfo").document(userID).collection("Children")
                refed.get().addOnSuccessListener { documents ->
                    for (doc in documents) {
                        Log.d(ContentValues.TAG, "полсделалзаголовок")

                        val fio = doc.getString("fioChild")
                        val row = sheet.createRow(i)
                        row.createCell(0).setCellValue(i.toString())
                        row.createCell(1).setCellValue(fio)
                        Log.d(ContentValues.TAG, "полдалзначение")

                        i++
                        Log.d(ContentValues.TAG, "пол  $i")


                    }
                    var outputStream: OutputStream? = null

                    try {
                        outputStream = contentResolver.openOutputStream(uri)
                        workbook.write(outputStream)
                        workbook.close()
                        Log.d(ContentValues.TAG, "полчтото")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d(ContentValues.TAG, "полошибка $e")
                    } finally {
                        outputStream?.close()
                        Log.d(ContentValues.TAG, "полполучилосьвроде")
                    }
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error", e)
        }
    }
    private fun createExcelFileMed(uri: Uri) {
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)
        Log.d(ContentValues.TAG, "ползашел ij")
        ref.get().addOnSuccessListener { document ->
            Log.d(ContentValues.TAG, "ползашел ${document.exists()}")
            if (document.exists()) {
                val ot = document.getString("otrid")
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("отряд $ot")
                Log.d(ContentValues.TAG, "ползашел")
                val header = sheet.createRow(0)
                header.createCell(0).setCellValue("ФИО ребенка")
                header.createCell(1).setCellValue("Дата рождения")
                header.createCell(2).setCellValue("Состояние здоровья")
                header.createCell(3).setCellValue("ФИО родителя")
                header.createCell(4).setCellValue("Контактный номер")
                var i = 1

                val refed = db.collection("VojatInfo").document(userID).collection("Children")
                refed.get().addOnSuccessListener { documents ->
                    for (doc in documents) {
                        Log.d(ContentValues.TAG, "полсделалзаголовок")

                        val row = sheet.createRow(i)
                        row.createCell(0).setCellValue(doc.getString("fioChild"))
                        row.createCell(1).setCellValue(doc.getString("date"))
                        row.createCell(2).setCellValue(doc.getString("medpokaz"))
                        row.createCell(3).setCellValue(doc.getString("fioParent"))
                        row.createCell(4).setCellValue(doc.getString("phone"))
                        Log.d(ContentValues.TAG, "полдалзначение")

                        i++
                    }
                    var outputStream: OutputStream? = null

                    try {
                        outputStream = contentResolver.openOutputStream(uri)
                        workbook.write(outputStream)
                        workbook.close()
                        Log.d(ContentValues.TAG, "полчтото")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d(ContentValues.TAG, "полошибка")
                    } finally {
                        outputStream?.close()
                        Log.d(ContentValues.TAG, "полполучилосьвроде")
                    }
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error", e)
        }
    }
    private fun createExcelFileReg(uri: Uri) {

        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)

        ref.get().addOnSuccessListener { document ->
            Log.d(ContentValues.TAG, "ползашел ${document.exists()}")
            if (document.exists()) {
                val ot = document.getString("otrid")
                val workbook = XSSFWorkbook()
                val sheet = workbook.createSheet("отряд $ot")
                Log.d(ContentValues.TAG, "ползашел")
                val header = sheet.createRow(0)
                header.createCell(0).setCellValue("Номер")
                header.createCell(1).setCellValue("ФИО ребенка")
                header.createCell(2).setCellValue("Пол")
                header.createCell(3).setCellValue("Дата рождения")
                header.createCell(4).setCellValue("Место учебы")
                header.createCell(5).setCellValue("Класс")
                header.createCell(6).setCellValue("Коллектив")
                header.createCell(7).setCellValue("номер путевки")
                header.createCell(8).setCellValue("ФИО родителя")
                header.createCell(9).setCellValue("Место работы")
                header.createCell(10).setCellValue("Контактный телефон")
                header.createCell(11).setCellValue("Адрес")
                header.createCell(12).setCellValue("Особенности")
                header.createCell(13).setCellValue("Состояние здоровья")



                val refed = db.collection("VojatInfo").document(userID).collection("Children")
                refed.get().addOnSuccessListener { documents ->
                    var i = 1
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "fполсделалзаголовок ${documents.count()} $i")

                        val row = sheet.createRow(i)
                        row.createCell(0).setCellValue(i.toString())
                        row.createCell(1).setCellValue(document.getString("fioChild"))
                        row.createCell(2).setCellValue(document.getString("sex"))
                        row.createCell(3).setCellValue(document.getString("date"))
                        row.createCell(4).setCellValue(document.getString("placeLern"))
                        row.createCell(5).setCellValue(document.getString("clas"))
                        row.createCell(6).setCellValue(document.getString("kolect"))
                        row.createCell(7).setCellValue(document.getString("putevka"))
                        row.createCell(8).setCellValue(document.getString("fioParent"))
                        row.createCell(9).setCellValue(document.getString("placeJob"))
                        row.createCell(10).setCellValue(document.getString("phone"))
                        row.createCell(11).setCellValue(document.getString("adres"))
                        row.createCell(12).setCellValue(document.getString("osob"))
                        row.createCell(13).setCellValue(document.getString("medpokaz"))
                        Log.d(ContentValues.TAG, "полдалзначение")
                        i++


                    }
                    var outputStream: OutputStream? = null

                    try {
                        outputStream = contentResolver.openOutputStream(uri)
                        workbook.write(outputStream)
                        workbook.close()
                        Log.d(ContentValues.TAG, "полчтото")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Log.d(ContentValues.TAG, "полошибка")
                    } finally {
                        outputStream?.close()
                        Log.d(ContentValues.TAG, "полполучилосьвроде")
                    }
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error", e)
        }
    }
    /*private fun createExcelFileRoj(uri: Uri) {
        val workbook = XSSFWorkbook()
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("VojatInfo").document(userID)

        ref.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val sheet = workbook.createSheet("отряд: ${document.getString("otrid")}")
                Log.d(ContentValues.TAG, "ползашел")
                val header = sheet.createRow(0)
                header.createCell(0).setCellValue("Номер")
                header.createCell(1).setCellValue("ФИО ребенка")
                var i = 1

                val refed = db.collection("VojatInfo").document(userID).collection("Children")
                refed.get().addOnSuccessListener { documents ->
                    adapter.clear()
                    for (document in documents) {
                        Log.d(ContentValues.TAG, "полсделалзаголовок")

                        val row = sheet.createRow(i)
                        row.createCell(0).setCellValue(i.toString())
                        row.createCell(1).setCellValue("fioChild")
                        Log.d(ContentValues.TAG, "полдалзначение")

                        i++

                        var outputStream: OutputStream? = null

                        try {
                            outputStream = contentResolver.openOutputStream(uri)
                            workbook.write(outputStream)
                            workbook.close()
                            Log.d(ContentValues.TAG, "полчтото")
                        } catch (e: IOException) {
                            e.printStackTrace()
                            Log.d(ContentValues.TAG, "полошибка")
                        } finally {
                            outputStream?.close()
                            Log.d(ContentValues.TAG, "полполучилосьвроде")
                        }
                    }
                }.addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error", e)
                }
            }
        }.addOnFailureListener { e ->
            Log.w(ContentValues.TAG, "Error", e)
        }
    }*/

    override fun onClick(reestr: Reestr) {
    }

}