package com.manuflowers.photoInspiration.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.manuflowers.photoInspiration.R
import com.manuflowers.photoInspiration.data.model.PhotoEntity

class PhotosAdapter :
    PagingDataAdapter<PhotoEntity, PhotosViewHolder>(ARTICLES_COMPARATOR) {

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.photos_view_holder, parent, false)

        return PhotosViewHolder(view)
    }

    companion object {
        val ARTICLES_COMPARATOR = object : DiffUtil.ItemCallback<PhotoEntity>() {
            override fun areItemsTheSame(
                oldItem: PhotoEntity,
                newItem: PhotoEntity
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: PhotoEntity,
                newItem: PhotoEntity
            ): Boolean = oldItem == newItem

        }
    }
}