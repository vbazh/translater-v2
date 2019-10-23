package com.vbazh.words.favorite.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.data.local.entity.TranslateEntity
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoriteAdapter(
    private val clickListener: (TranslateEntity) -> Unit,
    private val deleteListener: (TranslateEntity) -> Unit
) : ListAdapter<TranslateEntity, FavoriteViewHolder>(FavoriteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteViewHolder(inflater.inflate(R.layout.item_favorite, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, deleteListener)
    }
}

class FavoriteDiffCallback : DiffUtil.ItemCallback<TranslateEntity>() {
    override fun areItemsTheSame(oldItem: TranslateEntity, newItem: TranslateEntity): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: TranslateEntity, newItem: TranslateEntity): Boolean {
        return oldItem.source == newItem.source
    }
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(
        translate: TranslateEntity,
        clickListener: (TranslateEntity) -> Unit,
        deleteListener: (TranslateEntity) -> Unit
    ) {
        itemView.sourceText.text = translate.source
        itemView.targetText.text = translate.target
        itemView.setOnClickListener { clickListener(translate) }
        itemView.delete.setOnClickListener { deleteListener(translate) }
    }
}