package com.example.testingone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.testingone.model.SqlModels
import kotlinx.android.synthetic.main.reprocess_list_item.view.*
import android.R.attr.data
import com.example.testingone.R


class SQliteAdapter(val context:Context,val dList:ArrayList<SqlModels>):
    RecyclerView.Adapter<SQliteAdapter.ViewHolderss>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SQliteAdapter.ViewHolderss {

        return ViewHolderss(LayoutInflater.from(context).inflate(R.layout.reprocess_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dList.size
    }

    override fun onBindViewHolder(holder: SQliteAdapter.ViewHolderss, position: Int) {

        holder.fnmae.setText(dList.get(position).fnames)
        holder.lnmae.setText(dList.get(position).lNames)
        holder.emails.setText(dList.get(position).eMails)
    }

    class ViewHolderss(view:View):RecyclerView.ViewHolder(view){

        val fnmae=view.fnmae
        val lnmae=view.lnmae
        val emails=view.emails
    }
    fun gerData() :ArrayList<SqlModels>{
        return dList
    }

    fun removeItem(position: Int) {

        dList.removeAt(position)
        notifyItemRemoved(position)
    }
}