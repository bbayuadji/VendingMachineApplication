package com.example.vendingmachineapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vendingmachineapplication.databinding.ActivityMainBinding
import com.example.vendingmachineapplication.databinding.DialogCustomBayarBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HargaAdapter
    private lateinit var dialogBinding: DialogCustomBayarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.rvVM)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = HargaAdapter(ArrayList()) { id ->
            adapter.removeItemById(id)
        }
        recyclerView.adapter = adapter

        binding.llAqua.setOnClickListener {
            adapter.addItem(DaftarHarga(1,"Aqua", "100"))
        }
        binding.llTehBotol.setOnClickListener {
            adapter.addItem(DaftarHarga(2,"Teh Botol", "50"))
        }
        binding.llSprite.setOnClickListener {
            adapter.addItem(DaftarHarga(3, "Sprite", "20"))
        }
        binding.llFanta.setOnClickListener {
            adapter.addItem(DaftarHarga(4,"Fanta", "10"))
        }
        binding.llPepsi.setOnClickListener {
            adapter.addItem(DaftarHarga(5,"Pepsi", "5"))
        }
        binding.llCoke.setOnClickListener {
            adapter.addItem(DaftarHarga(6,"Coke", "1"))
        }

        binding.btBayar.setOnClickListener {
            customDialogBayar(adapter.getTotalHarga())
        }
    }

    private fun customDialogBayar(totalHarga: Int) {
        val customDialog = Dialog(this)
        dialogBinding = DialogCustomBayarBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.tvTotalTagihan.text = "Rp. $totalHarga"

        dialogBinding.tvKembalian.visibility = View.GONE
        dialogBinding.btCdOk.visibility = View.GONE
        dialogBinding.tvTotalKembalian.visibility = View.GONE

        var totalBayar = 0

        dialogBinding.tvUang10.setOnClickListener {
            val uang10 = dialogBinding.tvUang10.text.toString().replace("Rp.", "").toIntOrNull() ?: 0
            totalBayar += uang10
        }

        dialogBinding.tvUang20.setOnClickListener {
            val uang20 = dialogBinding.tvUang20.text.toString().replace("Rp.", "").toIntOrNull() ?: 0
            totalBayar += uang20
        }

        dialogBinding.tvUang50.setOnClickListener {
            val uang50 = dialogBinding.tvUang50.text.toString().replace("Rp.", "").toIntOrNull() ?: 0
            totalBayar += uang50
        }

        dialogBinding.tvUang100.setOnClickListener {
            val uang100 = dialogBinding.tvUang100.text.toString().replace("Rp.", "").toIntOrNull() ?: 0
            totalBayar += uang100
        }

        dialogBinding.btCdBayar.setOnClickListener {

            if (totalBayar < totalHarga) {
                Toast.makeText(this, "Pembayaran kurang", Toast.LENGTH_SHORT).show()
            }else if (totalBayar == totalHarga){
                Toast.makeText(this, "Uang pas", Toast.LENGTH_SHORT).show()
            } else {
                dialogBinding.tvKembalian.visibility = View.VISIBLE
                dialogBinding.btCdOk.visibility = View.VISIBLE
                dialogBinding.tvTotalKembalian.visibility = View.VISIBLE

                val kembalianMap = hitungKembalian(totalBayar, totalHarga)
                val pecahan = kembalianMap.entries.joinToString(" , ") { "Rp. ${it.key} x ${it.value}" }
                dialogBinding.tvTotalKembalian.text = pecahan
            }
        }

        dialogBinding.btCdOk.setOnClickListener{
            customDialog.dismiss()
            Toast.makeText(this, "Selamat Menikmati", Toast.LENGTH_LONG).show()
            recreate()
        }
        customDialog.show()
    }

    fun hitungKembalian(totalBayar: Int, totalHarga: Int): Map<Int, Int> {
        val denominasi = listOf(100, 50, 20, 10, 5, 1)
        val kembalian = totalBayar - totalHarga
        var sisa = kembalian
        val kembalianMap = mutableMapOf<Int, Int>()

        for (denom in denominasi) {
            if (sisa < denom) {
                continue
            }
            val jumlahDenom = sisa / denom
            sisa %= denom
            if (jumlahDenom > 0) {
                kembalianMap[denom] = jumlahDenom
            }
            if (sisa == 0) {
                break
            }
        }
        return kembalianMap
    }

}
