package com.example.valu_store.Interface

import androidx.fragment.app.FragmentActivity
import com.example.valu_store.Bean.Product
import com.example.valu_store.MainActivity
import com.example.valu_store.databinding.FragmentListBinding
import com.example.valu_store.fragments.List_Fragment

interface List_interface {
    fun callapi()
    fun cancel_api()
    fun handle_observer(fclass: List_Fragment,binding: FragmentListBinding)
    fun handlui()
    fun parse_result(result:String):MutableList<Product>?
    fun fire_api(url:String):String
    fun setadapter(list:MutableList<Product>)


}