package com.example.myapplication42

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GiphyViewModel : ViewModel(), DefaultLifecycleObserver {

    private val _state = MutableStateFlow(GiphyListState(emptyList()))
    val state = _state.asStateFlow()

    private val giphyApi = RetrofitInstance.retrofit.create(GiphyApi::class.java)

    private var searchJob: Job? = null

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        loadTrendingGifs()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        searchJob?.cancel()
    }

    private fun loadTrendingGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            val responses = giphyApi.fetchTrending().data.map { it.images.original.url }
            updateState(responses)
        }
    }

    fun onSearchQuery(searchQuery: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val responses = giphyApi.searchImages(searchQuery).data.map { it.images.original.url }
            updateState(responses)
        }
    }

    private fun updateState(items: List<String>) {
        viewModelScope.launch(Dispatchers.Main) {
            _state.update {
                it.copy(
                    imageResults = items
                )
            }
        }
    }
}

data class GiphyListState(
    val imageResults: List<String> // image urls
)