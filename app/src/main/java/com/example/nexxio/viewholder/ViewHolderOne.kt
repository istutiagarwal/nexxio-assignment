package com.example.nexxio.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.nexxio.data.NexxioData
import com.example.nexxio.databinding.CameraBinding


class PhotoViewHolder(private val photoBinding: CameraBinding) : BaseViewHolder(photoBinding.root) {
    private val cameraText: AppCompatTextView = photoBinding.cameraTitle
    private val image: AppCompatImageView = photoBinding.cameraImageOne
    private val cancelImage: AppCompatImageView = photoBinding.cameraCancelImage

    override fun bind(data: NexxioData) {
        cameraText.text = data.title
        listener?.getId(data.id, adapterPosition)
        if (data.imagePath == null) cancelImage.visibility = View.GONE
        data.imagePath?.let {
            image.setImageURI(it)
            cancelImage.visibility = View.VISIBLE
        }
        cancelImage.setOnClickListener {
            image.visibility = View.VISIBLE
            listener?.onItemClick(data, adapterPosition)
        }
        photoBinding.cameraLayout.setOnClickListener {
            listener?.onItemClick(data, adapterPosition)

        }
        if (data.imagePath != null) {
            image.setOnClickListener {
                listener?.onImageClick(data, adapterPosition)
            }
        }
    }


}




