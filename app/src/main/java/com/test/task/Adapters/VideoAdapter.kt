package com.test.task.Adapters

import VideoViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.task.R
import com.test.task.Server.Data.Video
import com.test.task.Server.RectRoundedTransformation


class VideoAdapter(private val list: List<Video>, videoViewModel: VideoViewModel) : RecyclerView
.Adapter<VideoAdapter.ViewHolder>() {
   // var onItemClick: ((Video) -> Unit)? = null
    var mList: List<Video>? = emptyList()
    var selectedLinearLayout: LinearLayout? = null
    var viewModel: VideoViewModel = videoViewModel

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = mList?.get(position)
        holder.card_view.animate().alpha(0.5f)
        Picasso.get().load(listItem!!.poster_url)
            .transform(RectRoundedTransformation())
            .resize(200, 200)
            .into(holder.img)
        if(position == 0){
            viewModel.setVideo(listItem)
            holder.card_view.setBackgroundResource(R.drawable.back_selected_video)
            holder.card_view.animate().alpha(1f)
            selectedLinearLayout = holder.card_view
        }else{
            holder.card_view.animate().alpha(0.5f)
        }
        holder.card_view.setOnClickListener {
            //onItemClick?.invoke(listItem)
            holder.card_view.animate().alpha(1f).duration = 200
            viewModel.setVideo(listItem)
            holder.card_view.setBackgroundResource(R.drawable.back_selected_video)
            if(selectedLinearLayout!=null){
                selectedLinearLayout!!.setBackgroundResource(R.drawable.back_small_poster)
                selectedLinearLayout!!.animate().alpha(0.5f).duration = 200
            }
            selectedLinearLayout = holder.card_view
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img)
        val card_view: LinearLayout = itemView.findViewById(R.id.card_view)
        init {
            mList = list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }
}
