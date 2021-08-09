package com.jmachado.placeholdermenu.ui.albums

import androidx.lifecycle.*
import com.jmachado.placeholdermenu.data.AlbumRepository
import com.jmachado.placeholdermenu.model.Album
import com.jmachado.placeholdermenu.service.RetrofitService
import kotlinx.coroutines.*

class AlbumsViewModel(private val repository: AlbumRepository) : ViewModel() {

    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val errorMessage = MutableLiveData<String>()

    val allAlbums: LiveData<List<Album>> = repository.allAlbums.asLiveData()

    fun fetchAlbums(){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = RetrofitService.getInstance().getAlbums()
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

class AlbumsViewModelFactory(private val repository: AlbumRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlbumsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}