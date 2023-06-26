package com.example.nexxio.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.nexxio.data.NexxioData

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    abstract fun bind(data: NexxioData)
}

interface OnItemClickListener {
    fun onItemClick(data: NexxioData, position: Int)
    fun getId(data: String, position: Int)
    fun getChoice(id: String, data: String, position: Int)
    fun getComment(id: String, data: String, position: Int)
    fun onImageClick(data: NexxioData, position: Int)
}
