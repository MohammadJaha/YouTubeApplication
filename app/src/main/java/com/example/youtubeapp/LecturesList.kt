package com.example.youtubeapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class LecturesList : AppCompatActivity(){

    private lateinit var sonic2Image : ImageView
    private lateinit var toDoLay : LinearLayout
    private lateinit var videosRV : RecyclerView
    private lateinit var videosList : ArrayList<ArrayList<String>>
    private lateinit var recyclerVAdapter : RecyclerViewAdapter
    private lateinit var backMain : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lectures_list)

        backMain = findViewById(R.id.backMain)
        sonic2Image = findViewById(R.id.sonic2Image)
        toDoLay = findViewById(R.id.toDoLay)
        videosRV = findViewById(R.id.videosRV)
        videosList = arrayListOf(
            arrayListOf("Orientation","Ik4tMWQiSL4"),
            arrayListOf("Week1 Day1","6fvXhCffLaY"),
            arrayListOf("Week1 Day1 Stand Down","fBA5j3c1D9c"),
            arrayListOf("Week1 Day2","VZnLB-q55tw"),
            arrayListOf("Week1 Day2 Stand Down","4ostmbmFL3Y"),
            arrayListOf("Week1 Day3","pESK3Vp_pCY"),
            arrayListOf("Week1 Day3 Stand Down","wUnueSGOGU4"),
            arrayListOf("Week1 Day4","YC1uPCY3_-0"),
            arrayListOf("Week1 Day4 Stand Down","IjB59Ud4K_c"),
            arrayListOf("How to Load Image From Url","hUViwFochtk")
        )

        recyclerVAdapter = RecyclerViewAdapter(videosList)
        videosRV.adapter = recyclerVAdapter
        videosRV.layoutManager = LinearLayoutManager(this)

        var intent = Intent(this, VideoPlay::class.java)
        recyclerVAdapter.setOnItemClickListener(object : YouTubePlayerListener {
            override fun onItemClick(position: Int) {
                intent.putExtra("myLocation",2)
                intent.putExtra("myTitle",videosList[position][0])
                intent.putExtra("videoPlay",videosList[position][1])
                startActivity(intent)
            }
        })

        backMain.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("status",1)
            startActivity(intent)
        }

    }
}