package com.zwh.android_byte_dance

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zwh.android_byte_dance.beans.Note
import com.zwh.android_byte_dance.beans.Priority
import com.zwh.android_byte_dance.beans.State
import com.zwh.android_byte_dance.db.TodoDbHelper
import com.zwh.android_byte_dance.db.TodoContract.TodoNote
import com.zwh.android_byte_dance.debug.DebugActivity
import com.zwh.android_byte_dance.ui.NoteListAdapter
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.content_todo.*
import java.util.*

class TodoListActivity : AppCompatActivity() {
    private val REQUEST_CODE_ADD = 1002

    private var recyclerView: RecyclerView? = null
    private var notesAdapter: NoteListAdapter? = null

    private var dbHelper: TodoDbHelper? = null
    private var database: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        setSupportActionBar(toolbar as Toolbar)

        fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivityForResult(Intent(this@TodoListActivity, NoteActivity::class.java), REQUEST_CODE_ADD)
            }
        })

        dbHelper = TodoDbHelper(this)
        database = dbHelper!!.getWritableDatabase()

        list_todo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list_todo.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        notesAdapter = NoteListAdapter(object : NoteOperator {
            override fun deleteNote(note: Note) {
                this@TodoListActivity.deleteNote(note)
            }

            override fun updateNote(note: Note) {
                this@TodoListActivity.updateNode(note)
            }
        })
        list_todo.adapter = notesAdapter

        notesAdapter!!.refresh(loadNotesFromDatabase())
    }

    override fun onDestroy() {
        super.onDestroy()
        database?.close()
        database = null
        dbHelper?.close()
        dbHelper = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_todo, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_settings -> return true
            R.id.action_debug -> {
                startActivity(Intent(this, DebugActivity::class.java))
                return true
            }
            else -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
            notesAdapter?.refresh(loadNotesFromDatabase())
        }
    }

    private fun loadNotesFromDatabase(): List<Note> {
        if (database == null) {
            return emptyList()
        }
        val result = LinkedList<Note>()
        var cursor: Cursor? = null
        try {
            cursor = database!!.query(
                TodoNote.TABLE_NAME, null, null, null, null, null,
                TodoNote.COLUMN_PRIORITY + " DESC"
            )

            while (cursor!!.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                val content = cursor.getString(cursor.getColumnIndex(TodoNote.COLUMN_CONTENT))
                val dateMs = cursor.getLong(cursor.getColumnIndex(TodoNote.COLUMN_DATE))
                val intState = cursor.getInt(cursor.getColumnIndex(TodoNote.COLUMN_STATE))
                val intPriority = cursor.getInt(cursor.getColumnIndex(TodoNote.COLUMN_PRIORITY))

                val note = Note(id)
                note.content = content
                note.date = Date(dateMs)
                note.state = State.from(intState)
                note.priority = Priority.from(intPriority)

                result.add(note)
            }
        } finally {
            cursor?.close()
        }
        return result
    }

    private fun deleteNote(note: Note) {
        if (database == null) {
            return
        }
        val rows = database!!.delete(
            TodoNote.TABLE_NAME,
            BaseColumns._ID + "=?",
            arrayOf<String>(note.id.toString())
        )
        if (rows > 0) {
            notesAdapter!!.refresh(loadNotesFromDatabase())
        }
    }

    private fun updateNode(note: Note) {
        if (database == null) {
            return
        }
        val values = ContentValues()
        values.put(TodoNote.COLUMN_STATE, note.state!!.intValue)

        val rows = database!!.update(
            TodoNote.TABLE_NAME, values,
            BaseColumns._ID + "=?",
            arrayOf<String>(note.id.toString())
        )
        if (rows > 0) {
            notesAdapter!!.refresh(loadNotesFromDatabase())
        }
    }

}
