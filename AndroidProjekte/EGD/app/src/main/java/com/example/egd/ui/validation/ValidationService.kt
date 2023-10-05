package com.example.egd.ui.validation

import androidx.compose.runtime.collectAsState
import com.example.egd.data.validation.ValidationObject
import com.example.egd.ui.EGDViewModel

class ValidationService () {

    fun validateCarInfoScreen(carName: String, fuelConsumption: String): Boolean{
        if (validateCarName(carName).valid && validateFuelConsumption(fuelConsumption).valid){
            return true
        }
        return false
    }

    fun validateCarName(carName:String): ValidationObject{
        if (carName.isEmpty()){
            return ValidationObject(false, "Field is required.")
        }
        if (carName.length < 4){
            return ValidationObject(false, "Must be at least 4 characters")
        }
        return ValidationObject(true, "");
    }
    fun validateFuelConsumption( fuelConsumption: String): ValidationObject {
        if (fuelConsumption == ""){
            return ValidationObject(false, "Field is required")
        }
        if (fuelConsumption != "" && fuelConsumption.toDouble() <= 0){
            return ValidationObject(false, "Must be greater 0")
        }
        return ValidationObject(true, "")
    }

    fun validatePassword(password:String): ValidationObject{
        if (password.length < 8){
            return ValidationObject(false, "Password must have 8 characters")
        }
        return ValidationObject(true, "")
    }

    fun validateEmail(email:String): ValidationObject{
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return ValidationObject(false, "Wrong email format")
        }
        return ValidationObject(true, "")
    }

    fun validateUserName(userName:String): ValidationObject{
        if (userName.length <4){
            return ValidationObject(false, "Username needs at least 4 characters")
        }
        return ValidationObject(true, "")
    }

    fun validateRegisterForm(userName: String, email: String, password: String, licencePlate:String) :Boolean{
        if (validatePassword(password).valid && validateEmail(email).valid && validateUserName(userName).valid && validateLicencePlate(licencePlate).valid){
            return true
        }
        return false
    }

    fun validateLicencePlate(licencePlate: String): ValidationObject {
        if (licencePlate.isNotEmpty()){
            return ValidationObject(false, "License Plate must have a value")
        }
        return ValidationObject(true, "")
    }

    fun validateLoginForm(email: String, password: String): Boolean{
        if (validatePassword(password).valid && validateEmail(email).valid){
            return true
        }
        return false
    }

    fun validateConnectionScreen(connectionSuccessful: Boolean): ValidationObject{
        if (!connectionSuccessful){
            return ValidationObject(false, "Connection wasn't successful")
        }
        return ValidationObject(true, "")

    }
}