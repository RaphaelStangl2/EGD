package com.example.ble_test.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ble_test.data.BLEUiState
import com.example.ble_test.data.ConnectionState
import com.example.ble_test.data.ble.BLEReceiveManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BLEViewModel @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager
) : ViewModel(){

    private val _uiState = MutableStateFlow(BLEUiState())
    val uiState: StateFlow<BLEUiState> = _uiState.asStateFlow()

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)


    private fun subscribeToChanges(){
        viewModelScope.launch {
            bleReceiveManager.data.collect{ result ->
                _uiState.update { currentState ->
                    currentState.copy(
                        test = result.test
                    )
                }
            }
        }

        /*_uiState.update { currentState ->
            currentState.copy(
                test = "Test"
            )
        }*/

    }

    fun disconnect(){
        bleReceiveManager.disconnect()
    }

    fun reconnect(){
        bleReceiveManager.reconnect()
    }

    fun initializeConnection(){
        subscribeToChanges()
        bleReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        bleReceiveManager.closeConnection()
    }

}