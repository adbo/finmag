package com.example.finmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val viewModel: EntryViewModel by viewModels {
        ViewModelFactory((application as EntryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvList: RecyclerView = findViewById(R.id.rvList)
        val fabAddEdit: FloatingActionButton = findViewById(R.id.fabAddEdit)
        val tvIncome: TextView = findViewById(R.id.tvIncome)
        val tvExpenses: TextView = findViewById(R.id.tvExpenses)

        val entriesAdapter = DataAdapter()
        rvList.adapter = entriesAdapter

        entriesAdapter.onItemClick = { entry ->
            viewModel.delete(entry)
        }

        viewModel.entries.observe(this) { entries ->
            // Update the cached copy of the words in the adapter.
            entries.let { entriesAdapter.submitList(it) }
        }

        viewModel.income.observe(this) { income ->
            // Update the cached copy of the words in the adapter.
            income.let { tvIncome.text = "%.2f".format(it/100)}
        }

        viewModel.expenses.observe(this) { expenses ->
            // Update the cached copy of the words in the adapter.
            expenses.let { tvExpenses.text = "%.2f".format(it/100) }
        }

        val intent = Intent(this, AddEditActivity::class.java)
        fabAddEdit.setOnClickListener { view -> startActivity(intent) }
    }
}