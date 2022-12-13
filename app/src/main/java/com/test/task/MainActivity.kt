package com.test.task

import VideoViewModel
import VideoViewModelFactory
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.task.Adapters.VideoAdapter
import com.test.task.Server.Data.Video
import com.test.task.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnTouchListener, View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerList: RecyclerView
    var videoView: VideoView? = null
    var textView: EditText? = null
    var editText: EditText? = null
    var textClick: TextView? = null
    var wrapVideo: FrameLayout? = null
    var video: MutableList<Video>? = null
    lateinit var adapter: VideoAdapter;
    var _xDelta: Int? = null
    var _yDelta: Int? = null
    private var bar: ProgressDialog? = null
    private var ctlr: MediaController? = null
    private val videoViewModel: VideoViewModel by viewModels {
        VideoViewModelFactory()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerList = binding.recyclerList
        videoView = binding.videoView
        wrapVideo = binding.wrapVideo
        textClick = binding.textClick
        textView = binding.textView
        editText = binding.editText
        recyclerList.setHasFixedSize(true)

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerList.layoutManager = layoutManager

        videoViewModel.videoList.observe(this, { allVideos ->
            video = allVideos as MutableList<Video>
            adapter = VideoAdapter(video!!,videoViewModel)
            recyclerList.adapter = adapter
            /*  adapter.onItemClick= { video ->
                 videoView!!.setVideoPath(video.small_thumbnail_url)
                  videoView!!.start()
                  wrapVideo!!.clipToOutline = true
              }*/
            adapter.notifyDataSetChanged()
        })

        videoViewModel.getVideoList()

        videoViewModel.videoOne.observe(this, { video ->
            videoView!!.setVideoPath(video.small_thumbnail_url)
            videoView!!.start()
            wrapVideo!!.clipToOutline = true
            videoView!!.setOnPreparedListener { it.isLooping = true }
          /*  bar = ProgressDialog(this@MainActivity)
            bar!!.setTitle("Connecting server")
            bar!!.setMessage("Please Wait... ")
            bar!!.setCancelable(false)
            bar!!.show()
            if (bar!!.isShowing()) {
                val uri: Uri = Uri.parse(video.small_thumbnail_url)
                videoView!!.setVideoURI(uri)
                videoView!!.start()
                ctlr = MediaController(this)
                ctlr!!.setMediaPlayer(videoView)
                videoView!!.setMediaController(ctlr)
                videoView!!.requestFocus()
            }
            bar!!.dismiss()*/

        })

        textClick!!.setOnClickListener(this)
        val layoutParams = FrameLayout.LayoutParams(150, 50)
        layoutParams.leftMargin = 50
        layoutParams.topMargin = 50
        layoutParams.bottomMargin = -250
        layoutParams.rightMargin = -250
        //textView!!.setLayoutParams(layoutParams)
        textView!!.setOnTouchListener(this)

        editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                textView!!.text = s
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val X = event.rawX.toInt()
        val Y = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams = view.layoutParams as FrameLayout.LayoutParams
                _xDelta = X - lParams.leftMargin
                _yDelta = Y - lParams.topMargin
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = view.layoutParams as FrameLayout.LayoutParams
                layoutParams.leftMargin = X - _xDelta!!
                layoutParams.topMargin = Y - _yDelta!!
                layoutParams.rightMargin = -250
                layoutParams.bottomMargin = -250
                view.layoutParams = layoutParams
            }
        }
        return true
    }

    override fun onClick(p0: View?) {
        textView?.visibility  = View.VISIBLE
        if(editText!!.isVisible == true){
            editText?.visibility  = View.GONE
        }else{
            editText?.visibility  = View.VISIBLE
        }
    }
}