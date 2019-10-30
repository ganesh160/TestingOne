package com.example.testingone.Util

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.testingone.R
import com.example.testingone.SideNavigation.BaseActivity
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.filter
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class UImportant {
    companion object {
        var ApplicationStatusClicked: Boolean = false
        var fromStatus: String = ""
        var emailVailds: String = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        lateinit var formattedDate: String
        var screenHeight: Int = 0
        var screenWidth: Int = 0
        var DisbursementClicked: Boolean = false
        var ReprocessClicked: Boolean = false
        var PendingFormsClicked: Boolean = false


        var Most_impt_Info_Doc_Count: Int = 0
        var Signature_Count: Int = 0
        var Agri_Income_Count: Int = 0
        var ID_Proof_Count: Int = 0
        var Address_Proof_Count: Int = 0
        var Proforma__Count: Int = 0
        var Income_Count: Int = 0
        var BankStatement_Count: Int = 0
        var Customer_Photo_Count: Int = 0
        var Form_60_61: Int = 0
        var Licence_Count: Int = 0
        var Pancard_Count: Int = 0

        var Aggrement_Document_Count: Int = 0
        var Rtocopy_Document_Count: Int = 0
        var Margin_Money_Document_Count: Int = 0
        var Nach_Mandate_Document_Count: Int = 0
        var Spdc_Document_Count: Int = 0
        var RC_Copy_Document_Count: Int = 0
        var Insurance_Document_Count: Int = 0
        var Invoice_Document_Count: Int = 0
        var Deviation_Document_Count: Int = 0
        var Life_Insurance_Count: Int = 0
        var  mFrom_Presanction_PhotoCapture : Boolean= false;


        var Reprocess_Most_impt_Info_Doc_Count: Int = 0
        var Reprocess_Signature_Count: Int = 0
        var Reprocess_Agri_Income_Count: Int = 0
        var Reprocess_ID_Proof_Count: Int = 0
        var Reprocess_Address_Proof_Count: Int = 0
        var Reprocess_Proforma__Count: Int = 0
        var Reprocess_Income_Count: Int = 0
        var Reprocess_BankStatement_Count: Int = 0
        var Reprocess_Customer_Photo_Count: Int = 0
        var Reprocess_Form_60_61: Int = 0
        var Reprocess_Licence_Count: Int = 0
        var Reprocess_Pancard_Count: Int = 0


        lateinit var mPhotoFile: File
        lateinit var mPostSanction_PhotoFile: File

        fun getAppFileDir(mContext: Context) : String {
            return mContext.filesDir.toString()
        }


        fun checkInternetConnection(mContext: Context): Boolean {
            val conMgr: ConnectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return conMgr.activeNetworkInfo != null && conMgr.activeNetworkInfo.isConnected
        }

        fun showCustomToast(mContext: AppCompatActivity, message: String) {
            val layoutInflater = mContext.getLayoutInflater()
            //val view_custom_toast = layoutInflater.inflate(R.layout.layout_toast, null)
            //val text = view_custom_toast.findViewById(R.id.txt_Toast) as TextView
            val customtoast = Toast(mContext)
            customtoast.setGravity(Gravity.CENTER, 0, 50)
            //text.text = message
            //customtoast.view = view_custom_toast
            customtoast.duration = Toast.LENGTH_LONG
            customtoast.show()
        }

        fun hideKeyboard(v: View) {
            val inputManager = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        fun splitDate(mActivity: Activity, date: String): String {
            return date.trim().split(" ")[0]
        }

        fun showAlert(mConext: Context, Message: String) {
            val alert = AlertDialog.Builder(mConext)
            alert.setTitle("Alert")
            alert.setMessage(Message)
            alert.setPositiveButton("OK") { dialog, which ->
                (mConext as AppCompatActivity).finish()
                dialog.dismiss()
            }.setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }
            alert.show()
        }

        fun showDatePickerDOBDilog(mActivity: Activity, et_formation_date: View, enableFutureDate: Boolean): String {
            formattedDate = "";
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val listner = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                try {
                    val monthC = month + 1
                    var dayName: String? = null
                    var monthName: String? = null
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                        dayName = "0$dayOfMonth"
                    } else {
                        dayName = "" + dayOfMonth
                    }
                    if (monthC >= 1 && monthC <= 9) {
                        monthName = "0$monthC"
                    } else {
                        monthName = "" + monthC
                    }
//$dayName

                    formattedDate = "$monthName/$dayName/$year"

                    (et_formation_date as EditText).setText("$monthName/$dayName/$year")
                    et_formation_date.error = null

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val datePickerDialog = DatePickerDialog(mActivity, listner, year, month, day)
            val now = System.currentTimeMillis() - 1000
            if (!enableFutureDate) {
                datePickerDialog.datePicker.maxDate = now
            }
            datePickerDialog.show()
            return formattedDate
        }


        fun DeclarationDatePickerDOBDilog(mActivity: Activity, et_formation_date: View, enableFutureDate: Boolean): String {
            formattedDate = "";
            val calendar = Calendar.getInstance()
            //calendar.add(Calendar.DATE, +1)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            val listner = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                try {
                    val monthC = month + 1
                    var dayName: String? = null
                    var monthName: String? = null
                    if (dayOfMonth >= 1 && dayOfMonth <= 9) {
                        dayName = "0$dayOfMonth"
                    } else {
                        dayName = "" + dayOfMonth
                    }
                    if (monthC >= 1 && monthC <= 9) {
                        monthName = "0$monthC"
                    } else {
                        monthName = "" + monthC
                    }
//$dayName

                    formattedDate = "$monthName/$dayName/$year"

                    (et_formation_date as EditText).setText("$monthName/$dayName/$year")
                    et_formation_date.error = null

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val datePickerDialog = DatePickerDialog(mActivity, listner, year, month, day)
            val now = System.currentTimeMillis() - 1000
            if (!enableFutureDate) {

                //datePickerDialog.datePicker.maxDate = Date().time

                val min_calendar = Calendar.getInstance()

                datePickerDialog.datePicker.minDate = min_calendar.timeInMillis
            }
            datePickerDialog.show()
            return formattedDate
        }


        fun parseDateFormat(time: String): String? {
            val inputPattern = "MM-dd-yyyy"
            val outputPattern = "dd/MM/yyyy"
            val inputFormat = SimpleDateFormat(inputPattern, Locale.US)
            val outputFormat = SimpleDateFormat(outputPattern, Locale.US)

            var date: Date? = null
            var str: String? = null

            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return str
        }

        fun emptyCheckEditBox(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 3) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }
        fun emptyCheckEditBoxHotel(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 1) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }


        fun emptyCheckEditBoxPassword(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 6) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }


        fun emptyCheckEditBox2Digit(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 2) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }




        fun CheckMonthsAtAddress(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().toInt() <= 11) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }
        fun YearsAccountActive(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().toInt() <= 99) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }

        fun emptyCheckEditBox1Digit(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 1) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }

        fun emptyCheckEditBox4Digit(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 4) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }

        fun CheckMinAmountLength(editBox: EditText, errorMsg: String): Boolean{
            if (editBox.text.toString().length >= 4) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }
        fun CheckMicrLength(editBox: EditText, errorMsg: String): Boolean{
                    if (editBox.text.toString().length ==9) {
                        editBox.setError(null)
                        return true
                    } else {
                        editBox.setError(errorMsg)
                        editBox.requestFocus()
                        hideKeyboard(editBox)
                        return false
                    }
                }


        fun isValidMobile(editBox: EditText, errorMsg: String): Boolean {
            var phone: String = editBox.text.toString().trim()
            var check = false
            if (!Pattern.matches("[a-zA-Z]+", phone)) {
                if (phone.length == 10 && Integer.parseInt(phone[0].toString()) > 5) {
                    editBox.setError(null)
                    check = true
                } else {
                    editBox.setError(errorMsg)
                    editBox.requestFocus()
                    hideKeyboard(editBox)
                    check = false
                }
            }
            return check
        }


        fun isValidAmount(editBox: EditText, errorMsg: String): Boolean {
            var amount: String = editBox.text.toString().trim()
            var check = false
            if (!amount[0].toString().equals("0")) {
                check = true
                editBox.setError(null)
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                check = false
            }
            return check
        }


