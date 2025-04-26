//package com.example.android_bootcamp.presentation.screen.profile
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android_bootcamp.common.Resource
//import com.example.android_bootcamp.presentation.mapper.toPresentation
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class ProfileViewModel @Inject constructor(
//    private val authRepository: AuthRepository,
//    private val cacheRepository: CacheRepository,
//) : ViewModel() {
//
//    private val _userData = MutableStateFlow<Resource<UserUi>>(Resource.Success(null))
//    val userData: StateFlow<Resource<UserUi>> = _userData
//
//    val language = cacheRepository.getAppLanguage()
//
//    fun fetchUserData() {
//        viewModelScope.launch {
//            _userData.value = Resource.Loading
//
//            val result = authRepository.getUserData()
//            if (result.isSuccess) {
//                val userDto = result.getOrNull()
//                if (userDto != null) {
//                    _userData.value = Resource.Success(userDto.toPresentation())
//                } else {
//                    _userData.value = Resource.Error("User data not found")
//                }
//            } else {
//                _userData.value =
//                    Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error")
//            }
//        }
//    }
//
//    fun updateLanguage(language: String) {
//        viewModelScope.launch {
//            cacheRepository.updateLanguage(language)
//        }
//    }
//
//    fun clear() {
//        viewModelScope.launch(Dispatchers.IO) {
//            cacheRepository.clearPreferences()
//        }
//    }
//}
