package com.example.valu_store.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.valu_store.Bean.Product
import com.example.valu_store.Viewholder.Product_viewholder
import com.example.valu_store.databinding.ProductRowBinding
import com.example.valu_store.viewmodel.List_viewmodel

class Products_adapter(var list:MutableList<Product>,var model:List_viewmodel): RecyclerView.Adapter<Product_viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Product_viewholder {
        val binding = ProductRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Product_viewholder(binding, model)
    }

    override fun onBindViewHolder(holder: Product_viewholder, position: Int) {
  holder.handlerow(list[position])
    }

    override fun getItemCount(): Int {
   return list.size
    }
}