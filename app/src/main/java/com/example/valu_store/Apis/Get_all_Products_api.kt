package com.example.valu_store.Apis

import com.example.valu_store.Helper.CheckInternetConnection
import com.example.valu_store.Retrofitapis.AsyncRq
import com.example.valu_store.viewmodel.List_viewmodel

class Get_all_Products_api(var model:List_viewmodel):AsyncRq<String,Any>() {
    var internetCheck: Boolean = false
    override fun onPreExecute() {
        super.onPreExecute()
        model.loading.value=true
        internetCheck = CheckInternetConnection(model.activity).haveNetworkConnection()
    }
    override fun doInBackground(vararg Param: Any): String {
       if (internetCheck){
return model.fire_api("https://fakestoreapi.com/products")
       }
        return ""
    }

    override fun onPostExecute(result: String) {


        model.loading.value=false
        if (result.isNotEmpty()&&result.isNotBlank()){
          model.productslist.value=  model.parse_result(result)
        }

    }
}