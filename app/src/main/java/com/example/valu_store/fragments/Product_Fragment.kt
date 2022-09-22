package com.example.valu_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.valu_store.Bean.Product
import com.example.valu_store.R
import com.example.valu_store.databinding.FragmentListBinding
import com.example.valu_store.databinding.FragmentProductBinding
import com.example.valu_store.viewmodel.List_viewmodel
import com.example.valu_store.viewmodel.Product_viewmodel


class Product_Fragment : Fragment() {
    lateinit var binding:FragmentProductBinding
    val model by viewModels<Product_viewmodel>()
    lateinit var product:Product
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.handleui(product,requireActivity(),binding)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Product) =
            Product_Fragment().apply {

               product=param1


            }
    }
}