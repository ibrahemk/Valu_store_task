package com.example.valu_store.Interface

import androidx.fragment.app.FragmentActivity
import com.example.valu_store.Bean.Product
import com.example.valu_store.databinding.FragmentProductBinding

interface Product_interface {
    fun handleui(product: Product,activity: FragmentActivity,binding: FragmentProductBinding)
    fun setimage(product: Product, activity: FragmentActivity, binding: FragmentProductBinding)
}