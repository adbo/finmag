package com.example.finmag

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import java.text.SimpleDateFormat

const val ENTRY_ID = "entry_id"

val viewModel: EntryViewModel? = null

class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dt: SimpleDateFormat? = SimpleDateFormat("dd.MM.yyyy")

    var place: TextView = view.findViewById(R.id.tvPlace)
    var amount: TextView = view.findViewById(R.id.tvAmount)
    var date: TextView = view.findViewById(R.id.tvDate)
    var category: TextView = view.findViewById(R.id.tvCategory)

    fun bind(entry: Entry, onItemClick: ((Entry) -> Unit)?) {
        place.text = entry.place
        amount.text = entry.amount.toString()
        date.text = dt!!.format(entry.date)
        category.text = entry.category

        val intent = Intent(itemView.context, AddEditActivity::class.java).apply {
            putExtra(ENTRY_ID, entry.uid)
        }
        itemView.setOnClickListener { view -> view.context.startActivity(intent) }
        itemView.setOnLongClickListener { view -> AlertDialog.Builder(view.context)
            .setTitle(R.string.question_title)
            .setMessage(R.string.question_message)
            .setPositiveButton(R.string.yes) { _, _ -> onItemClick?.invoke(entry) }
            .setNegativeButton(R.string.no) { _, _ -> return@setNegativeButton }
            .show()
            return@setOnLongClickListener true
        }
    }
}

fun deleteEntry(entry: Entry) {

}

class DataAdapter : ListAdapter<Entry, DataViewHolder>(EntriesComparator()) {
    var onItemClick: ((Entry) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClick)
    }
}

class EntriesComparator : DiffUtil.ItemCallback<Entry>() {
    override fun areItemsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Entry, newItem: Entry): Boolean {
        return oldItem.type == newItem.type && oldItem.category == newItem.category &&
                oldItem.amount == newItem.amount && oldItem.place == newItem.place &&
                oldItem.date == newItem.date
    }
}