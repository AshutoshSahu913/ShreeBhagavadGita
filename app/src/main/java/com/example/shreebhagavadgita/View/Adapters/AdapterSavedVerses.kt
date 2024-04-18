package com.example.shreebhagavadgita.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shreebhagavadgita.DataSource.Room.SavedVersesEntity
import com.example.shreebhagavadgita.databinding.VersesListItemsBinding

class AdapterSavedVerses(val onVersesItemClicked: (SavedVersesEntity) -> Unit) :
    RecyclerView.Adapter<AdapterSavedVerses.viewHolder>() {
    private var diffUtil = object : DiffUtil.ItemCallback<SavedVersesEntity>() {
        override fun areItemsTheSame(
            oldItem: SavedVersesEntity, newItem: SavedVersesEntity
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: SavedVersesEntity, newItem: SavedVersesEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class viewHolder(private var binding: VersesListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val verses = differ.currentList[position]
            binding.apply {
                verseNumber.text = "Verse ${verses.chapter_number}.${verses.verse_number}"
                verseDes.text = verses.translations[0].description

                versesItemClick.setOnClickListener {
                    onVersesItemClicked(verses)
                }
            }
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AdapterSavedVerses.viewHolder {
        val binding =
            VersesListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterSavedVerses.viewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}