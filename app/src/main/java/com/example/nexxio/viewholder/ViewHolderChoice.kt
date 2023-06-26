package com.example.nexxio.viewholder

import android.view.View
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.example.nexxio.R
import com.example.nexxio.data.NexxioData

class ViewHolderChoice(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val radioButton : RadioButton = itemView.findViewById(R.id.radio_one)

     fun bind(data: NexxioData) {

    }
}