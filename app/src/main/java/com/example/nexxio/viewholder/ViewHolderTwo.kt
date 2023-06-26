package com.example.nexxio.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.nexxio.adapter.ChoiceAdapter
import com.example.nexxio.data.NexxioData
import com.example.nexxio.databinding.RvChoiceBinding

class ChoiceViewHolder(private val choiceBinding: RvChoiceBinding) :
    BaseViewHolder(choiceBinding.root) {
    private val rvChoice: RecyclerView = choiceBinding.recyclerviewChoice

    override fun bind(data: NexxioData) {
        rvChoice.adapter = ChoiceAdapter(data).apply {
            setClickListener(listener)
        }
    }
}
