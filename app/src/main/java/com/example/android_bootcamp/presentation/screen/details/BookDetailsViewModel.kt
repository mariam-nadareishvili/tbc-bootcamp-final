//package com.example.android_bootcamp.presentation.screen.details
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android_bootcamp.common.Resource
//import com.example.android_bootcamp.domain.repository.BookRepository
//import com.example.android_bootcamp.presentation.mapper.toPresentation
//import com.example.android_bootcamp.presentation.screen.search.BookUi
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class BookDetailsViewModel @Inject constructor(
//    private val bookRepository: BookRepository
//) : ViewModel() {
//
//    private val _bookDetailsState =
//        MutableStateFlow<Resource<BookUi>>(Resource.Success(null))
//    val bookDetailsState: StateFlow<Resource<BookUi>> = _bookDetailsState
//
//    private val _similarBooksState =
//        MutableStateFlow<Resource<List<BookUi>>>(Resource.Success(null))
//    val similarBooksState: StateFlow<Resource<List<BookUi>>> = _similarBooksState
//
//    fun getBookDetails(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            bookRepository.getBookById(id).collectLatest {
//                _bookDetailsState.value = when (it) {
//                    is Resource.Loading -> Resource.Loading
//                    is Resource.Success -> {
//                        val bookUi = it.data?.toPresentation()
//
//                        bookUi?.let {
//                            getSimilarBooks(bookUi.genres.first().name)
//                        }
//
//                        Resource.Success(bookUi)
//                    }
//
//                    is Resource.Error -> Resource.Error(it.message)
//                }
//            }
//        }
//    }
//
//    private fun getSimilarBooks(genre: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            bookRepository.searchBooks(genre).collectLatest {
//                _similarBooksState.value = when (it) {
//                    is Resource.Loading -> Resource.Loading
//                    is Resource.Success -> Resource.Success(it.data?.map { it.toPresentation() })
//                    is Resource.Error -> Resource.Error(it.message)
//                }
//            }
//        }
//    }
//}