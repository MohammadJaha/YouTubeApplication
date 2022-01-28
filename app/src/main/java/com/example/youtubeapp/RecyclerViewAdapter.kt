package com.example.youtubeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubeapp.databinding.VideosViewBinding

class RecyclerViewAdapter (private val videosName : ArrayList<ArrayList<String>>) : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {

    private lateinit var myListener: YouTubePlayerListener

    class ItemViewHolder (val binding: VideosViewBinding, listener: YouTubePlayerListener): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(listener:YouTubePlayerListener ){
        myListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            VideosViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            myListener
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val videoName = videosName[position][0]

        holder.binding.apply {
            videoTV.text = videoName
        }
    }

    override fun getItemCount(): Int {
        return videosName.size
    }
}