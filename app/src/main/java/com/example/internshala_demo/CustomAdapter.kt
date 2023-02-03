package com.example.internshala_demo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val context: Context?,
                    private val mList: ArrayList<String>,
                    private val cellClickListener: CellClickListener
                    ): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        holder.Button.setOnClickListener{
            cellClickListener.onCellClickListener(data,position)
        }

        // sets the text to the textview from our itemHolder class
        holder.textView.text = mList[position]
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val Button: Button = itemView.findViewById(R.id.apply)
        val textView: TextView = itemView.findViewById(R.id.course)
    }


}