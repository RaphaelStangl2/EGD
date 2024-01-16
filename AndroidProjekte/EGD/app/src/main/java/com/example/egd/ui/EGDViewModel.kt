package com.example.egd.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.egd.data.*
import com.example.egd.data.ble.BLEReceiveManager
import com.example.egd.data.ble.GPSService
import com.example.egd.data.costsEnum.CostsEnum
import com.example.egd.data.entities.*
import com.example.egd.data.http.HttpService
import com.example.egd.data.http.MapsHttpService
import com.example.egd.ui.validation.ValidationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
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
import java.time.LocalDate
import java.time.DateTimeException
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class EGDViewModel @Inject constructor(
    private val bleReceiveManager: BLEReceiveManager,
    val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private var carTrackingServiceIsRunning = false
    private var BLEServiceIsRunning = false
    private var InvitationServiceIsRunning = false


    //stats rsheed
    private val _statsState = MutableStateFlow(StatisticsScreenState())
    val statsState: StateFlow<StatisticsScreenState> = _statsState.asStateFlow()

    private val _costsState = MutableStateFlow(CostsState())
    val costsState: StateFlow<CostsState> = _costsState.asStateFlow()

    private val _driveState = MutableStateFlow(DriveState())
    val driveState: StateFlow<DriveState> = _driveState.asStateFlow()

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

    private val _connectionState  = MutableStateFlow(ConnectionScreenState())
    val connectionState: StateFlow<ConnectionScreenState> = _connectionState.asStateFlow()

    private val _invitationState = MutableStateFlow(InvitationState())
    val invitation: StateFlow<InvitationState> = _invitationState.asStateFlow()

    var lockCarTracking = false

    fun setConnectionState(connectionEnum: ConnectionEnum){
        _connectionState.update { currentState ->
            currentState.copy(
                connectionState = connectionEnum
            )
        }
    }

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
                licensePlate = car.licensePlate,
                latitude = car.latitude,
                longitude = car.longitude,
                uuid = car.uuid,
                currentUser = car.currentUser
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
            if (assignedFriendsList != null){
                list = assignedFriendsList?.toMutableList()!!
                list?.add(user)
                setAssignedFriendsList(list.toTypedArray())

            }
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
            if (searchFriendList != null){
                list = searchFriendList?.toMutableList()!!
                list?.add(user)
                setSearchFriendList(list.toTypedArray())
            }

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

    fun setLicensePlate(licensePlate: String){
        _getStartedUiState.update { currentState ->
            currentState.copy(
                licensePlate = licensePlate
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

        if (lockCarTracking){
            var currentDriverIsInitialized = false
            carList.forEach { car -> if (car.currentUser != null){
                currentDriverIsInitialized = true
            }
            }
            if (currentDriverIsInitialized){
                lockCarTracking = false
            }
        }
        viewModelScope.launch {
            carList.forEach {
                if (homeUiState.value.user != null){
                    val response: ResponseBody = HttpService.retrofitService.getUserCarWithoutId(UserCar(homeUiState.value.user!!, it, false))
                    val userCar = readUserCarFromJson(response.byteStream())
                    it.isAdmin = userCar!!.isAdmin
                }
            }
            setUUIDListBLE(carList?.map {it.uuid}?.toTypedArray())
            _homeUiState.update { currentState ->
                currentState.copy(
                    cars = carList
                )
            }
        }
    }

    private fun readUserCarFromJson(inputStream: InputStream): UserCar? {
        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }
        return Gson().fromJson(jsonStringVar, UserCar::class.java)
    }

    fun setUser(user: User) {
        _homeUiState.update { currentState ->
            currentState.copy(
                user = user
            )
        }
    }

    fun readInvitationsFromJson(inputStream: InputStream): Array<Invitation> {
        var jsonStringVar = inputStream.bufferedReader()
            .use { it.readText() }

        val invitationType = object : TypeToken<Invitation>() {}.type

        return Gson().fromJson(jsonStringVar, Array<Invitation>::class.java)
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

    private suspend fun sendRegisterRequestWithoutCar(onRegistered: () -> Unit, sharedPreference: () -> SharedPreferences?) {
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

        if (service.validateRegisterForm(value.userName, value.email, value.password)) {
            try {
                //response = HttpService.retrofitService.postRegistration(userToAdd)
                response = HttpService.retrofitService.postRegistration(userToAdd)

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
                setUUIDListBLE(emptyArray())
                setCurrentUUID("")
                setNumberOfSteps(5)

                var editor = sharedPreference?.edit()
                editor?.putString("email", value.email)
                editor?.putBoolean("isLoggedIn", true)
                editor?.apply()
            } catch (e: Exception) {
                if (e.localizedMessage == "HTTP 500 ") {
                    setResponse("Email address already exists")
                }
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
        var car = Car(null, value.carName, value.averageCarConsumption.toDouble(), 0.0, 0.0, value.currentUUID, value.licensePlate, null, null)
        var userCarToAdd = UserCar(userToAdd, car, true)

        var friendsAssignList: ArrayList<UserCar> = ArrayList<UserCar>()
        var invitationList: ArrayList<Invitation> = ArrayList<Invitation>()

        if (homeValue.assignedFriendsList != null) {
            for (friend in homeValue.assignedFriendsList!!) {
                invitationList.add(
                    Invitation(null, User(
                        friend.id,
                        friend.userName,
                        friend.email,
                        friend.password
                    ),userCarToAdd, "waiting"))
            }
        }
        friendsAssignList.add(userCarToAdd)

        var finalList: Array<UserCar> = friendsAssignList.toTypedArray()

        if (service.validateRegisterForm(value.userName, value.email, value.password)) {
            try {

                //response = HttpService.retrofitService.postRegistration(userToAdd)
                response = HttpService.retrofitService.postUserCars(finalList)
                for (invitation in invitationList){
                    HttpService.retrofitService.addInvitation(invitation)
                }

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
                setUUIDListBLE(emptyArray())
                setCurrentUUID("")
                setNumberOfSteps(5)

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

    private fun setCurrentUUID(uuid: String) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                currentUUID = uuid,
                connectionSuccessful = false

            )
        }
    }


    private fun subscribeToChanges(){
        var homeScreenState = homeUiState.value
        viewModelScope.launch {
            var connectionState = connectionState.value
            bleReceiveManager.data.collect{ result ->
                if (result.test){
                    connectionState.connectionState = ConnectionEnum.Connected
                    _getStartedUiState.update { currentState ->
                        currentState.copy(
                            connectionSuccessful = result.uuid!= null,
                            accidentCode = result.accidentCode,
                            currentUUID = result.uuid
                        )
                    }
                    var userCar = getUserCarForUUID(result.uuid)
                    if (userCar != null){
                        setDriveStartPosition(LatLng(userCar?.car!!.latitude, userCar.car.longitude))
                    }
                    setCurrentDrivingUser(result.uuid)

                } else{
                    connectionState.connectionState = ConnectionEnum.Uninitialized
                    removeCurrentDrivingUser(result.uuid)
                    _getStartedUiState.update { currentState ->
                        currentState.copy(
                            connectionSuccessful = false,
                            accidentCode = result.accidentCode,
                        )
                    }
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
        connectionState.value.connectionState = ConnectionEnum.Disconnected
    }

    fun reconnect(){
        bleReceiveManager.reconnect()
        connectionState.value.connectionState = ConnectionEnum.Connected
    }

    fun initializeConnection(startForeground: () -> Unit) {
        //BLEService.startService(this, "BLE Service is running")
        //startForeground()
        bleReceiveManager.startReceiving()
        subscribeToChanges()
        addCloseable(Closeable({bleReceiveManager.closeConnection()}))
        connectionState.value.connectionState = ConnectionEnum.CurrentlyInitializing

        var gpsService: GPSService = GPSService(viewModel = this, {})
        //gpsService.run()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startCarTrackingService() {
        if (!carTrackingServiceIsRunning){
            carTrackingServiceIsRunning = true

            viewModelScope.launch {
                while(true){
                    delay(3000)
                    var homeUiState = homeUiState.value
                    var getStartedState = getStartedUiState.value
                    var driveState = driveState.value
                    getUserPosition(){ location ->
                        if (homeUiState.cars!= null && location!= null && !lockCarTracking){
                            var car: Car? = null
                            for (i in homeUiState.cars!!){
                                if (getStartedState.currentUUID == i.uuid){
                                    car = i
                                }
                            }
                            if (car != null && car.currentUser != null){
                                car?.latitude = location.latitude
                                car?.longitude = location.longitude
                                viewModelScope.launch {
                                    if (car != null) {
                                        HttpService.retrofitService.putCar(car)
                                        if (driveState.startPosition != null && getKilometersWithoutAPI(driveState.startPosition!!,
                                                LatLng(location.latitude, location.longitude)) > 1) {
                                            setKilometersForDrive(
                                                driveState.startPosition!!,
                                                LatLng(location.latitude, location.longitude)
                                            )
                                            setDriveStartPosition(LatLng(location.latitude, location.longitude))
                                            var drive: Drive = Drive(null, driveState.kilometers, userCar = null, date = localDateToDate(LocalDate.now()))
                                            HttpService.retrofitService.addDrive(drive)
                                            // UserCar setzen
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getKilometersWithoutAPI(startPosition: LatLng, destinationPosition: LatLng): Double {
        var results : FloatArray = FloatArray(1)
        Location.distanceBetween(startPosition.latitude, startPosition.longitude, destinationPosition.latitude, destinationPosition.longitude, results)
        return results[0].toDouble()/1000
    }

    fun startInvitationService(onNoInternetConnection: () -> Boolean) {

        if (!InvitationServiceIsRunning){
            InvitationServiceIsRunning = true

            viewModelScope.launch {
                while(true){
                    var homeUiState = homeUiState.value
                    var invitationState = invitation.value
                    invitationState.incomingInvitationList
                    var user = homeUiState.user
                    if (onNoInternetConnection()){
                        if (user?.id != null){
                            var responseInv = HttpService.retrofitService.getInvitationByUserId(user.id!!)
                            var responseInvSend = HttpService.retrofitService.getInvitationBySendUserId(user.id!!)

                            var invArray =readInvitationsFromJson(responseInvSend!!.byteStream())
                            var invSendArray= readInvitationsFromJson(responseInv!!.byteStream())

                            setStatusInvitationList(invArray)
                            setIncomingInvitationList(invSendArray)
                        }
                    }

                    delay(10000)
                }
            }
        }
    }

    fun updateInvitatioStatus(invitationV: Invitation, status:String){
        invitationV.status = status
        var incomingInvitationArray = invitation.value.incomingInvitationList
        var incomingInvitationList = incomingInvitationArray?.toMutableList()
        incomingInvitationList?.remove(invitationV)
        invitationV.status=status
        incomingInvitationList?.add(invitationV)
        setIncomingInvitationList(incomingInvitationList?.toTypedArray())

        viewModelScope.launch {
            HttpService.retrofitService.updateInvitationStatus(invitationV)
        }
    }

    private fun setIncomingInvitationList(invArray: Array<Invitation>?) {
        _invitationState.update { currentState->
            currentState.copy(
                incomingInvitationList = invArray
            )
        }
    }

    private fun setStatusInvitationList(invSendArray: Array<Invitation>) {
        _invitationState.update { currentState->
            currentState.copy(
                statusInvitationList = invSendArray
            )
        }
    }

    fun startBLEService(){
        if (!BLEServiceIsRunning){
            BLEServiceIsRunning = true

            viewModelScope.launch {
                while(true){
                    var homeUiState = homeUiState.value
                    if (homeUiState.cars != null){
                        val listCars = homeUiState.cars
                        setUUIDListBLE(listCars?.map {it.uuid}?.toTypedArray())
                        if (connectionState.value.connectionState == ConnectionEnum.Uninitialized){
                            initializeConnection {  }
                        }
                        //if (connectionState.value.connectionState == ConnectionEnum.Disconnected){
                        //    reconnect()
                        //}
                    }
                    delay(10000)
                }
            }
        }
    }

    public override fun onCleared() {
        if (homeUiState.value.cars != null){
            for (car:Car in homeUiState.value.cars!!){
                if (car.currentUser != null){
                    if (car.uuid != null){
                        removeCurrentDrivingUser(car.uuid!!)
                    }
                }
            }
        }
        super.onCleared()
        //bleReceiveManager.closeConnection()
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

    fun checkRegister(
        onRegistered: () -> Unit,
        sharedPreference: () -> SharedPreferences?,
        withCar: Boolean
    ) {
        viewModelScope.launch {
            if (withCar == true){
                sendRegisterRequest(onRegistered, sharedPreference)
            } else{
                sendRegisterRequestWithoutCar(onRegistered, sharedPreference)
            }
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
        invitation.value.statusInvitationList = null
        invitation.value.incomingInvitationList = null
        getStartedUiState.value.step = 1
        getStartedUiState.value.numberOfSteps = 5
        setUser(User(id = null, userName = "", email = "", password = "",))


        editor?.putString("email", "")
        editor?.putBoolean("isLoggedIn", false)
        editor?.apply()

        //stopForegroundService()
        bleReceiveManager.disconnect()
        bleReceiveManager.closeConnection()
        setCurrentUUID("")
        homeUiState.value.cars = emptyArray()

        onCleared()
        setConnectionSuccessful(false)
    }

    private fun setCars(emptyArray: Array<Car>) {
        _homeUiState.update { currentState ->
            currentState.copy(
                 cars = emptyArray,
            )
        }
    }

    fun setConnectionSuccessful(connectionSuccessful: Boolean) {
        _getStartedUiState.update { currentState ->
            currentState.copy(
                connectionSuccessful = connectionSuccessful,
                currentUUID = ""
            )
        }
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
                getStartedVal.licensePlate,
                null,
                null
            )

            var friendsAssignList: ArrayList<UserCar> = ArrayList<UserCar>()

            /*if (homeValue.assignedFriendsList != null) {
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
            }*/
            friendsAssignList.add(UserCar(homeValue.user!!, car, true))

            var finalList: Array<UserCar> = friendsAssignList.toTypedArray()

            try {
                //response = HttpService.retrofitService.postRegistration(userToAdd)
                response = HttpService.retrofitService.postUserCars(finalList)

                if (homeValue.assignedFriendsList != null) {
                    for (user in homeValue.assignedFriendsList!!){
                        if (homeValue.user != null){
                            HttpService.retrofitService.addInvitation(Invitation(null,user,UserCar(homeValue.user, car, true), "waiting"))
                        }
                    }
                }

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
            val getStartedValue = getStartedUiState.value
            val validationService = ValidationService()
            var response: ResponseBody? = null


            if (validationService.validateCarInfoScreen(editCarValue.name, editCarValue.consumption, editCarValue.licensePlate)) {
                try {
                    val car = Car(editCarValue.id, editCarValue.name, editCarValue.consumption.toDouble(), editCarValue.latitude, editCarValue.longitude, editCarValue.uuid, editCarValue.licensePlate, editCarValue.currentUser,null)
                    response = HttpService.retrofitService.putCar(car)


                    val addedFriendsList =  editCarValue.addFriendList
                    val removedFriendsList = editCarValue.removeFriendList

                    if (removedFriendsList != null){
                        for (user in removedFriendsList){
                            try{
                                HttpService.retrofitService.deleteUserCar(UserCar(user, car, false))
                            } catch( e:Exception){
                            }
                        }
                    }
                    if (addedFriendsList != null) {
                        for (user in addedFriendsList){
                            if (homeValue.user != null){
                                HttpService.retrofitService.addInvitation(Invitation(null,user,UserCar(homeValue.user, car, true), "waiting"))
                            }
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
            if(removedFriendsList != null){
                list = removedFriendsList?.toMutableList()!!
                list?.add(user)
                setRemovedFriendsList(list.toTypedArray())
            }

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
    private fun getUserCarForUUID(uuid:String): UserCar?{
        val getStartedState = getStartedUiState.value
        val homeState = homeUiState.value
        var car:Car? = null
        var userCar:UserCar? = null
        if (getStartedState!= null && homeState.cars!= null){
            for (i in homeState.cars!!){
                if (i.uuid == uuid && i.id != null){
                    car = i
                }
            }
        }
        if (homeState.user != null && car != null){
            userCar = UserCar(homeState.user, car, true)
        }
        return userCar
    }

    private fun removeCurrentDrivingUser(uuid: String){
        val userCar = getUserCarForUUID(uuid)
        if (userCar != null){
            lockCarTracking = true
            viewModelScope.launch {
                HttpService.retrofitService.removeCurrentDriver(userCar)
            }
        }
    }

    private fun setCurrentDrivingUser(uuid:String){
        val userCar = getUserCarForUUID(uuid)
        if (userCar != null){
            lockCarTracking = true
            viewModelScope.launch {
                HttpService.retrofitService.putCurrentDriver(userCar)
            }
            if (homeUiState.value.user?.id != null){
                getCarsForUserId(homeUiState.value.user!!.id!!)
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

    fun setDate(selectedDate: LocalDate) {
        _statsState.update { statisticsScreenState ->
            statisticsScreenState.copy(selectedDate=selectedDate)
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

    fun deleteCar(car:Car, context: Context) {
        viewModelScope.launch {
            try{
                HttpService.retrofitService.deleteCar(car.id!!)
            } catch(e: Exception){
                if (e.localizedMessage == "HTTP 500 ") {
                    Toast.makeText(context, "Disconnect from current device before deleting car", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun addInvitationToDeleteList(deleteInvitationList: Array<Invitation>?){
        _invitationState.update { currentState ->
            currentState.copy(
                deleteInvitationList =deleteInvitationList
            )
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun deleteInvitations() {
        var deleteInvitationList = invitation.value.deleteInvitationList
        var statusInvitationArray = invitation.value.statusInvitationList
        addInvitationToDeleteList(null)
        var statusInvitationList = statusInvitationArray?.toMutableList()




        if (deleteInvitationList != null) {
                for (invitation in deleteInvitationList){
                    statusInvitationList?.remove(invitation)
                    viewModelScope.launch {
                        HttpService.retrofitService.deleteInvitation(invitation.id.toString())
                    }
                }
                setStatusInvitationList(statusInvitationList!!.toTypedArray())
            }
    }

    fun getNotificationCount(): Int{
        val invitation = invitation.value
        var notificationCount = 0
        if (invitation.deleteInvitationList != null){
            notificationCount += invitation.deleteInvitationList?.size!!
        }
        if (invitation.incomingInvitationList != null){
            for (invitation in invitation.incomingInvitationList!!) {
                if (invitation.status == "agree" || invitation.status == "dismiss") {
                    notificationCount--
                }
            }
            notificationCount += invitation.incomingInvitationList?.size!!
        }
        if (invitation.statusInvitationList != null){
            notificationCount += invitation.statusInvitationList?.size!!
        }

        return notificationCount
    }

    suspend fun setKilometersForDrive(startPosition: LatLng, destinationPosition: LatLng) {
        var response: DirectionsResponse? = null
        response = MapsHttpService.retrofitService.getMapsDirections(startPosition.latitude.toString()+","+startPosition.longitude.toString(),destinationPosition.latitude.toString()+","+destinationPosition.longitude.toString(), "driving", "AIzaSyBzdzVWCdsxOGDXDpFx71mtdlhXnQeVdDk")
        _driveState.update { currentState->
            currentState.copy(
                kilometers = response.routes[0].legs[0].distance.value.toDouble()/1000
            )
        }
    }

    fun setDriveStartPosition(startPosition: LatLng){
        _driveState.update { currentState->
            currentState.copy(
                startPosition = startPosition
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun localDateToDate(localDate: LocalDate): Date {
        val zoneId = ZoneId.systemDefault() // You can specify a different time zone if needed
        val instant = localDate.atStartOfDay(zoneId).toInstant()
        return Date.from(instant)
    }

    fun setStatisticsCar(car: Car?) {
        _statsState.update { state->
            state.copy(
                car = car
            )
        }
    }

    fun setReason(reason: CostsEnum) {
        _costsState.update { currentState->
            currentState.copy(
                reason = reason
            )
        }
    }

    fun setCosts(costs: String) {
        _costsState.update { currentState->
            currentState.copy(
                costs = costs
            )
        }
    }

    fun addCosts() {
        viewModelScope.launch {
            val response: ResponseBody = HttpService.retrofitService.getUserCarWithoutId(UserCar(homeUiState.value.user!!, statsState.value.car!!, false))
            val userCar = readUserCarFromJson(response.byteStream())

            val costs:Costs = Costs(null, costsState.value.reason.toString(), costsState.value.costs.toLong(), userCar = userCar)

            HttpService.retrofitService.addCosts(costs)

        }
    }

    fun setShowCosts(b: Boolean) {
        _costsState.update { currentState->
            currentState.copy(
                showCosts=b
            )
        }
    }

    fun clearCostsData() {
        _costsState.update { currentState->
            currentState.copy(
                costs="",
                reason=null,
                showCosts=false
            )
        }
    }
}
