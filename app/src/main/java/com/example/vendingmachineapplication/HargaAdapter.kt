package com.example.vendingmachineapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vendingmachineapplication.databinding.ItemsRowBinding

class HargaAdapter(private val listData: ArrayList<DaftarHarga>,
                   private val onItemClick: (id: Int) -> Unit) :
    RecyclerView.Adapter<HargaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemsRowBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])

        if (position % 2 == 0){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.light_grey))
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class ViewHolder(private val binding: ItemsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DaftarHarga) {
            binding.tvRowName.text = item.title
            binding.tvRowHarga.text = "Rp. ${item.harga}"

            binding.root.setOnClickListener {
                onItemClick(item.id)
            }

            binding.ivBtnDelete.setOnClickListener {
                removeItemById(item.id)
            }
        }
    }

    fun getItemById(id: Int): DaftarHarga? {
        for (i in listData.indices) {
            if (listData[i].id == id) {
                return listData[i]
            }
        }
        return null
    }

    fun removeItemById(id: Int) {
        for (i in listData.indices) {
            if (listData[i].id == id) {
                listData.removeAt(i)
                notifyItemRemoved(i)
                break
            }
        }
    }

    fun addItem(daftarHarga: DaftarHarga) {
        this.listData.add(daftarHarga)
        notifyItemInserted(this.listData.size - 1)
    }

    fun getTotalHarga(): Int {
        var totalHarga = 0
        for (item in listData) {
            totalHarga += item.harga.toInt()
        }
        return totalHarga
    }
}
