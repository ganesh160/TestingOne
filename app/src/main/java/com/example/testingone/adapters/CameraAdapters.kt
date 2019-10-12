package com.example.testingone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testingone.R
import com.example.testingone.WorkingWithCamera
import com.example.testingone.workingwithAdunits.ImageFile
import kotlinx.android.synthetic.main.post_documents_inflater_layout.view.*
import java.io.File

class CameraAdapters(val contexts:Context,val Imglist: ArrayList<ImageFile>) :
    RecyclerView.Adapter<CameraAdapters.ViewHolders>() {

    var context: Context
    var mImageList: ArrayList<ImageFile>
    init {
        this.context = contexts
        this.mImageList = Imglist
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CameraAdapters.ViewHolders {

        return ViewHolders(LayoutInflater.from(context).inflate(R.layout.post_documents_inflater_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return Imglist.size
    }

    override fun onBindViewHolder(holder: CameraAdapters.ViewHolders, position: Int) {

        val data: ImageFile = mImageList.get(position)
        //val imgPath: Uri = Uri.parse(data.imagePath)

        holder.file_name.text = data.imageName

        val imgFile = File((context as WorkingWithCamera).filesDir.toString()+"/"+data.imagePath+"/"+data.imageName+".jpeg")
        Glide.with(contexts)
            .load(imgFile)
            //.override(delete_most_impt_info_doc.measuredWidth, delete_most_impt_info_doc.measuredHeight)
            .fitCenter()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.mImageViewPOstDoc)


    }

    class ViewHolders (view: View): RecyclerView.ViewHolder(view){

        val file_name=view.file_name
        val mImageViewPOstDoc = view.post_doc_img
    }
}