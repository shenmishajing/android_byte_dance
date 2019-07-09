package com.zwh.android_byte_dance

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val items: List<Data>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent.context, parent)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        if (position > 2) {
            holder.no.setTextColor(Color.parseColor("#99ffffff"))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var no = itemView.findViewById<TextView>(R.id.no)
        var title = itemView.findViewById<TextView>(R.id.title)
        var number = itemView.findViewById<TextView>(R.id.number)

        companion object {
            fun create(context: Context, root: ViewGroup): ViewHolder {
                var v = LayoutInflater.from(context).inflate(R.layout.list_recycle, root, false)
                return ViewHolder(v)
            }
        }

        fun bind(data: Data?) {
            if (data != null) {
                title.setText(data.title)
                no.setText(data.no.toString() + ".")
                number.setText(data.number.toString())
            }
        }
    }
}