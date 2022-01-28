package com.example.youtubeapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var status = 0

    private lateinit var rotateAnimation: Animation
    private lateinit var sonic1Image : ImageView
    private lateinit var welcomeLay : LinearLayout
    private lateinit var loadingImage : ImageView
    private lateinit var connectionTV : TextView
    private lateinit var retryButton: Button
    private lateinit var bundle: Bundle

    private lateinit var sonic2Image : ImageView
    private lateinit var toDoLay : LinearLayout
    private lateinit var videosRV : RecyclerView
    private lateinit var videosList : ArrayList<ArrayList<String>>
    private lateinit var recyclerVAdapter : RecyclerViewAdapter

    private var check = true
    private lateinit var connectionStatus : String
    private lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent.extras?.isEmpty == false) {
            bundle = intent.extras!!
            status = bundle.getInt("status")
        }

        if(savedInstanceState!=null){
            status = savedInstanceState.getInt("myNumber", 1)
        }


        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        connectionStatus = if (activeNetwork?.isConnectedOrConnecting == true)
            "Connection Success"
        else
            "Connection Fail"

        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_animmation)
        sonic1Image = findViewById(R.id.sonic1Image)
        loadingImage = findViewById(R.id.loadingImage)
        connectionTV = findViewById(R.id.connectionTV)
        welcomeLay = findViewById(R.id.welcomeLay)
        retryButton = findViewById(R.id.retryButton)

        sonic2Image = findViewById(R.id.sonic2Image)
        toDoLay = findViewById(R.id.toDoLay)
        videosRV = findViewById(R.id.videosRV)
        videosList = arrayListOf(
            arrayListOf("Lectures",""),
            arrayListOf("Funny Cats","DXUAyRRkI6k"),
            arrayListOf("Super FUNNY VIDEOS","DODLEX4zzLQ"),
            arrayListOf("Ronaldinho","_dBz4dTZocg"),
            arrayListOf("Developing Your Focus","dt6vJxMa41Q"),
            arrayListOf("Focus On Learning","wlCz8nkDNqo")
        )

        handler = Handler()
        if (status == 0){
            handler.postDelayed({
                startingView()
            }, 1000)
            if (activeNetwork?.isConnectedOrConnecting == true) {
                connect()
            }
            else{
                disconnect()
            }
        }
        else{
            changeView()
        }

        recyclerVAdapter = RecyclerViewAdapter(videosList)
        videosRV.adapter = recyclerVAdapter
        videosRV.layoutManager = LinearLayoutManager(this)

        val intent = Intent(this, VideoPlay::class.java)
        recyclerVAdapter.setOnItemClickListener(object : YouTubePlayerListener {
            override fun onItemClick(position: Int) {
                if(position == 0){
                    startActivity(Intent(this@MainActivity, LecturesList::class.java))
                }
                else{
                    intent.putExtra("myLocation", 1)
                    intent.putExtra("myTitle", videosList[position][0])
                    intent.putExtra("videoPlay", videosList[position][1])
                    startActivity(intent)
                }
            }
        })
    }

    private fun startingView(){
        loadingImage.startAnimation(rotateAnimation)
        if (check)
            handler.postDelayed({
                startingView()
            }, 3000)
        check = true
    }

    private fun connect(){
        handler.postDelayed({
            check = false
            connectionTV.text = "   $connectionStatus   "
        }, 10000)
        handler.postDelayed({
            status = 1
            changeView()
        }, 12000)
    }

    private fun disconnect(){
        handler.postDelayed({
            connectionTV.text = "   $connectionStatus   "
            retryButton.isVisible = true
            retryButton.setOnClickListener{
                recreate()
            }
        }, 10000)
    }

    private fun changeView() {
        sonic2Image.isVisible = true
        toDoLay.isVisible = true
        videosRV.isVisible = true
        welcomeLay.isVisible = false
        sonic1Image.isVisible = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("myNumber", status)
    }
}