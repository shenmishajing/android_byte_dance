package com.zwh.android_byte_dance.ui

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zwh.android_byte_dance.NoteOperator
import com.zwh.android_byte_dance.R
import com.zwh.android_byte_dance.beans.Note
import com.zwh.android_byte_dance.beans.State
import java.text.SimpleDateFormat
import java.util.*

class NoteViewHolder(itemView: View, operator: NoteOperator) : RecyclerView.ViewHolder(itemView) {
    private val SIMPLE_DATE_FORMAT = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH)

    private val operator = operator

    private var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
    private var contentText: TextView = itemView.findViewById(R.id.text_content)
    private var dateText: TextView = itemView.findViewById(R.id.text_date)
    private var deleteBtn: ImageButton = itemView.findViewById(R.id.btn_delete)

    fun bind(note: Note) {
        contentText.setText(note.content)
        dateText.text = SIMPLE_DATE_FORMAT.format(note.date)

        checkBox.setOnCheckedChangeListener(null)
        checkBox.isChecked = note.state === State.DONE
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            note.state = (if (isChecked) State.DONE else State.TODO)
            operator.updateNote(note)
        }
        deleteBtn.setOnClickListener { operator.deleteNote(note) }

        if (note.state === State.DONE) {
            contentText.setTextColor(Color.GRAY)
            contentText.paintFlags = contentText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            contentText.setTextColor(Color.BLACK)
            contentText.paintFlags = contentText.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        itemView.setBackgroundColor(note.priority!!.color)
    }
}