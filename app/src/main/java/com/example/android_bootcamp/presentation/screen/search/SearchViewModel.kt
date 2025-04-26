//package com.example.android_bootcamp.presentation.screen.search
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android_bootcamp.common.Resource
//import com.example.android_bootcamp.presentation.mapper.toPresentation
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class SearchViewModel @Inject constructor(private val bookRepository: BookRepository) :
//    ViewModel() {
//
//    private val _genreState =
//        MutableStateFlow<Resource<List<GenreUi>>>(Resource.Success(null))
//    val genreState: StateFlow<Resource<List<GenreUi>>> = _genreState
//
//    private val _searchedBooksState =
//        MutableStateFlow<Resource<List<BookUi>>>(Resource.Success(null))
//    val searchedBooksState: StateFlow<Resource<List<BookUi>>> = _searchedBooksState
//
//    private var searchJob: Job? = null
//
//    init {
//        getGenres()
//    }
//
//    private fun getGenres() {
//        viewModelScope.launch(Dispatchers.IO) {
//            bookRepository.getGenres().collectLatest {
//                _genreState.value = when (it) {
//                    is Resource.Loading -> Resource.Loading
//                    is Resource.Success -> Resource.Success(it.data?.map { it.toPresentation() })
//                    is Resource.Error -> Resource.Error(it.message)
//                }
//            }
//        }
//    }
//
//    fun search(query: String) {
//        searchJob?.cancel() // Cancel the previous job if it's still running
//
//        if (query.length >= 3) {
//            searchJob = viewModelScope.launch(Dispatchers.IO) {
//                delay(500)
//
//                bookRepository.searchBooks(query).collectLatest {
//                    _searchedBooksState.value = when (it) {
//                        is Resource.Loading -> Resource.Loading
//                        is Resource.Success -> Resource.Success(it.data?.map { it.toPresentation() })
//                        is Resource.Error -> Resource.Error(it.message)
//                    }
//                }
//            }
//        } else {
//            _searchedBooksState.value = Resource.Success(emptyList())
//        }
//    }
//}