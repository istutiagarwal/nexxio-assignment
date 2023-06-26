package com.example.nexxio.viewholder

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import com.example.nexxio.data.NexxioData
import com.example.nexxio.databinding.CommentBinding
import com.google.android.material.textfield.TextInputEditText

class CommentViewHolder(private val commentBinding: CommentBinding) :
    BaseViewHolder(commentBinding.root) {
    private val textView: AppCompatTextView = commentBinding.commentTitle
    private val switch: SwitchCompat = commentBinding.commentToggleButton
    private val textInput: TextInputEditText = commentBinding.editText

    private var commentText: String = ""
    override fun bind(data: NexxioData) {
        textView.text = data.title
        textInput.visibility = View.GONE
        listener?.getId(data.id, adapterPosition)
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                textInput.visibility = View.VISIBLE
            } else {
                textInput.visibility = View.GONE
            }
        }
        textInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                commentText = s.toString()
                listener?.getComment(data.id, commentText, adapterPosition)
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }
}
