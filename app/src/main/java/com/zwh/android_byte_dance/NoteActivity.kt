package com.zwh.android_byte_dance

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.zwh.android_byte_dance.db.TodoDbHelper
import com.zwh.android_byte_dance.db.TodoContract.TodoNote
import kotlinx.android.synthetic.main.activity_note.*
import android.widget.Toast
import com.zwh.android_byte_dance.beans.Priority
import com.zwh.android_byte_dance.beans.State

class NoteActivity : AppCompatActivity() {
    private var dbHelper: TodoDbHelper? = null
    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setTitle(R.string.take_a_note)

        dbHelper = TodoDbHelper(this)
        database = dbHelper!!.writableDatabase

        edit_text.isFocusable = true
        edit_text.requestFocus()

        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)?.showSoftInput(edit_text, 0)

        btn_low.isChecked = true

        btn_add.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var content = edit_text.text
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(this@NoteActivity, "No content to add", Toast.LENGTH_LONG).show()
                    return
                }

                if (saveNote2Database(content.toString().trim(), getSelectedPriority())) {
                    Toast.makeText(this@NoteActivity, "Note added", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                } else {
                    Toast.makeText(this@NoteActivity, "Error", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        database = null
        dbHelper?.close()
        dbHelper = null
    }

    private fun saveNote2Database(content: String, priority: Priority): Boolean {
        if (database == null || TextUtils.isEmpty(content)) {
            return false
        }
        val values = ContentValues()
        values.put(TodoNote.COLUMN_CONTENT, content)
        values.put(TodoNote.COLUMN_STATE, State.TODO.intValue)
        values.put(TodoNote.COLUMN_DATE, System.currentTimeMillis())
        values.put(TodoNote.COLUMN_PRIORITY, priority.intValue)
        val rowId = database!!.insert(TodoNote.TABLE_NAME, null, values)
        return !rowId.equals((-1))
    }

    private fun getSelectedPriority(): Priority {
        return when (radio_group.checkedRadioButtonId) {
            R.id.btn_high -> Priority.High
            R.id.btn_medium -> Priority.Medium
            else -> Priority.Low
        }
    }
}
