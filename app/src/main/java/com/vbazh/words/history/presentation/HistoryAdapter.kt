package com.vbazh.words.history.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.data.local.entity.TranslateEntity
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(
    private val clickListener: (TranslateEntity) -> Unit,
    private val deleteListener: (TranslateEntity) -> Unit,
    private val favoriteListener: (TranslateEntity) -> Unit
) :
    ListAdapter<TranslateEntity, HistoryViewHolder>(HistoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HistoryViewHolder(inflater.inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener, deleteListener, favoriteListener)
    }
}

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        translate: TranslateEntity,
        clickListener: (TranslateEntity) -> Unit,
        deleteListener: (TranslateEntity) -> Unit,
        favoriteListener: (TranslateEntity) -> Unit
    ) {
        itemView.sourceText.text = translate.source
        itemView.targetText.text = translate.target

        if (translate.isFavorite == 1) {
            itemView.favorite.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_black_24dp))
        } else {
            itemView.favorite.setImageDrawable(itemView.context.getDrawable(R.drawable.ic_star_border_black_24dp))
        }
        itemView.setOnClickListener { clickListener(translate) }
        itemView.delete.setOnClickListener { deleteListener(translate) }
        itemView.favorite.setOnClickListener { favoriteListener(translate) }
    }
}

class HistoryDiffCallback : DiffUtil.ItemCallback<TranslateEntity>() {
    override fun areItemsTheSame(oldItem: TranslateEntity, newItem: TranslateEntity): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: TranslateEntity, newItem: TranslateEntity): Boolean {
        return oldItem.source == newItem.source && oldItem.isFavorite == newItem.isFavorite
    }
}
