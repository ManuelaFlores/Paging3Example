package com.manuflowers.quicknews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

//https://newsapi.org/v2/everything?q=cine&page=3&apiKey=a2e34e7403e44a66ba0c2784cf5f8b3c