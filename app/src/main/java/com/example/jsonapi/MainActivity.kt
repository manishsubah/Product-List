package com.example.jsonapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        recyclerView = findViewById<RecyclerView>(R.id.recycleview)

        val retrofitbuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppInterface::class.java)

        val retrofitData = retrofitbuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                //if api call is success then use the data of API and show in your app
                val responseBody = response.body() // mydata class variables
                val productList = responseBody?.products!!

//                val collectDatainStringBuider = StringBuffer()
//                for(myData in productList) {
//                    collectDatainStringBuider.append(myData.title+" ")
//                }
//                val tv = findViewById<TextView>(R.id.textview)
//                tv.text = collectDatainStringBuider

                myAdapter = MyAdapter(this@MainActivity, productList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // if api fails, then
                Log.d("Main Activity", "onFailure" + t.message)
            }
        })
    }
}