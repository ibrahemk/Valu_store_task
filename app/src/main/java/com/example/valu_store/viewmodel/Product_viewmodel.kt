package com.example.valu_store.viewmodel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.valu_store.Bean.Product
import com.example.valu_store.Interface.Product_interface
import com.example.valu_store.databinding.FragmentProductBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class Product_viewmodel@Inject constructor() : ViewModel(),Product_interface {
    override fun handleui( product: Product, activity: FragmentActivity, binding: FragmentProductBinding) {

        binding.title.text=product.title
        binding.count.text=product.count
        binding.ratingBar.rating=product.rate.toFloat()
        binding.ratingBar.isEnabled=false

        binding.des.text=product.description
        setimage(product,activity,binding)

    }

    override fun setimage(product: Product, activity: FragmentActivity, binding: FragmentProductBinding) {
        Glide.with(activity)
            .asBitmap()
            .load(product.image)
            .into(binding.image)
    }


}