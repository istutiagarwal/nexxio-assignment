package com.example.nexxio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nexxio.R
import com.example.nexxio.data.NexxioData
import com.example.nexxio.databinding.CameraBinding
import com.example.nexxio.databinding.CommentBinding
import com.example.nexxio.databinding.RvChoiceBinding
import com.example.nexxio.viewholder.BaseViewHolder
import com.example.nexxio.viewholder.ChoiceViewHolder
import com.example.nexxio.viewholder.CommentViewHolder
import com.example.nexxio.viewholder.OnItemClickListener
import com.example.nexxio.viewholder.PhotoViewHolder

class BaseAdapter() : RecyclerView.Adapter<BaseViewHolder>() {

    private var list = mutableListOf<NexxioData>()
    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            1 -> {
                val binding: CameraBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.camera,
                    parent,
                    false
                )
                return PhotoViewHolder(binding).apply {
                    setOnItemClickListener(listener)
                }
            }

            2 -> {
                val binding: RvChoiceBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.rv_choice,
                    parent,
                    false
                )
                return ChoiceViewHolder(binding).apply {
                    setOnItemClickListener(listener)
                }
            }

            else -> {
                val binding: CommentBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.comment,
                    parent,
                    false
                )
                return CommentViewHolder(binding).apply {
                    setOnItemClickListener(listener)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {

        val item = list[position]
        return when (item.type) {
            "PHOTO" -> 1
            "SINGLE_CHOICE" -> 2
            else -> 3
        }
    }

    fun updateItem(data: NexxioData, position: Int) {
        list[position] = data
        notifyItemChanged(position)
    }

    fun setItems(list: List<NexxioData>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}