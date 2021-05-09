package com.example.finmag

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.util.*


class EntryViewModel(private val repository: Repository) : ViewModel() {

    val entries: LiveData<List<Entry>> = repository.entries.asLiveData()
    val income: LiveData<Float> = repository.income.asLiveData()
    val expenses: LiveData<Float> = repository.expenses.asLiveData()

    fun getEntry(id: Int) : LiveData<Entry> {
        return repository.getEntry(id).asLiveData()
    }

    fun insert(entry: Entry) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun update(entry: Entry) = viewModelScope.launch {
        repository.update(entry)
    }

    fun delete(entry: Entry) = viewModelScope.launch {
        repository.delete(entry)
    }
}

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}