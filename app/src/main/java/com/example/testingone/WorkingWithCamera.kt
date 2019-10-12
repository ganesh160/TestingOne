package com.example.testingone


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testingone.adapters.CameraAdapters
import com.example.testingone.workingwithAdunits.Camera
import com.example.testingone.workingwithAdunits.ImageFile
import kotlinx.android.synthetic.main.activity_working_with_camera.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class WorkingWithCamera : AppCompatActivity() {

    val mPreSanctionDocumentList: ArrayList<ImageFile> = arrayListOf()
    val REQUEST_IMAGE_CAPTURE: Int = 1234
    lateinit var obj: ImageFile
    lateinit var file: File
    lateinit var camera: Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_working_with_camera)

        capture_image_btn.setOnClickListener{

            obj= ImageFile()
            obj.imageName="first"
            obj.imagePath="WATS_DOC"
            //code to capture with camera
            capture(obj)
        }


    }
    fun capture(obj: ImageFile) {
        camera = Camera.Builder(this)
            .resetToCorrectOrientation(true)
            .setTakePhotoRequestCode(REQUEST_IMAGE_CAPTURE)
            .setDirectory(obj.imagePath)
            .setName(obj.imageName)
            .setImageFormat(Camera.IMAGE_JPEG)
            .setCompression(100)
            .setImageHeight(((0 * 0.30).toInt()))
            .build(this)
        try {
            camera.takePicture()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==REQUEST_IMAGE_CAPTURE && resultCode==Activity.RESULT_OK){

            mPreSanctionDocumentList.add(obj)

            uploadDataToServer()
            val adapter=CameraAdapters(this,mPreSanctionDocumentList)
            camera_recycler.setLayoutManager(GridLayoutManager(this, 2))
            camera_recycler.adapter = adapter


        }else if (resultCode == Activity.RESULT_CANCELED) {

        }
    }


    fun uploadDataToServer(){

        var requestBody: RequestBody
        var multiPartBody: MultipartBody.Part
        val multipleFiles: ArrayList<MultipartBody.Part> = ArrayList<MultipartBody.Part>()//arrayListOf()



        for (i in 0..mPreSanctionDocumentList.size - 1) {
            file = File(
                (this).filesDir.toString() + "/" + mPreSanctionDocumentList.get(
                    i
                ).imagePath + "/" + mPreSanctionDocumentList.get(i).imageName + ".jpeg"
            )
            requestBody = RequestBody.create(MediaType.parse("*/*"), file)


            when (mPreSanctionDocumentList.get(i).imageName) {
                "first" -> {
                    multiPartBody =
                        MultipartBody.Part.createFormData(
                            "ADDRESS1",
                            (this).filesDir.toString() + "/" +mPreSanctionDocumentList.get(
                                i
                            ).imagePath + "/" + mPreSanctionDocumentList.get(i).imageName + ".jpeg",
                            requestBody
                        )
                    multipleFiles.add(multiPartBody)
                }
            }


        }

        //Toast.makeText(this,""+multiPartBody.toString(),Toast.LENGTH_SHORT).show()


    }

}
