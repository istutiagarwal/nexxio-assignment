package com.example.nexxio.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nexxio.R
import com.example.nexxio.data.NexxioData
import com.example.nexxio.data.ResultModel
import com.example.nexxio.viewholder.OnItemClickListener
import com.example.nexxio.viewholder.ViewHolderChoice

class ChoiceAdapter(private val data: NexxioData) : RecyclerView.Adapter<ViewHolderChoice>() {
    private var lastSelectedPosition = -1
    //private var resultModel : ResultModel = ResultModel("","")

    private var listener: OnItemClickListener? = null
    fun setClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChoice {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.choice, parent, false)

        return ViewHolderChoice(view)
    }

    override fun getItemCount(): Int {
        return data.dataMap.getValue("options").size
    }

    override fun onBindViewHolder(holder: ViewHolderChoice, position: Int) {
        holder.bind(data)

        val value = data.dataMap.getValue("options")[position]
        holder.radioButton.text = value
        holder.radioButton.setOnClickListener {
            lastSelectedPosition = position
            notifyDataSetChanged()
            //resultModel(data.id, value)
            listener?.getChoice(data.id, value, position)
            Log.d(
                "istuti",
                "lastSelectedPosition ${data.dataMap.getValue("options")[lastSelectedPosition]}"
            )
        }
        holder.radioButton.isChecked = lastSelectedPosition == position

    }
}


interface setClickListener {
    fun getChoice(id: String, data: String, position: Int)
}
