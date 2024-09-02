package com.example.vojdip.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vojdip.R
import com.example.vojdip.databinding.ReestrItemBinding


class ReestrAdapter(var list: List): RecyclerView.Adapter<ReestrAdapter.ReestrHolder>() {
    var reestrList = ArrayList<Reestr>()

    var searchL = ArrayList<Reestr>()

    class ReestrHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ReestrItemBinding.bind(item)
        fun bind (reestr: Reestr, list: List) = with(binding) {
            tvfioreestr.text = "Фио ребенка: ${reestr.fioChild}"
            tvputevka.text = "Номер путевки: ${reestr.putevka}"
            tvsex.text = "Пол: ${reestr.sex}"
            tvdate.text = "Дата рождения: ${reestr.date}"
            tvplaceLern.text = "Место учебы: ${reestr.placeLern}"
            tvclas.text = "Класс: ${reestr.clas}"
            tvkolect.text = "Коллектив: ${reestr.kolect}"
            tvfioParent.text = "Фио родителя: ${reestr.fioParent}"
            tvplaceJob.text = "Место работы: ${reestr.placeJob}"
            tvphone.text = "Телефон: ${reestr.phone}"
            tvadres.text = "Адрес: ${reestr.adres}"
            tvosob.text = "Особенности: ${reestr.osob}"
            tvmedpok.text = "Медицинские показания: ${reestr.medpokaz}"
            tvotrid.text = "Отряд: ${reestr.otrid}"


            itemView.setOnClickListener {
                list.onClick(reestr)
                if (tvsex.visibility== View.VISIBLE) {
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
                }

            }
        }
    }

    fun clear(){
        reestrList.clear()
        notifyDataSetChanged()
    }

    fun addReestr(reestr: Reestr){
        reestrList.add(reestr)
        searchL = reestrList
        notifyDataSetChanged()
    }
    fun searchDataList(searchList: ArrayList<Reestr>){

        reestrList = searchList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReestrHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reestr_item, parent, false)
        return ReestrHolder(view)
    }

    override fun onBindViewHolder(holder: ReestrHolder, position: Int) {
        holder.bind(reestrList[position], list)
    }

    override fun getItemCount(): Int {
        return reestrList.size
    }

    interface List {
        fun onClick(reestr: Reestr)
    }

}