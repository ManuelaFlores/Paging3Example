package com.manuflowers.photoInspiration.ui.main.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.manuflowers.photoInspiration.data.model.PhotoEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.photos_view_holder.view.*

class PhotosViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(photoEntity: PhotoEntity) = with(containerView) {
        newsTitleTextView.text = photoEntity.userName
        Glide.with(this)
            .load(photoEntity.smallUrl)
            .centerCrop()
            .into(newsImageView)
    }

}