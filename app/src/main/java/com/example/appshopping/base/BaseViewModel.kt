package com.example.appshopping.base

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<ViewState : BaseViewState, ViewEvent : BaseViewEvent, ViewEffect : BaseViewEffect>(
    initialViewState: ViewState
) : ViewModel() {

    private val _viewModelJob = SupervisorJob()
    private val _coroutineScope = CoroutineScope(Dispatchers.IO)

    protected open val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.d("TAG", "$coroutineContext at $this get error: $throwable")
    }
    protected open val coroutineScope: CoroutineScope by lazy {
        _coroutineScope + exceptionHandler + _viewModelJob
    }

    /**
     * Used for storing and sending state of views. It should be repopulate right after view is recreated.
     * Example: Storing data on screen like text, checkbox..
     */
    protected open var currentState: ViewState = initialViewState
    private val _state = MutableStateFlow(initialViewState)
    val state: Flow<ViewState> = _state

    /**
     * Update state and notify to consumer.
     */
    protected fun setState(state: ViewState) {
        this.currentState = state
        this._state.value = state
    }

    /**
     * Used for sending an event from ViewModel to View layer (Fragment, Activity) once.
     * If the effect is sent when view is not ready to handle, it will be ignored.
     */
    private val _effect =Channel<ViewEffect>()
    val effect: Flow<ViewEffect> = _effect.receiveAsFlow()

    /**
     * Send effect to view.
     */
    protected fun setEffect(effect: ViewEffect) {
        coroutineScope.ensureActive()
        coroutineScope.launch { _effect.send(effect) }
    }

    /**
     * Handle event from view.
     */
    abstract fun onEvent(event: ViewEvent)

}