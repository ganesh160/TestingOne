package com.example.testingone.sqldatabase

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.graphics.drawable.ColorDrawable
import android.R
import android.graphics.*
import androidx.core.content.ContextCompat
import android.graphics.Color.parseColor





open class SwipeToDeleteCallback (context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    var mClearPaint: Paint
    private var mBackground: ColorDrawable? = null
    private var backgroundColor: Int = 0
    private var deleteDrawable: Drawable? = null
    private var intrinsicWidth: Int = 0
    private var intrinsicHeight: Int = 0
    init {
        mBackground = ColorDrawable()
        backgroundColor = Color.parseColor("#b80f0a")
        mClearPaint = Paint()
        mClearPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_delete)
        intrinsicWidth = deleteDrawable!!.getIntrinsicWidth()
        intrinsicHeight = deleteDrawable!!.getIntrinsicHeight()
    }


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {

        return false
    }



    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top


        // Draw the red delete background
        mBackground!!.color = backgroundColor
        mBackground!!.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        mBackground!!.draw(c)

    }


}