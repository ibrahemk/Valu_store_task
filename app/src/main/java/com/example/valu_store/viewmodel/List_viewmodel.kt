package com.example.valu_store.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.valu_store.Adapter.Products_adapter
import com.example.valu_store.Apis.Get_all_Products_api
import com.example.valu_store.Bean.Product
import com.example.valu_store.Interface.List_interface
import com.example.valu_store.Retrofitapis.AsyncRq
import com.example.valu_store.Retrofitapis.RF_Requests
import com.example.valu_store.databinding.FragmentListBinding
import com.example.valu_store.fragments.List_Fragment
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONArray
import javax.inject.Inject
@HiltViewModel
class List_viewmodel @Inject constructor() : ViewModel(),List_interface {
lateinit var activity: FragmentActivity
lateinit var binding: FragmentListBinding
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var productslist: MutableLiveData<MutableList<Product>> = MutableLiveData()
    var async:AsyncRq<String,Any>?=null
    override fun callapi() {
        async=Get_all_Products_api(this@List_viewmodel)
        async!!.execute<String>()
    }

    override fun cancel_api() {
        async.let {
            if (it!!.getStatus()==AsyncRq.Status.RUNNING){
                it.cancel()
            }
        }
    }

    override fun handle_observer(fclass: List_Fragment, binding: FragmentListBinding) {
     this.activity= fclass.requireActivity()
        this.binding=binding
        loading.observe(fclass) { show ->
            if (show) {
                binding.load.visibility = View.VISIBLE
                binding.list.visibility = View.GONE
            } else {
                binding.load.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }

        }
        productslist.observe(fclass) { list ->
            if (list.size>0) {
                setadapter(list)
            }
        }
    }

    override fun handlui() {
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        binding.list.layoutManager = mLayoutManager
        binding.list.itemAnimator = DefaultItemAnimator()
    }

    override fun parse_result(result:String):MutableList<Product>? {
        var jsonArray=JSONArray(result)
        var list :MutableList<Product> = mutableListOf()
        for (i in 0 until jsonArray.length()) {
            var product=Product()
            var json=jsonArray.getJSONObject(i)
            if (json.has("id")){
                product.id=json.getString("id")
            }
            if (json.has("title")){
                product.title=json.getString("title")
            }
            if (json.has("price")){
                product.price=json.getString("price")
            }
            if (json.has("description")){
                product.description=json.getString("description")
            }
            if (json.has("image")){
                product.image=json.getString("image")
            }
            if (json.has("rating")&&json.getJSONObject("rating").has("rate")){
                product.rate=json.getJSONObject("rating").getString("rate")
            }
            if (json.has("rating")&&json.getJSONObject("rating").has("count")){
                product.count=json.getJSONObject("rating").getString("count")
            }
            list.add(product)

        }
        return if (list.size>0) {
            list
        }else{
            null
        }
    }

    override fun fire_api(url: String):String {
        return  RF_Requests().Get_request(url)
    }

    override fun setadapter(list:MutableList<Product>) {
   binding.list.adapter=Products_adapter(list,this@List_viewmodel)
    }

}