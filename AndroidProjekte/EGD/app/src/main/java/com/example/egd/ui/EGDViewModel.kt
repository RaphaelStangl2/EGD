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
import com.example.egd.data.ble.BLEService
import com.example.egd.data.ble.GPSService
import com.example.egd.data.entities.Car
import com.example.egd.data.entities.User
import com.example.egd.data.entities.UserCar
import com.example.egd.data.http.HttpService
import com.example.egd.ui.validation.ValidationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.io.Closeable
import java.io.InputStream
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.concurrent.thread
import kotlin.concurrent.timerTask

@HiltViewModel
class EGDViewModel @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager,
    val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private var carTrackingServiceIsRunning = false

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

    fun setInitializeConnectionBLE(initializeConnectionBLE:Boolean){
        bleReceiveManager.setInitializeConnection(initializeConnectionBLE)
    }
    fun setUUIDListBLE(uuidList:Array<String?>?){
        bleReceiveManager.setUUIDList(uuidList)
    }

    fun setCar(car: Car, consumption:String) {
        _editCarUiState.update { currentState ->
            currentState.copy(
                id = car.id,
                car = car,
                name = car.name,
                consumption = consumption,
                licencePlate = car.licencePlate
            )
        }
    }

    fun setTriedToSubmitLogin(triedToSubmit: Boolean) {
        _loginUiState.update { currentState ->
            currentState.copy(
                triedToSubmit = triedToSubmit
            )
        }
    }

    fun setTriedToSubmit(triedToSubmit: Boolean) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                triedToSubmit = triedToSubmit
            )
        }
    }

    fun addUserToAssignedList(user: User) {
        val assignedFriendsList = _homeUiState.value.assignedFriendsList
        var assignedListToAdd: Array<User>
        var list: MutableList<User>
        if (assignedFriendsList == null) {
            assignedListToAdd = arrayOf(user)
            setAssignedFriendsList(assignedListToAdd)
        } else {
            list = assignedFriendsList?.toMutableList()
            list?.add(user)
            setAssignedFriendsList(list.toTypedArray())
        }
    }

    fun removeUserFromAssignedFriendList(user: User) {
        val assignedFriendsList = _homeUiState.value.assignedFriendsList
        if (assignedFriendsList != null) {
            var list = assignedFriendsList.toMutableList()
            list.remove(user)
            setAssignedFriendsList(list.toTypedArray())
        }
    }

    fun removeUserFromAssignedEditFriendsList(user:User){
        val assignedFriendsList = _editCarUiState.value.assignedFriendList
        if (assignedFriendsList != null) {
            var list = assignedFriendsList.toMutableList()
            list.remove(user)
            setAssignedEditFriendsList(list.toTypedArray())
        }
    }

    fun setAssignedFriendsList(list: Array<User>?) {
        _homeUiState.update { currentState ->
            currentState.copy(
                assignedFriendsList = list
            )
        }
    }

    fun addUserToSearchList(user: User) {
        val searchFriendList = _homeUiState.value.searchFriendList
        var searchListToAdd: Array<User>
        var list: MutableList<User>
        if (searchFriendList == null) {
            searchListToAdd = arrayOf(user)
            setSearchFriendList(searchListToAdd)
        } else {
            list = searchFriendList?.toMutableList()
            list?.add(user)
            setSearchFriendList(list.toTypedArray())
        }
    }

    fun removeUserFromSearchFriendList(user: User) {
        val searchFriendList = _homeUiState.value.searchFriendList
        if (searchFriendList != null) {
            var list = searchFriendList.toMutableList()
            list.remove(user)
            setSearchFriendList((list.toTypedArray()))
        }
    }

    fun setSearchFriendList(list: Array<User>?) {
        _homeUiState.update { currentState ->
            currentState.copy(
                searchFriendList = list
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

    fun setHomeSearchBarContent(searchBarContent: String) {
        _homeUiState.update { currentState ->
            currentState.copy(
                searchBarContent = searchBarContent
            )
        }
    }

    fun setMapSearchBarContent(searchBarContent: String) {
        _mapUiState.update { currentState ->
            currentState.copy(
                searchBarContent = searchBarContent
            )
        }
    }

    fun setFirstName(userName: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                userName = userName
            )
        }
    }

    fun setResponse(response: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                response = response
            )
        }
    }

    fun setResponseLogin(response: String) {
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

    fun setEmailRegister(email: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    fun setPasswordRegister(password: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun setEGDDevice(egdDevice: Boolean) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                EGDDevice = egdDevice
            )
        }
    }

    fun setNumberOfSteps(stepVal: Int) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                numberOfSteps = stepVal
            )
        }
    }

    fun setFuelConsumption(carConsumption: String) {

        var count = 0

        for (element in carConsumption) {
            if (element == '.') {
                count++
            }
        }


        if (count <= 1 && carConsumption.length <= 4) {
            _getStartedUiState.update { currentState ->
                currentState.copy(
                    averageCarConsumption = carConsumption
                )
            }
        }
    }

    fun setCarName(carName: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                carName = carName
            )
        }
    }

    fun setLicencePlate(licencePlate: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                licencePlate = licencePlate
            )
        }
    }

    fun setStep(stepVal: Int) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                step = stepVal
            )
        }
    }

    fun setVisibility(visibility: Boolean) {
        _loginUiState.update { currentState ->
            currentState.copy(
                passwordVisibility = visibility
            )
        }
    }

    fun setPasswordLogin(password: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                password = password
            )
        }
    }

    fun setEmailLogin(email: String) {
        _loginUiState.update { currentState ->
            currentState.copy(
                email = email
            )
        }
    }

    //fun readUsersFromJson

    fun readCarsFromJson(inputStream: InputStream) {
        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }

        val listCarType = object : TypeToken<Array<Car>>() {}.type
        var carList = Gson().fromJson(jsonStringVar, Array<Car>::class.java)

        _homeUiState.update { currentState ->
            currentState.copy(
                cars = carList
            )
        }
    }

    fun setUser(user: User) {
        _homeUiState.update { currentState ->
            currentState.copy(
                user = user
            )
        }
    }

    fun readUserFromJson(inputStream: InputStream) {
        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }

        val userType = object : TypeToken<User>() {}.type
        var user = Gson().fromJson(jsonStringVar, User::class.java)

        setUser(user)
    }

    fun readUsersFromJson(inputStream: InputStream, function: String, ) {
        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }

        val listCarType = object : TypeToken<Array<User>>() {}.type
        var userList = Gson().fromJson(jsonStringVar, Array<User>::class.java)

        if (function == "assign"){
            setAssignedFriendsList(userList)
        }
        if (function == "search"){
            setSearchFriendList(userList)
        }
        if (function == "assignEdit"){
            setAssignedEditFriendsList(userList)
        }
    }


    suspend fun getCars(userId: Long) {
        var response: ResponseBody

        try {
            response = HttpService.retrofitService.getCarsForUser(userId)
            readCarsFromJson(response.byteStream())

        } catch (e: Exception) {
            e.message
        }
    }

    private suspend fun getUsersForFilterAsync(filter: String) {
        var response: ResponseBody

        try {
            response = HttpService.retrofitService.getUserForFilter(filter)
            readUsersFromJson(response.byteStream(), "search")

        } catch (e: Exception) {
            e.message
        }
    }

    suspend fun sendLoginRequest(onLogin: () -> Unit, sharedPreference: () -> SharedPreferences?) {
        val value = loginUiState.value
        var response: ResponseBody
        var sharedPreference = sharedPreference()

        //HttpService.loginRequest(User("", value.email, value.password))

        try {
            response = HttpService.retrofitService.postLogin(
                User(
                    id = null,
                    userName = "",
                    email = value.email,
                    password = value.password
                )
            )
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

        } catch (e: Exception) {
            if (e.localizedMessage == "HTTP 404 ") {
                setResponseLogin("Email address doesn't exist")
            }
            if (e.localizedMessage == "HTTP 401 ") {
                setResponseLogin("Wrong password or email")
            }
            if (e.localizedMessage == "HTTP 500 ") {
                setResponseLogin("Wrong password or email")
            }
        }

    }


    @SuppressLint("SuspiciousIndentation")
    suspend fun sendRegisterRequest(
        onRegistered: () -> Unit,
        sharedPreference: () -> SharedPreferences?
    ) {
        val service = ValidationService()
        var response: ResponseBody? = null
        val value = getStartedUiState.value
        val homeValue = homeUiState.value
        var sharedPreference = sharedPreference()
        var userToAdd = User(
            id = null,
            userName = value.userName,
            email = value.email,
            password = value.password
        )
        var car = Car(null, value.carName, value.averageCarConsumption.toDouble(), 0.0, 0.0, value.currentUUID, value.licencePlate)

        var friendsAssignList: ArrayList<UserCar> = ArrayList<UserCar>()

        if (homeValue.assignedFriendsList != null) {
            for (friend in homeValue.assignedFriendsList!!) {
                friendsAssignList.add(
                    UserCar(
                        User(
                            friend.id,
                            friend.userName,
                            friend.email,
                            friend.password
                        ), car, false
                    )
                )
            }
        }
        friendsAssignList.add(UserCar(userToAdd, car, true))

        var finalList: Array<UserCar> = friendsAssignList.toTypedArray()

        if (service.validateRegisterForm(value.userName, value.email, value.password)) {
            try {
                //response = HttpService.retrofitService.postRegistration(userToAdd)
                response = HttpService.retrofitService.postUserCars(finalList)

                if (response != null) {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            response = response.string()
                        )
                    }
                }

                setTriedToSubmit(false)
                setCarName("")
                setFuelConsumption("")
                onRegistered()
                bleReceiveManager.closeConnection()

                var editor = sharedPreference?.edit()
                editor?.putString("email", value.email)
                editor?.putBoolean("isLoggedIn", true)
                editor?.apply()
            } catch (e: Exception) {
                if (e.localizedMessage == "HTTP 404 ") {
                    setResponse("Email address already exists")
                }
            }
        }
    }



    private fun subscribeToChanges(){
        viewModelScope.launch {
            bleReceiveManager.data.collect{ result ->
                if (result.test){
                    connectionState = ConnectionState.Connected
                } else{
                    connectionState = ConnectionState.Uninitialized
                }
                _getStartedUiState.update { currentState ->
                    currentState.copy(
                        connectionSuccessful = result.test,
                        accidentCode = result.accidentCode,
                        currentUUID = result.uuid
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
        connectionState = ConnectionState.Disconnected
    }

    fun reconnect(){
        bleReceiveManager.reconnect()
        connectionState = ConnectionState.Connected
    }

    fun initializeConnection(startForeground: () -> Unit) {
        //BLEService.startService(this, "BLE Service is running")
        //startForeground()
        bleReceiveManager.startReceiving()
        subscribeToChanges()
        addCloseable(Closeable({bleReceiveManager.closeConnection()}))
        connectionState = ConnectionState.CurrentlyInitializing

        var gpsService: GPSService = GPSService(viewModel = this, {})
        //gpsService.run()
    }

    fun startCarTrackingService(){
        if (!carTrackingServiceIsRunning){
            carTrackingServiceIsRunning = true
            var homeUiState = homeUiState.value
            var getStartedState = getStartedUiState.value

            viewModelScope.launch {
                while(true){
                    delay(3000)
                    getUserPosition(){ location ->
                        if (homeUiState.cars!= null && location!= null){
                            var car: Car? = null
                            for (i in homeUiState.cars!!){
                                if (getStartedState.currentUUID == i.uuid){
                                    car = i
                                }
                            }
                            if (car != null){
                                car?.latitude = location.latitude
                                car?.longitude = location.longitude
                                viewModelScope.launch {
                                    if (car != null) {
                                        HttpService.retrofitService.putCar(car)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public override fun onCleared() {
        super.onCleared()
        bleReceiveManager.closeConnection()
    }


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

    fun getCarsForUserId(userId: Long) {
        viewModelScope.launch {
            getCars(userId)
        }
    }



    fun startGettingCars(userId: Long) {
        setGetCars(true)

        val mapUiState = mapUiState.value


        viewModelScope.launch {
            while (mapUiState.getCars){
                getCarsForUserId(userId)
                delay(4000)
            }
            /*timer!!.schedule(timerTask {
                getCarsForUserId(userId)
            }, 4000)*/
        }

    }

    fun stopGettingCars() {
        setGetCars(false)
    }

    fun setGetCars(getCars:Boolean){
        _mapUiState.update { currentState ->
            currentState.copy(
                getCars = getCars
            )
        }
    }

    fun getUsersForFilter(filter: String) {
        viewModelScope.launch {
            getUsersForFilterAsync(filter)
        }
    }

    fun logout(sharedPreference: () -> SharedPreferences?, stopForegroundService: () -> Unit) {
        val sharedPreference = sharedPreference()

        var editor = sharedPreference?.edit()

        homeUiState.value.assignedFriendsList = null
        homeUiState.value.searchFriendList = null
        homeUiState.value.cars = null
        getStartedUiState.value.step = 1
        getStartedUiState.value.numberOfSteps = 5
        setUser(User(id = null, userName = "", email = "", password = "",))

        editor?.putString("email", "")
        editor?.putBoolean("isLoggedIn", false)
        editor?.apply()

        //stopForegroundService()
        bleReceiveManager.disconnect()
        bleReceiveManager.closeConnection()
        connectionState = ConnectionState.Uninitialized

        onCleared()
        setConnectionSuccessful(false)
    }

    fun setConnectionSuccessful(connectionSuccessful: Boolean) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                connectionSuccessful = connectionSuccessful,
                currentUUID = ""
            )
        }
        setCurrentDrivingUser()

    }

    fun getUserForEmail(sharedPreference: () -> SharedPreferences?) {

        viewModelScope.launch {
            val homeUiStateValue = homeUiState.value
            var response: ResponseBody
            val sharedPreference = sharedPreference()?.getString("email", "")

            try {
                if (sharedPreference != null) {
                    response = HttpService.retrofitService.getUserForEmail(sharedPreference)
                    readUserFromJson(response.byteStream())

                    if (homeUiStateValue.user?.id != null) {
                        getCarsForUserId(homeUiStateValue.user.id)
                    }
                }
            } catch (e: Exception) {
                if (e.message == "") {
                    throw e
                }
            }
        }
    }

    fun checkLogin(sharedPreference: () -> SharedPreferences?): Boolean {
        var isLoggedIn = sharedPreference()?.getBoolean("isLoggedIn", false)

        if (isLoggedIn == true) {
            getUserForEmail { sharedPreference() }
            return true
        } else {
            return false
        }
    }

    fun addCar(onAdded: () -> Unit) {
        viewModelScope.launch {
            var response: ResponseBody? = null
            val getStartedVal = getStartedUiState.value
            val homeValue = homeUiState.value

            var car: Car = Car(
                null,
                getStartedVal.carName,
                getStartedVal.averageCarConsumption.toDouble(),
                0.0,
                0.0,
                getStartedVal.currentUUID,
                getStartedVal.licencePlate
            )

            var friendsAssignList: ArrayList<UserCar> = ArrayList<UserCar>()

            if (homeValue.assignedFriendsList != null) {
                for (friend in homeValue.assignedFriendsList!!) {
                    friendsAssignList.add(
                        UserCar(
                            User(
                                friend.id,
                                friend.userName,
                                friend.email,
                                friend.password
                            ), car, false
                        )
                    )
                }
            }
            friendsAssignList.add(UserCar(homeValue.user!!, car, true))

            var finalList: Array<UserCar> = friendsAssignList.toTypedArray()

            try {
                //response = HttpService.retrofitService.postRegistration(userToAdd)
                response = HttpService.retrofitService.postUserCars(finalList)

                if (response != null) {
                    _loginUiState.update { currentState ->
                        currentState.copy(
                            response = response.string()
                        )
                    }
                }
                setCarName("")
                setFuelConsumption("")
                setAssignedFriendsList(null)
                setSearchFriendList(null)
                onAdded()
            } catch (e: Exception) {
                if (e.localizedMessage == "HTTP 404 ") {
                    setResponse("Email address already exists")
                }
            }
        }
    }

    fun updateCar(onUpdated: () -> Unit) {
        viewModelScope.launch {
            val editCarValue = editCarUiState.value
            val homeValue = homeUiState.value
            val validationService = ValidationService()
            var response: ResponseBody? = null


            if (validationService.validateCarInfoScreen(editCarValue.name, editCarValue.consumption)) {
                try {
                    val car = Car(editCarValue.id, editCarValue.name, editCarValue.consumption.toDouble(), 0.0, 0.0, null, editCarValue.licencePlate)
                    response = HttpService.retrofitService.putCar(car)

                    val addedFriendsList =  editCarValue.addFriendList
                    val removedFriendsList = editCarValue.removeFriendList

                    if (removedFriendsList != null){
                        for (user in removedFriendsList){
                            HttpService.retrofitService.deleteUserCar(UserCar(user, car, false))
                        }
                    }
                    if (addedFriendsList != null) {
                        for (user in addedFriendsList){
                            HttpService.retrofitService.postUserCar(UserCar(user, car, false))
                        }
                    }

                    /*if (removedFriendsList != null){
                        for (user in removedFriendsList){
                            HttpService.retrofitService.
                        }
                    }*/
                    setAddedFriendsList(emptyArray())
                    setAssignedFriendsList(emptyArray())
                    setRemovedFriendsList(emptyArray())
                    setTriedToSubmitEdit(false)
                    onUpdated()

                } catch (e: Exception) {
                    throw e
                }
            }
        }
    }

    fun setTriedToSubmitEdit(triedToSubmit: Boolean) {
        _editCarUiState.update { currentState ->
            currentState.copy(
                triedToSubmit = triedToSubmit
            )
        }
    }

    fun getUsersForCar(){

        viewModelScope.launch {
            val id = editCarUiState.value.id
            var response: ResponseBody? = null

            try {
                response = HttpService.retrofitService.getUsersForCar(id)

                readUsersFromJson(response.byteStream(), "assignEdit")

            } catch(e:Exception){
                throw e
            }
        }
    }

    fun setButtonClicked(b: Boolean) {
        _getStartedUiState.update { currentState->
            currentState.copy(
                buttonClicked = b
            )
        }

    }

    fun setAccidentCode(s: String) {
        _getStartedUiState.update { currentState->
            currentState.copy(
                accidentCode = s
            )
        }
    }

    fun addUserToRemovedFriendsList(user: User) {
        var removedFriendsList = editCarUiState.value.removeFriendList

        var removedListToAdd: Array<User>
        var list: MutableList<User>

        if (removedFriendsList == null) {
            removedListToAdd = arrayOf(user)
            setRemovedFriendsList(removedListToAdd)
        } else {
            list = removedFriendsList?.toMutableList()
            list?.add(user)
            setRemovedFriendsList(list.toTypedArray())
        }
    }
    fun addUserToAddedList(user:User){
        var addedList = editCarUiState.value.addFriendList

        var addedListToAdd: Array<User>
        var list: MutableList<User>

        if (addedList == null) {
            addedListToAdd = arrayOf(user)
            setAddedFriendsList(addedListToAdd)
        } else {
            list = addedList.toMutableList()
            list?.add(user)
            setAddedFriendsList(list.toTypedArray())
        }
    }

    fun setAddedFriendsList(addedListToAdd: Array<User>) {
        _editCarUiState.update { currentState->
            currentState.copy(
                addFriendList = addedListToAdd
            )
        }
    }

    fun setAssignedEditFriendsList(userList: Array<User>?) {
        _editCarUiState.update { currentState->
            currentState.copy(
                assignedFriendList = userList
            )
        }
    }

    fun setRemovedFriendsList(removedListToAdd: Array<User>) {
        _editCarUiState.update { currentState->
            currentState.copy(
                removeFriendList = removedListToAdd
            )
        }
    }

    fun setCurrentDrivingUser(){
        val getStartedState = getStartedUiState.value
        val homeState = homeUiState.value
        var car:Car? = null
        var userCar:UserCar? = null
        if (getStartedState!= null && homeState.cars!= null){
            for (i in homeState.cars!!){
                if (i.uuid == getStartedState.currentUUID && i.id != null){
                    car = i
                }
            }
        }
        if (homeState.user != null && car != null){
            userCar = UserCar(homeState.user, car, true)
        }
        if (userCar != null){
            viewModelScope.launch {
                HttpService.retrofitService.putCurrentDriver(userCar)
            }
        }
    }

    fun applyChangedUsers(){
        _homeUiState.value.assignedFriendsList

    }

    fun addUserToAssignedEditList(user: User) {
        var assignedList = editCarUiState.value.assignedFriendList

        var assignedListToAdd: Array<User>
        var list: MutableList<User>

        if (assignedList == null) {
            assignedListToAdd = arrayOf(user)
            setAddedFriendsList(assignedListToAdd)
        } else {
            list = assignedList.toMutableList()
            list?.add(user)
            setAssignedEditFriendsList(list.toTypedArray())
        }
    }

    override fun addCloseable(closeable: Closeable) {
        super.addCloseable(closeable)
    }

    fun closeConnection(){
        bleReceiveManager.closeConnection()
    }

    fun removeUserFromAddList(user: User) {
        val addFriendsList = editCarUiState.value.addFriendList
        if (addFriendsList != null) {
            var list = addFriendsList.toMutableList()
            list.remove(user)
            setAddedFriendsList(list.toTypedArray())
        }
    }
}