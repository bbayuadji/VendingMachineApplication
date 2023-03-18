package com.example.vendingmachineapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(inflater: LayoutInflater, parent: ViewGroup):
    RecyclerView.ViewHolder(inflater.inflate(R.layout.items_row,parent,false)) {
    private var title: TextView? = null
    private var harga: TextView? = null

    init {
        title = itemView.findViewById(R.id.tvRowName)
        harga = itemView.findViewById(R.id.tvRowHarga)
    }

    fun bind(data: DaftarHarga){
        title?.text = data.title
        harga?.text = data.harga
    }

}