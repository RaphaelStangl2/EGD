package com.example.egd.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egd.data.*
import com.example.egd.data.ble.BLEReceiveManager
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
class EGDViewModel @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager
) : ViewModel(){

    private val _getStartedUiState = MutableStateFlow(GetStartedUiState())
    val getStartedUiState: StateFlow<GetStartedUiState> = _getStartedUiState.asStateFlow()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState: StateFlow<MapUiState> = _mapUiState.asStateFlow()

    private val _homeUiState = MutableStateFlow(HomeScreenState())
    val homeUiState: StateFlow<HomeScreenState> = _homeUiState.asStateFlow()

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    fun setHomeSearchBarContent(searchBarContent: String){
        _homeUiState.update { currentState ->
            currentState.copy(
                searchBarContent = searchBarContent
            )
        }
    }

    fun setMapSearchBarContent(searchBarContent: String){
        _mapUiState.update { currentState ->
            currentState.copy(
                searchBarContent = searchBarContent
            )
        }
    }

    fun setFirstName(firstName: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                firstName = firstName
            )
        }
    }

    fun setEmailRegister(email: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun setPasswordRegister(password: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun setEGDDevice(egdDevice: Boolean){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                EGDDevice = egdDevice
            )
        }
    }

    fun setNumberOfSteps(stepVal: Int){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                numberOfSteps = stepVal
            )
        }
    }

    fun setFuelConsumption(carConsumption: String){

        var count = 0

        for (element in carConsumption){
            if (element == '.'){
                count++
            }
        }


        if (count <= 1 && carConsumption.length <= 4){
            _getStartedUiState.update { currentState ->
                currentState.copy(
                    averageCarConsumption = carConsumption
                )
            }
        }
    }

    fun setCarName(carName: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                carName = carName
            )
        }
    }

    fun setStep(stepVal: Int){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                step = stepVal
            )
        }
    }

    fun setVisibility(visibility: Boolean){
        _loginUiState.update { currentState ->
            currentState.copy(
                passwordVisibility = visibility
            )
        }
    }

    fun setPasswordLogin(password: String){
        _loginUiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun setEmailLogin(email: String){
        _loginUiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    /*private fun subscribeToChanges(){
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
    }*/

}