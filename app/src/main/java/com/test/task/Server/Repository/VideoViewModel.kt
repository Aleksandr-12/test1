

import androidx.lifecycle.*
import com.notepad.Notepad.Retrofit.Common.Common
import com.notepad.Notepad.Retrofit.Repository.VideoRepository
import com.test.task.Server.Data.Video
import kotlinx.coroutines.*


class VideoViewModel: ViewModel() {
    private val videoRepository: VideoRepository = VideoRepository(Common.retrofitApi)
    var job: Job? = null
    val videoList = MutableLiveData<List<Video>>()
    val videoOne = MutableLiveData<Video>()
    fun getVideoList() {
        job  = CoroutineScope(Dispatchers.IO).launch {
            val response = videoRepository.getVideoList()
            withContext(Dispatchers.Main) {
                videoList.postValue(response)
            }
        }
    }

    fun setVideo(video: Video){
        job  = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                videoOne.postValue(video)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
class VideoViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideoViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}