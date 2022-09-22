package com.example.valu_store.Viewholder

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.valu_store.Bean.Product
import com.example.valu_store.R
import com.example.valu_store.databinding.ProductRowBinding
import com.example.valu_store.fragments.Product_Fragment
import com.example.valu_store.viewmodel.List_viewmodel

class Product_viewholder (val binding: ProductRowBinding, val model:List_viewmodel)
    : RecyclerView.ViewHolder(binding.root){

        fun handlerow(product: Product){
            binding.title.text=product.title
            binding.cost.text=product.price
            setimage(product)
            binding.row.setOnClickListener { open_product(product) }

        }
    fun setimage(product: Product) {


        Glide.with(model.activity)
            .asBitmap()
            .load(product.image)
            .into(binding.image)

    }
     fun open_product(product: Product) {
        model.activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, Product_Fragment.newInstance(product), "product")
            .addToBackStack("product").commit()
    }
}