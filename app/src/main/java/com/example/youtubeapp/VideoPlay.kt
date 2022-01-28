package com.example.youtubeapp

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoPlay : AppCompatActivity() {

    private lateinit var youTubePlayerView : YouTubePlayerView
    private var currentTime : Float =0f
    private lateinit var videoPlay : String
    private lateinit var bundle: Bundle
    private var myLocation = 1
    private lateinit var exitButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_play)

        if(savedInstanceState!=null){
            currentTime = savedInstanceState.getFloat("currentTime", 1f)
        }

        bundle = intent.extras!!
        myLocation = bundle.getInt("myLocation")
        title = bundle.getString("myTitle")
        videoPlay = bundle.getString("videoPlay")!!

        exitButton = findViewById(R.id.exitButton)

        youTubePlayerView = findViewById(R.id.youTubePlayerView)
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoPlay, currentTime);
            }
            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)
                currentTime = second
            }
        })

        val handler = Handler()
        var intent = Intent(this, MainActivity::class.java)
        if (myLocation == 2)
            intent = Intent(this, LecturesList::class.java)
        exitButton.setOnClickListener{
            intent.putExtra("status",1)
            startActivity(intent)
            handler.postDelayed({
                onDestroy()
            }, 3000)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("currentTime", currentTime)
    }

}