package com.example.valu_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.valu_store.R
import com.example.valu_store.databinding.FragmentListBinding
import com.example.valu_store.viewmodel.List_viewmodel


class List_Fragment : Fragment() {

    lateinit var binding:FragmentListBinding
    val model by viewModels<List_viewmodel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.handle_observer(this@List_Fragment,binding)
        model.handlui()
    }

    override fun onResume() {
        super.onResume()
        model.callapi()
    }

    override fun onDetach() {
        super.onDetach()
        model.callapi()
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            List_Fragment().apply {}
    }
}