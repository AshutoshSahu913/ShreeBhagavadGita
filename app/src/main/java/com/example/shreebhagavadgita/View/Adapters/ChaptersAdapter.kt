package com.example.shreebhagavadgita.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shreebhagavadgita.DataSource.Models.ChaptersItem
import com.example.shreebhagavadgita.R
import com.example.shreebhagavadgita.databinding.ChapterListItemsBinding

class ChaptersAdapter(
    val onClickedChapter: ((ChaptersItem?) -> Unit)?,
    val onFavClicked: ((ChaptersItem?) -> Unit)?,
    val showFavBtn: Boolean,
    val onFavClickedNot: ((ChaptersItem?) -> Unit)?
) :
    RecyclerView.Adapter<ChaptersAdapter.viewHolder>() {

    //show the list compare the old and new data in recyclerview
    //manage updates to RecyclerView adapters. RecyclerView is a UI component used in Android to efficiently display large sets of data.
    var diffUtil = object : DiffUtil.ItemCallback<ChaptersItem>() {
        override fun areItemsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {

            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChaptersItem, newItem: ChaptersItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    inner class viewHolder(var binding: ChapterListItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {

            var chapter = differ.currentList[position]

            if (!showFavBtn) {
                binding.saveBtn.visibility = View.GONE
            }
            binding.apply {

                tvChapterNumber.text = "Chapter ${chapter.chapter_number}"
                tvChapterTitle.text = chapter.name_translated
                tvChapterDes.text = chapter.chapter_summary
                tvVerseCount.text = chapter.verses_count.toString()
            }

            binding.itemClick.setOnClickListener {
                onClickedChapter?.invoke(chapter)
            }
            binding.apply {
                var isSaved = false
                saveBtn.setOnClickListener {
                    isSaved = if (!isSaved) {
                        saveBtn.setImageResource(R.drawable.baseline_favorite_24)
                        onFavClicked?.invoke(chapter)
                        true
                    } else {
                        saveBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                        onFavClickedNot?.invoke(chapter)
                        false
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersAdapter.viewHolder {
        val binding =
            ChapterListItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return viewHolder(binding)


    }

    override fun onBindViewHolder(holder: ChaptersAdapter.viewHolder, position: Int) {
        holder.bind(position)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}