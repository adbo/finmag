package com.example.finmag

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class AddEditActivity : AppCompatActivity() {
    val ENTRY_ID = "entry_id"
    lateinit var shareInfo: String

    private val viewModel: EntryViewModel by viewModels {
        ViewModelFactory((application as EntryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        val actionbar = supportActionBar
        actionbar!!.title = "Add/Update Entry"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val spinnerType: Spinner = findViewById(R.id.spinnerType)
        val etCategory: EditText = findViewById(R.id.etCategory)
        val etAmount: EditText = findViewById(R.id.etAmount)
        val etPlace: EditText = findViewById(R.id.etPlace)
        val etDate: EditText = findViewById(R.id.etDate)

        ArrayAdapter.createFromResource(
            this,
            R.array.type,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerType.adapter = adapter
        }

        etDate.setText(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(System.currentTimeMillis()))
        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            etDate.setText(sdf.format(cal.time))

        }
        etDate.setOnClickListener {
            DatePickerDialog(this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val id = intent.getIntExtra(ENTRY_ID, 0)

        var entry: Entry? = null

        if (id > 0) {
            viewModel.getEntry(id).observe(this) { item ->
                item.let { entry = it }
                spinnerType.setSelection(entry!!.type)
                etCategory.setText(entry!!.category)
                val amountFormat = DecimalFormat("##.##")
                etAmount.setText(amountFormat.format(entry!!.amount))
                etPlace.setText(entry!!.place)
                etDate.setText(SimpleDateFormat("dd.MM.yyyy", Locale.US).format(entry!!.date))
            }
        }

        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(etCategory.text) || TextUtils.isEmpty(etAmount.text) || TextUtils.isEmpty(etPlace.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val type = spinnerType.selectedItemPosition
                val category = etCategory.text.toString()
                val amount = etAmount.text.toString().toBigDecimal()
                val place = etPlace.text.toString()
                val date = SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(etDate.text.toString())
                if (entry != null) {
                    val updatedEntry = Entry(entry!!.uid, type, category, amount, place, date)
                    viewModel.update(updatedEntry)
                } else {
                    val newEntry = Entry(0, type, category, amount, place, date)
                    viewModel.insert(newEntry)
                }
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        shareInfo = "" + etCategory.text + " " +
                etAmount.text + " " +
                etPlace.text + " " +
                etDate.text + " "
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_share -> shareIntent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareIntent() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, shareInfo)
        startActivity(intent)
    }
}