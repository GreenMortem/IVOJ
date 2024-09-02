package com.example.vojdip.admin

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vojdip.R

import com.example.vojdip.databinding.VojItemBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class VojAdapter(var list: List): RecyclerView.Adapter<VojAdapter.VojHolder>() {
    var vojList = ArrayList<Voj>()



    var searchL = ArrayList<Voj>()
    class VojHolder(item: View): RecyclerView.ViewHolder(item) {
        private var db = Firebase.firestore

        val binding = VojItemBinding.bind(item)

        fun bind (voj: Voj, list: List) = with(binding) {
            tvsernameVoj.text = voj.sername
            tvnameVoj.text = voj.name
            tvphoneVoj.text = voj.phone
            tvmailVoj.text = voj.email
            if (voj.otrid == null){
                tvOtridVoj.text = "Отряд: не назначено"
            }else{
                tvOtridVoj.text = "Отряд: ${voj.otrid}"
            }
            Log.d(ContentValues.TAG, "шоты ${voj.email}")

            itemView.setOnClickListener {
                list.onClick(voj)
                Log.d(ContentValues.TAG, "шоты if")
                if (tvmailVoj.visibility== View.VISIBLE) {
                    tvmailVoj.visibility = View.GONE
                    tvOtridVoj.visibility = View.GONE
                    tvnameVoj.visibility = View.GONE
                    etOtridVi.visibility = View.GONE
                    button.visibility = View.GONE
                } else {
                    Log.d(ContentValues.TAG, "шоты else")
                    tvmailVoj.visibility = View.VISIBLE
                    tvOtridVoj.visibility = View.VISIBLE
                    tvnameVoj.visibility = View.VISIBLE
                    etOtridVi.visibility = View.VISIBLE
                    button.visibility = View.VISIBLE
                }
            }
            button.setOnClickListener {
                val ot = etOtridVi.text.toString()
                val user = hashMapOf(
                    "name" to voj.name,
                    "sername" to voj.sername,
                    "phone" to voj.phone,
                    "email" to voj.email,
                    "otrid" to ot
                )
                db.collection("VojatInfo").document(voj.userid.toString()).set(user)
                etOtridVi.clearComposingText()

            }
        }
    }

    fun clear(){
        vojList.clear()
        notifyDataSetChanged()
    }

    fun addVoj(voj: Voj){
        vojList.add(voj)
        searchL = vojList
        notifyDataSetChanged()
    }
    fun searchDataList(searchList: ArrayList<Voj>){

        vojList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VojHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voj_item, parent, false)
        return VojAdapter.VojHolder(view)
    }

    override fun getItemCount(): Int {
        return vojList.size
    }

    override fun onBindViewHolder(holder: VojHolder, position: Int) {
        holder.bind(vojList[position], list)
    }

    interface List {
        fun onClick(voj: Voj)

    }
}