package com.example.egd.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egd.data.*
import com.example.egd.data.ble.BLEReceiveManager
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.example.egd.data.http.HttpService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class EGDViewModel @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager,
    val fusedLocationClient: FusedLocationProviderClient
) : ViewModel(){

    private val _editCarUiState = MutableStateFlow(EditCarUiState())
    val editCarUiState: StateFlow<EditCarUiState> = _editCarUiState.asStateFlow()

    private val _getStartedUiState = MutableStateFlow(GetStartedUiState())
    val getStartedUiState: StateFlow<GetStartedUiState> = _getStartedUiState.asStateFlow()

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState.asStateFlow()

    private val _mapUiState = MutableStateFlow(MapUiState())
    val mapUiState: StateFlow<MapUiState> = _mapUiState.asStateFlow()


    private val _homeUiState = MutableStateFlow(HomeScreenState())
    val homeUiState: StateFlow<HomeScreenState> = _homeUiState.asStateFlow()

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    fun setCar(car:Car){
        _editCarUiState.update { currentState ->
            currentState.copy(
                car = car,
                name = car.name,
                consumption = car.consumption.toString()
            )
        }
    }



    fun setCurrentLocation(location: Location?) {
        _mapUiState.update { currentState ->
            currentState.copy(
                startLocation = location
            )
        }
    }

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

    fun setFirstName(userName: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                userName = userName
            )
        }
    }

    fun setResponse(response: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                response = response
            )
        }
    }

    fun setResponseLogin(response: String){
        _loginUiState.update { currentState ->
            currentState.copy(
                response = response
            )
        }
    }


    fun setFriendSearchBarContent(content: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                friendSearchBarContent = content
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

    fun readCarsFromJson(inputStream:InputStream){

        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }

        val listCarType = object : TypeToken<Array<Car>>() {}.type
        //Gson().fromJson(jsonString, listCountryType)
        var carList = Gson().fromJson(jsonStringVar, Array<Car>::class.java)

        _homeUiState.update { currentState ->
            currentState.copy(
                cars = carList
            )
        }
    }

    suspend fun sendLoginRequest(onLogin: () -> Unit, sharedPreference: () -> SharedPreferences?) {
        val value = loginUiState.value
        var response: ResponseBody
        var sharedPreference = sharedPreference()

        //HttpService.loginRequest(User("", value.email, value.password))

        try{
            response = HttpService.retrofitService.postLogin(User("", value.email, value.password))
            _getStartedUiState.update { currentState ->
                currentState.copy(
                    response = response.string()
                )
            }
            onLogin()

            var editor = sharedPreference?.edit()
            editor?.putString("email", value.email)
            editor?.putBoolean("isLoggedIn", true)
            editor?.apply()

        }catch (e: Exception) {
            if (e.localizedMessage == "HTTP 404 "){
                setResponseLogin("Email address doesn't exist")
            }
            if (e.localizedMessage == "HTTP 401 "){
                setResponseLogin("Wrong password or email")
            }
            if (e.localizedMessage == "HTTP 500 "){
                setResponseLogin("Wrong password or email")
            }
        }

    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun sendRegisterRequest(onRegistered: () -> Unit, sharedPreference: () -> SharedPreferences?) {
        var response: ResponseBody
        val value = getStartedUiState.value
        var sharedPreference = sharedPreference()


        if (value.password.length < 8){
            setResponse("Password needs at least 8 characters")
        }
        else if (value.userName.length <4){
            setResponse("Username needs at least 4 characters")
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(value.email).matches()){
            setResponse("Wrong email format")
        }
        else{
            try{
                response = HttpService.retrofitService.postRegistration(User(value.userName, value.email, value.password))
                _loginUiState.update { currentState ->
                    currentState.copy(
                        response = response.string()
                    )
                }
                onRegistered()

                var editor = sharedPreference?.edit()
                editor?.putString("email", value.email)
                editor?.putBoolean("isLoggedIn", true)
                editor?.apply()
            }catch (e: Exception) {
                if (e.localizedMessage == "HTTP 404 "){
                    setResponse("Email address already exists")
                }
            }
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


    @SuppressLint("MissingPermission")
    fun getUserPosition(callback: (Location?) -> Unit) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            callback(location)
        }.addOnFailureListener { exception ->
            // Handle failure
            callback(null)
        }
    }

    fun checkLogin(onLogin: () -> Unit, sharedPreference: () -> SharedPreferences?) {
        viewModelScope.launch {
            sendLoginRequest(onLogin, sharedPreference)
        }
    }

    fun checkRegister(onRegistered: () -> Unit, sharedPreference: () -> SharedPreferences?) {
        viewModelScope.launch {
            sendRegisterRequest(onRegistered, sharedPreference)
        }
    }

    fun logout(sharedPreference: () -> SharedPreferences?){
        val sharedPreference = sharedPreference()

        var editor = sharedPreference?.edit()
        editor?.putString("email", "")
        editor?.putBoolean("isLoggedIn", false)
        editor?.apply()
    }


}