package com.zwh.android_byte_dance.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zwh.android_byte_dance.NoteOperator
import com.zwh.android_byte_dance.R
import com.zwh.android_byte_dance.beans.Note
import java.util.ArrayList

class NoteListAdapter(operator: NoteOperator) : RecyclerView.Adapter<NoteViewHolder>() {

    private val operator = operator
    private val notes = ArrayList<Note>()


    fun refresh(newNotes: List<Note>?) {
        notes.clear()
        if (newNotes != null) {
            notes.addAll(newNotes)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(itemView, operator)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, pos: Int) {
        holder.bind(notes.get(pos))
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}