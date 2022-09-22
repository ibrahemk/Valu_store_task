package com.example.valu_store

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.valu_store.fragments.List_Fragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, List_Fragment.newInstance(), "home").commit()

    }
}