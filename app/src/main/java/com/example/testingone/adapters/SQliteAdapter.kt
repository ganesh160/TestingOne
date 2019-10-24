package com.example.testingone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.testingone.model.SqlModels
import kotlinx.android.synthetic.main.reprocess_list_item.view.*
import android.R.attr.data
import android.widget.Filter
import android.widget.Filterable
import com.example.testingone.HotelWithSqliteDb.FirstActity
import com.example.testingone.HotelWithSqliteDb.HotelModel
import com.example.testingone.R


class SQliteAdapter(val context:Context,var dList:ArrayList<HotelModel>):
    RecyclerView.Adapter<SQliteAdapter.ViewHolderss>() ,Filterable {

     var dlistFilterable:ArrayList<HotelModel>
     lateinit var Filterable:ArrayList<HotelModel>
    init {
        dlistFilterable=dList
    }

    lateinit var firstActivittt:FirstActity
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SQliteAdapter.ViewHolderss {
//
//        return ViewHolderss(LayoutInflater.from(context).inflate(R.layout.reprocess_list_item, parent, false))
//    }
//
//    override fun getItemCount(): Int {
//        return dList.size
//    }
//
//    override fun onBindViewHolder(holder: SQliteAdapter.ViewHolderss, position: Int) {
//
//        holder.fnmae.setText(dList.get(position).fnames)
//        holder.lnmae.setText(dList.get(position).lNames)
//        holder.emails.setText(dList.get(position).eMails)
//    }
//
//    class ViewHolderss(view:View):RecyclerView.ViewHolder(view){
//
//        val fnmae=view.fnmae
//        val lnmae=view.lnmae
//        val emails=view.emails
//    }
//    fun gerData() :ArrayList<SqlModels>{
//        return dList
//    }

    fun removeItem(position: Int) {
        dList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SQliteAdapter.ViewHolderss {

        return ViewHolderss(LayoutInflater.from(context).inflate(R.layout.reprocess_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dList.size
    }

    override fun onBindViewHolder(holder: SQliteAdapter.ViewHolderss, position: Int) {

        val data:HotelModel=dList.get(position)
        holder.fnmae.setText(dList.get(position).iNames)
        holder.lnmae.setText(dList.get(position).icost)
        holder.emails.setText(dList.get(position).igsts)
        holder.total.setText("Rs ."+dList.get(position).itotals)
        holder.tablayout.tag=data

        holder.tablayout.setOnClickListener {
            firstActivittt.deleteImg(it.tag as HotelModel)
        }

    }

    class ViewHolderss(view:View):RecyclerView.ViewHolder(view){

        val fnmae=view.fnmae
        val lnmae=view.lnmae
        val emails=view.emails
        val total=view.total
        val tablayout=view.tablayout
    }


    override fun getFilter(): Filter {

        val sss = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val results =
                    FilterResults()        // Holds the results of a filtering operation in values
                val FilteredArrList = ArrayList<HotelModel>()

                if (dList == null) {
                    dList =
                        ArrayList<HotelModel>() // saves the original data in mOriginalValues
                }

                if (p0 == null || p0.length === 0) {

                    // set the Original result to return
                    results.count = dList.size
                    results.values = dList
                } else {
                    var p0 = p0.toString().toLowerCase()
                    for (i in 0 until dList.size) {
                        val data = dList.get(i).iNames
                        if (data.toLowerCase().startsWith(p0.toString())) {
                            FilteredArrList.add(
                                HotelModel(
                                    dList.get(i).iNames,
                                    dList.get(i).icost, "", ""
                                )
                            )
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size
                    results.values = FilteredArrList
                }
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                dList = p1!!.values as ArrayList<HotelModel>
                notifyDataSetChanged()
            }
        }
        return sss
    }

        fun callBack(firstActivity: FirstActity) {
            firstActivittt = firstActivity
        }
}