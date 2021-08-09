package com.jmachado.placeholdermenu.ui.posts

import androidx.lifecycle.*
import com.jmachado.placeholdermenu.data.PostRepository
import com.jmachado.placeholdermenu.model.Post
import com.jmachado.placeholdermenu.service.RetrofitService
import kotlinx.coroutines.*

class PostsViewModel (private val repository: PostRepository) : ViewModel() {

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val errorMessage = MutableLiveData<String>()

    val allPosts: LiveData<List<Post>> = repository.allPosts.asLiveData()

    fun fetchPosts(){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = RetrofitService.getInstance().getPosts()
            withContext(Dispatchers.Main) {

                if (response.isSuccessful) {
                    repository.deleteAll()
                    repository.insertAll(response.body()?.toList()!!)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }


    private fun onError(message: String) {
        errorMessage.value = message
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}

class PostsViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}