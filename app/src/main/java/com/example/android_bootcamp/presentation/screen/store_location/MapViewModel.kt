package com.example.android_bootcamp.presentation.screen.store_location

//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.android_bootcamp.common.Resource
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class MapViewModel @Inject constructor(
//    private val repository: LocationRepository
//) : ViewModel() {
//
//    private val _locations =
//        MutableStateFlow<Resource<List<MapClusterItem>>>(Resource.Success(null))
//    val locations: StateFlow<Resource<List<MapClusterItem>>> = _locations
//
//    fun loadLocations() {
//        viewModelScope.launch {
//            repository.getPlaces().collectLatest { result ->
//                _locations.value = when (result) {
//                    is Resource.Loading -> Resource.Loading
//                    is Resource.Success -> Resource.Success(result.data?.map {
//                        MapClusterItem(
//                            name = it.title,
//                            latitude = it.latitude,
//                            longitude = it.longitude,
//                            address = it.address
//                        )
//                    })
//
//                    is Resource.Error -> Resource.Error(result.message)
//                }
//            }
//        }
//    }
//}