//        fun isValidStdCode(editBox: EditText, errorMsg: String): Boolean {
//            var amount: String = editBox.text.toString().trim()
//            var check = false
//            if (!amount[1].toString().equals("0")) {
//                check = true
//                editBox.setError(null)
//            } else {
//                editBox.setError(errorMsg)
//                editBox.requestFocus()
//                hideKeyboard(editBox)
//                check = false
//            }
//            return check
//        }


        fun isValidBankNumber(editBox: EditText, errorMsg: String): Boolean {
            var amount: String = editBox.text.toString().trim()
            var check = false
            var count:Int=0

            for (i in 0..amount.length-1) {
                if (amount[i].toString().equals("0")) {
                    count++
                }
            }
            if (count!=amount.length) {
                check = true
                editBox.setError(null)
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                check = false
            }

            return check
        }




        fun validDateRoiEditBox2Digit(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 2 && editBox.text.toString().trim { it <= ' ' }.length <= 5) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }

        fun validDateEditBox4Digit(editBox: EditText, errorMsg: String): Boolean {
            if (editBox.text.toString().trim { it <= ' ' }.length >= 4) {
                editBox.setError(null)
                return true
            } else {
                editBox.setError(errorMsg)
                editBox.requestFocus()
                hideKeyboard(editBox)
                return false
            }
        }

        fun calculateAge(birthDate: Date): Age {
            var years = 0
            var months = 0
            var days = 0

            //create calendar object for birth day
            val birthDay = Calendar.getInstance()
            birthDay.timeInMillis = birthDate.time

            //create calendar object for current day
            val currentTime = System.currentTimeMillis()
            val now = Calendar.getInstance()
            now.timeInMillis = currentTime

            //Get difference between years
            years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR)
            val currMonth = now.get(Calendar.MONTH) + 1
            val birthMonth = birthDay.get(Calendar.MONTH) + 1

            //Get difference between months
            months = currMonth - birthMonth

            //if month difference is in negative then reduce years by one
            //and calculate the number of months.
            if (months < 0) {
                years--
                months = 12 - birthMonth + currMonth
                if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                    months--
            } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                years--
                months = 11
            }

            //Calculate the days
            if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
                days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE)
            else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                val today = now.get(Calendar.DAY_OF_MONTH)
                now.add(Calendar.MONTH, -1)
                days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today
            } else {
                days = 0
                if (months == 12) {
                    years++
                    months = 0
                }
            }
            //Create new Age object
            return Age(days, months, years)
        }



        fun getCityFromPinCode(mContext : Context, latLong : List<String>) : String{
            var city : String = ""
            val gcd : Geocoder = Geocoder(mContext, Locale.getDefault());
            var addressList : List<Address> = arrayListOf();
            addressList = gcd.getFromLocation(latLong[0].toDouble(), latLong[1].toDouble(), 1);

            if (addressList != null && addressList.size > 0) {
                val address: Address = addressList . get (0);

                city = address.getSubAdminArea(); //city
                address.getAdminArea();  //state
                address.getPostalCode();  //zipcode
            }

            return city
        }

        fun buildAlertMessageNoGps(mContext: Context) {
            val builder = AlertDialog.Builder(mContext)
            builder.setMessage("Your GPS seems to be disabled, click YES to enable it.")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    dialog.dismiss()
                    mContext.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            builder.show()
        }


        fun checkWEmail(inputDate: String): Boolean {
            val iss: CharSequence = inputDate

            return Pattern.compile(emailVailds).matcher(iss).matches()
        }

        fun deleteDir(dir: File): Boolean {
            if (dir.isDirectory) {
                val children = dir.list()
                for (i in children!!.indices) {
                    val success = deleteDir(File(dir, children[i]))
                    if (!success) {
                        return false
                    }
                }
            }
            // The directory is now empty so delete it
            return dir.delete()
        }

        fun deleteLatest() {
            // TODO Auto-generated method stub
            val f = File(Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera")

            //Log.i("Log", "file name in delete folder :  "+f.toString());
            val files = f.listFiles()
            if (files != null) {
                //Log.i("Log", "List of files is: " +files.toString());
                Arrays.sort(files) { o1, o2 ->
                    if ((o1 as File).lastModified() > (o2 as File).lastModified()) {
                        //Log.i("Log", "Going -1");
                        -1
                    } else if (o1.lastModified() < o2.lastModified()) {
                        //Log.i("Log", "Going +1");
                        1
                    } else {
                        //Log.i("Log", "Going 0");
                        0
                    }
                    //return Long.compare(((File) o2).lastModified(), ((File) o1).lastModified());
                }

                if (files.size > 0) {
                    //Log.i("Log", "Count of the FILES AFTER DELETING ::"+files[0].length());
                    if (files[0].exists()) {
                        files[0].delete()
                    }
                }
            }
        }

        fun compressImage(mContext: Context, imageUri: String): String {

            val filePath = getRealPathFromURI(mContext, imageUri)
            var scaledBitmap: Bitmap? = null

            val options = BitmapFactory.Options()

            //      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
            //      you try the use the bitmap here, you will get null.
            options.inJustDecodeBounds = true
            var bmp = BitmapFactory.decodeFile(filePath, options)

            var actualHeight = options.outHeight
            var actualWidth = options.outWidth

            //      max Height and width values of the compressed image is taken as 816x612

            //float maxHeight = 816.0f;
            //float maxWidth = 612.0f;

            val maxHeight = 1920.0f;
            val maxWidth = 1080.0f;

            //val maxHeight = 2160.0f
            //val maxWidth = 3840.0f


            var imgRatio = (actualWidth / actualHeight).toFloat()
            val maxRatio = maxWidth / maxHeight

            //      width and height values are set maintaining the aspect ratio of the image

            if (actualHeight > maxHeight || actualWidth > maxWidth) {
                if (imgRatio < maxRatio) {
                    imgRatio = maxHeight / actualHeight
                    actualWidth = (imgRatio * actualWidth).toInt()
                    actualHeight = maxHeight.toInt()
                } else if (imgRatio > maxRatio) {
                    imgRatio = maxWidth / actualWidth
                    actualHeight = (imgRatio * actualHeight).toInt()
                    actualWidth = maxWidth.toInt()
                } else {
                    actualHeight = maxHeight.toInt()
                    actualWidth = maxWidth.toInt()

                }
            }

            //      setting inSampleSize value allows to load a scaled down version of the original image
            options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

            //      inJustDecodeBounds set to false to load the actual bitmap
            options.inJustDecodeBounds = false

            //      this options allow android to claim the bitmap memory if it runs low on memory
            options.inPurgeable = true
            options.inInputShareable = true
            options.inTempStorage = ByteArray(16 * 1024)

            try {
                //          load the bitmap from its path
                bmp = BitmapFactory.decodeFile(filePath, options)
            } catch (exception: Exception) {
                exception.printStackTrace()

            }

            try {
                scaledBitmap =
                    Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
            } catch (exception: java.lang.Exception) {
                exception.printStackTrace()
            }

            val ratioX = actualWidth / options.outWidth.toFloat()
            val ratioY = actualHeight / options.outHeight.toFloat()
            val middleX = actualWidth / 2.0f
            val middleY = actualHeight / 2.0f

            val scaleMatrix = Matrix()
            scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

            val canvas = Canvas(scaledBitmap!!)
            canvas.setMatrix(scaleMatrix)
            canvas.drawBitmap(
                bmp,
                middleX - bmp.width / 2,
                middleY - bmp.height / 2,
                Paint(Paint.FILTER_BITMAP_FLAG)
            )

            //      check the rotation of the image and display it properly
            val exif: ExifInterface
            try {
                exif = ExifInterface(filePath!!)

                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0
                )
                Log.d("EXIF", "Exif: $orientation")
                val matrix = Matrix()
                if (orientation == 6) {
                    matrix.postRotate(90f)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 3) {
                    matrix.postRotate(180f)
                    Log.d("EXIF", "Exif: $orientation")
                } else if (orientation == 8) {
                    matrix.postRotate(270f)
                    Log.d("EXIF", "Exif: $orientation")
                }
                scaledBitmap = Bitmap.createBitmap(
                    scaledBitmap, 0, 0,
                    scaledBitmap.width, scaledBitmap.height, matrix,
                    true
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            var out: FileOutputStream? = null
            val filename = filePath
            try {
                out = FileOutputStream(filename)

                //          write the compressed bitmap at the destination specified by filename.
                scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            return filename!!
        }

        private fun getRealPathFromURI(mContext: Context, contentURI: String): String? {
            val contentUri = Uri.parse(contentURI)
            val cursor = mContext.getContentResolver()!!.query(contentUri, null, null, null, null)
            if (cursor == null) {
                return contentUri.path
            } else {
                cursor!!.moveToFirst()
                val index = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                return cursor!!.getString(index)
            }
        }

        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
            val totalPixels = (width * height).toFloat()
            val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++
            }

            return inSampleSize
        }

        fun slideUp(view : View){
            view.visibility = View.VISIBLE

            val animate = TranslateAnimation(0.0f,0.0f,view.height.toFloat(), 0.0f)
            animate.duration = 300
            animate.fillAfter = true
            view.animation = animate
        }

        fun slideDown(view : View){
            view.visibility = View.GONE

            val animate = TranslateAnimation(0.0f,0.0f,0.0f, view.height.toFloat())
            animate.duration = 300
            animate.fillAfter = true
            view.animation = animate
        }

        fun makeAlert(mContext : Context, message:String){
            val alert = AlertDialog.Builder(mContext as BaseActivity)
            alert.setMessage(message)
            alert.setPositiveButton("OK") { dialog, which ->
                (mContext as BaseActivity).finish()
                (mContext as BaseActivity).overridePendingTransition(R.anim.enter_from_left_frag, R.anim.exit_to_right_frag)
                dialog.dismiss()
            }
            alert.setCancelable(false)
            alert.show()
        }



        fun showAlert(context : Context) {
            val alert = AlertDialog.Builder(context)
            alert.setTitle("Alert")
            alert.setCancelable(false)
            alert.setMessage("Your session expired, Please Re-Login.")
            alert.setPositiveButton("OK") { dialog, which ->
                dialog.dismiss()
                //mDrawerLayout.openDrawer(Gravity.END);
            }
            alert.show()
        }
    }
}