package com.example.finmag

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

class Repository(private val entryDao: EntryDao) {
    val entries: Flow<List<Entry>> = entryDao.getAll()
    val income: Flow<Float> = entryDao.getIncome()
    val expenses: Flow<Float> = entryDao.getExpenses()

    fun getEntry(id: Int) : Flow<Entry> {
        return entryDao.getEntry(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(entry: Entry) {
        entryDao.insert(entry)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(entry: Entry) {
        entryDao.update(entry)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(entry: Entry) {
        entryDao.delete(entry)
    }
}