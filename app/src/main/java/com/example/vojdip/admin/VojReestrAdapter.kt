package com.example.vojdip.admin

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vojdip.R
import com.example.vojdip.databinding.VojReestrItemBinding
import com.example.vojdip.vojat.ActivityAddChild


class VojReestrAdapter(val context: Context, var list: List): RecyclerView.Adapter<VojReestrAdapter.VojReestrHolder>() {

    var vojreestrList = ArrayList<VojReestr>()

    var searchL = ArrayList<VojReestr>()

    inner class VojReestrHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = VojReestrItemBinding.bind(item)
        fun bind (vojreestr: VojReestr, list: List) = with(binding) {
            tvfioreestr.text = "Фио ребенка: ${vojreestr.fioChild}"
            tvputevka.text = "Номер путевки: ${vojreestr.putevka}"
            tvsex.text = "Пол: ${vojreestr.sex}"
            tvdate.text = "Дата рождения: ${vojreestr.date}"
            tvplaceLern.text = "Место учебы: ${vojreestr.placeLern}"
            tvclas.text = "Класс: ${vojreestr.clas}"
            tvkolect.text = "Коллектив: ${vojreestr.kolect}"
            tvfioParent.text = "Фио родителя: ${vojreestr.fioParent}"
            tvplaceJob.text = "Место работы: ${vojreestr.placeJob}"
            tvphone.text = "Телефон: ${vojreestr.phone}"
            tvadres.text = "Адрес: ${vojreestr.adres}"
            tvosob.text = "Особенности: ${vojreestr.osob}"
            tvmedpok.text = "Медицинские показания: ${vojreestr.medpokaz}"
            tvotrid.text = "Отряд: ${vojreestr.otrid}"


            itemView.setOnClickListener {
                list.onClick(vojreestr)
                if (tvsex.visibility == View.VISIBLE) {
                    tvsex.visibility = View.GONE
                    tvdate.visibility = View.GONE
                    tvplaceLern.visibility = View.GONE
                    tvclas.visibility = View.GONE
                    tvkolect.visibility = View.GONE
                    tvfioParent.visibility = View.GONE
                    tvplaceJob.visibility = View.GONE
                    tvphone.visibility = View.GONE
                    tvadres.visibility = View.GONE
                    tvosob.visibility = View.GONE
                    tvmedpok.visibility = View.GONE
                    tvotrid.visibility = View.GONE
                    buttonAddChR.visibility = View.GONE
                } else {
                    tvsex.visibility = View.VISIBLE
                    tvdate.visibility = View.VISIBLE
                    tvplaceLern.visibility = View.VISIBLE
                    tvclas.visibility = View.VISIBLE
                    tvkolect.visibility = View.VISIBLE
                    tvfioParent.visibility = View.VISIBLE
                    tvplaceJob.visibility = View.VISIBLE
                    tvphone.visibility = View.VISIBLE
                    tvadres.visibility = View.VISIBLE
                    tvosob.visibility = View.VISIBLE
                    tvmedpok.visibility = View.VISIBLE
                    tvotrid.visibility = View.VISIBLE
                    buttonAddChR.visibility = View.VISIBLE
                }

            }
            buttonAddChR.setOnClickListener {

                val intent = Intent(context, ActivityAddChild::class.java).apply {
                    putExtra("key", vojreestr.index)
                }

                context.startActivity(intent)
            }
        }
    }

    fun clear(){
        vojreestrList.clear()
        notifyDataSetChanged()
    }

    fun setVojReestrList(newList: ArrayList<VojReestr>) {
        vojreestrList.clear()
        vojreestrList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addVojReestr(vojreestr: VojReestr){
        vojreestrList.add(vojreestr)
        searchL = vojreestrList
        notifyDataSetChanged()
    }

    fun searchDataList(searchList: ArrayList<VojReestr>){

        vojreestrList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VojReestrHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.voj_reestr_item, parent, false)
        return VojReestrHolder(view)
    }

    override fun getItemCount(): Int {
        return vojreestrList.size
    }

    override fun onBindViewHolder(holder: VojReestrHolder, position: Int) {
        holder.bind(vojreestrList[position], list)

    }

    interface List {
        fun onClick(vojreestr: VojReestr)
    }
}