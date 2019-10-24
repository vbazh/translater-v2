package com.vbazh.words.languages.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vbazh.words.R
import com.vbazh.words.data.local.entity.LanguageEntity
import kotlinx.android.synthetic.main.item_language.view.*

class LanguageAdapter(private val clickListener: (LanguageEntity) -> Unit) :
    ListAdapter<LanguageEntity, LanguageViewHolder>(LanguageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LanguageViewHolder(inflater.inflate(R.layout.item_language, parent, false))
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}

class LanguageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(language: LanguageEntity, clickListener: (LanguageEntity) -> Unit) {
        itemView.languageName.text = language.name
        itemView.setOnClickListener { clickListener(language) }
    }
}

class LanguageDiffCallback : DiffUtil.ItemCallback<LanguageEntity>() {
    override fun areItemsTheSame(oldItem: LanguageEntity, newItem: LanguageEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LanguageEntity, newItem: LanguageEntity): Boolean {
        return oldItem == newItem
    }
}