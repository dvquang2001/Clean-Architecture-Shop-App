package com.example.appshopping.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appshopping.domain.usecase.main.save_user.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val saveUserUseCase: SaveUserUseCase) :
    ViewModel() {

    private val _viewModelJob = SupervisorJob()
    private val _coroutineScope = CoroutineScope(Dispatchers.IO)

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("TAG", "$coroutineContext at $this get error: $throwable")
    }
    private val coroutineScope: CoroutineScope by lazy {
        _coroutineScope + exceptionHandler + _viewModelJob
    }

    private var job: Job? = null


    fun saveUserToStorage() {
        job?.cancel()
        job = coroutineScope.launch {
           saveUserUseCase()
       }
    }

}
