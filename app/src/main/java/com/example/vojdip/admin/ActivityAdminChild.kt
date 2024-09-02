package com.example.vojdip.admin


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import android.widget.SearchView
import com.example.vojdip.databinding.ActivityAdminChildBinding

class ActivityAdminChild : AppCompatActivity(), ReestrAdapter.List {

    private var db = Firebase.firestore
    private  val adapter = ReestrAdapter(this)



    private lateinit var numb: String
    private lateinit var index: String
    private lateinit var fioChild: String
    private lateinit var sex: String
    private lateinit var date: String
    private lateinit var placeLern: String
    private lateinit var clas: String
    private lateinit var kolect: String
    private lateinit var putevka: String
    private lateinit var fioParent: String
    private lateinit var placeJob: String
    private lateinit var phone: String
    private lateinit var adres: String
    private lateinit var osob: String
    private lateinit var medpokaz: String
    private lateinit var otrid: String


    private val PICK_FILE_REQUEST_CODE = 1


    lateinit var bindAdminChild : ActivityAdminChildBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindAdminChild = ActivityAdminChildBinding.inflate(layoutInflater)
        setContentView(bindAdminChild.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Реестр детей"
        init()
        bindAdminChild.bnAdChAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" // все типы файлов
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
        }

        bindAdminChild.searchView.setIconifiedByDefault(false)
        bindAdminChild.searchView.queryHint = "Поиск"
        bindAdminChild.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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

    private fun init() = with(bindAdminChild){
        rvAdCh.layoutManager = LinearLayoutManager(this@ActivityAdminChild)
        rvAdCh.adapter = adapter
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
                    val medpokaz = document.getString("medpokaz")
                    val osob = document.getString("osob")
                    val otrid = document.getString("otrid")
                    val reestr = Reestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob, medpokaz, otrid)
                    adapter.addReestr(reestr)
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
                    val reestr = Reestr(index, numb, fioChild, sex, date, placeLern, clas, kolect, putevka, fioParent, placeJob, phone, adres, osob, medpokaz, otrid)
                    adapter.addReestr(reestr)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Получаем URI выбранного файла
            val uri = data?.data
            uri?.let { readExcelFile(it) }
        }
    }
    private fun readExcelFile(uri: Uri) {
        var i = 0
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val rows = sheet.iterator()
        rows.next()
        while (rows.hasNext()) {
            Log.d(ContentValues.TAG, "типтут строка")
            val currentRow = rows.next()
            val cellIterator = currentRow.iterator()
            while (cellIterator.hasNext()) {



                var currentCell = cellIterator.next()
                var cellValue = currentCell.toString()
                numb = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                fioChild = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                sex = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                date = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                placeLern = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                clas = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                kolect = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                putevka = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                fioParent = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                placeJob = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                phone = cellValue.trim()

                currentCell = cellIterator.next()
                cellValue = currentCell.toString()
                adres = cellValue.trim()

                val chil = hashMapOf(
                    "index" to i.toString(),
                    "numb" to numb,
                    "fioChild" to fioChild,
                    "sex" to sex,
                    "date" to date,
                    "placeLern" to placeLern,
                    "clas" to clas,
                    "kolect" to kolect,
                    "putevka" to putevka,
                    "fioParent" to fioParent,
                    "placeJob" to placeJob,
                    "phone" to phone,
                    "adres" to adres,
                    "osob" to "",
                    "medpokaz" to "",
                    "otrid" to ""
                )

                Log.d(ContentValues.TAG, "типтуа ${chil}")

                db.collection("Reestr").document(i.toString()).set(chil)
                i++

            }
        }
        workbook.close()
        inputStream?.close()
        Toast.makeText(
            baseContext,
            "Загружена!",
            Toast.LENGTH_SHORT,
        ).show()
    }

    override fun onClick(reestr: Reestr) {


    }
}
