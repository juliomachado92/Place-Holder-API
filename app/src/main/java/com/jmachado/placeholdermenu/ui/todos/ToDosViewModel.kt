package com.jmachado.placeholdermenu.ui.todos

import androidx.lifecycle.*
import com.jmachado.placeholdermenu.data.TodosRepository
import com.jmachado.placeholdermenu.model.Todos
import com.jmachado.placeholdermenu.service.RetrofitService
import kotlinx.coroutines.*

class ToDosViewModel(private val repository: TodosRepository) : ViewModel() {
    private var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val errorMessage = MutableLiveData<String>()

    val allTodos: LiveData<List<Todos>> = repository.allTodos.asLiveData()

    fun fetchTodos(){

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = RetrofitService.getInstance().getTodos()
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

class ToDosViewModelFactory(private val repository: TodosRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ToDosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}